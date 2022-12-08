package com.felzan.iapps.service;

import com.felzan.iapps.exception.CouldNotLoadFileException;
import com.felzan.iapps.exception.FileNotValidException;
import com.felzan.iapps.model.EPaper;
import com.felzan.iapps.repository.EPaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private static final String EPAPER_XSD = "EPaper.xsd";

    @Value("${app.validation.folder}")
    private String validationPath;

    private final EPaperRepository ePaperRepository;

    public EPaper upload(EPaper ePaper) {
        return ePaperRepository.save(ePaper);
    }

    public Page<EPaper> searchByAnyOfExample(EPaper ePaper, Pageable pageable) {
        Page<EPaper> searchResult;
        if (ePaper != null) {
            Example<EPaper> example = Example.of(ePaper, ExampleMatcher.matchingAny());
            searchResult = ePaperRepository.findAll(example, pageable);
        } else {
            searchResult = ePaperRepository.findAll(pageable);
        }
        return searchResult;
    }

    public void isValid(MultipartFile file) {
        try {
            Schema schema = loadValidationSchema();
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file.getInputStream()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CouldNotLoadFileException();
        } catch (SAXException e) {
            throw new FileNotValidException(e.getMessage());
        }
    }

    private Schema loadValidationSchema() throws IOException, SAXException {
        String filepath = validationPath + EPAPER_XSD;
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(ResourceUtils.getFile(filepath).getCanonicalFile());
    }
}
