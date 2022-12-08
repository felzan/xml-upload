package com.felzan.iapps.service;

import com.felzan.iapps.exception.FileNotValidException;
import com.felzan.iapps.model.EPaper;
import com.felzan.iapps.repository.EPaperRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService fileService;
    @Mock
    private EPaperRepository ePaperRepository;

    @Test
    @DisplayName("Should upload with success.")
    void uploadWithSuccess() {
        EPaper ePaper = EPaper.builder().build();
        when(ePaperRepository.save(any(EPaper.class)))
                .thenReturn(ePaper);

        fileService.upload(ePaper);

        verify(ePaperRepository, times(1)).save(ePaper);
    }

    @Test
    @DisplayName("Should throw exception when null.")
    void uploadThrowException() {
        when(ePaperRepository.save(null))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> fileService.upload(null));

        verify(ePaperRepository, times(1)).save(null);
    }

    @Test
    @DisplayName("Should findAll by example")
    void searchByAnyOfExample() {
        Sort sort = by(asc("createdAt"));
        Pageable pageable = PageRequest.of(0, 5, sort);
        EPaper ePaper = EPaper.builder().build();

        fileService.searchByAnyOfExample(ePaper, pageable);

        verify(ePaperRepository, times(0)).findAll(pageable);
        verify(ePaperRepository, times(1)).findAll(any(Example.class), eq(pageable));
    }

    @Test
    @DisplayName("Should findAll when example is null")
    void searchByAnyOfExampleNull() {
        Sort sort = by(asc("createdAt"));
        Pageable pageable = PageRequest.of(0, 5, sort);

        fileService.searchByAnyOfExample(null, pageable);

        verify(ePaperRepository, times(1)).findAll(pageable);
        verify(ePaperRepository, times(0)).findAll(null, pageable);
    }

    @Test
    @DisplayName("Should validate XML")
    void isValid() {
        ReflectionTestUtils.setField(fileService, "validationPath", "classpath:xml/validation/");
        MockMultipartFile validFile = new MockMultipartFile("tmp", "originalFilename", "application/xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <epaperRequest> <deviceInfo name=\"Browser\" id=\"test@comp\"> <screenInfo width=\"1280\" height=\"752\" dpi=\"300\" /> <osInfo name=\"Browser\" version=\"1.0\" /> <appInfo> <newspaperName>abb</newspaperName> <version>1.0</version> </appInfo> </deviceInfo> <getPages editionDefId=\"11\" publicationDate=\"2017-06-06\" /> </epaperRequest>".getBytes());

        fileService.isValid(validFile);
    }

    @Test
    @DisplayName("Should throw exception when file is not valid")
    void isValidThrowFileNotValidException() {
        ReflectionTestUtils.setField(fileService, "validationPath", "classpath:xml/validation/");
        MockMultipartFile notValidFile = new MockMultipartFile("tmp", "originalFilename", "application/xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <epaperRequest> <deviceInfo name=\"Browser\" id=\"test@comp\"> <screenInfo width=\"1280\" height=\"752\" dpi=\"300\" /> <osInfo name=\"Browser\" version=\"1.0\" /> <appInfo> <newspaperName>abb</newspaperName> <version>AAA</version> </appInfo> </deviceInfo> <getPages editionDefId=\"11\" publicationDate=\"2017-06-06\" /> </epaperRequest>".getBytes());

        assertThrows(FileNotValidException.class, () -> fileService.isValid(notValidFile));
    }
}