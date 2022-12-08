package com.felzan.iapps.controller;

import com.felzan.iapps.controller.request.EPaperRequest;
import com.felzan.iapps.model.EPaper;
import com.felzan.iapps.service.FileService;
import com.felzan.iapps.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/files")
public class FileController {

    private final FileService fileService;
    private final XmlUtils xmlUtils;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<EPaper> postFile(@RequestParam("file") MultipartFile file) {
        fileService.isValid(file);
        EPaperRequest request = xmlUtils.readXml(file, EPaperRequest.class);
        EPaper ePaper = request.toEPaperBuilder()
                .filename(file.getOriginalFilename())
                .build();
        return ResponseEntity.ok().body(fileService.upload(ePaper));
    }

    @GetMapping()
    public ResponseEntity<Page<EPaper>> search(@RequestBody(required = false) EPaper example,
            @PageableDefault(size = 5)
            @SortDefault.SortDefaults(
                    @SortDefault(sort = "createdAt", direction = ASC)
            ) Pageable pageable) {
        return ResponseEntity.ok().body(fileService.searchByAnyOfExample(example, pageable));
    }

}
