package com.felzan.iapps.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class XmlUtils {

    private final XmlMapper mapper;

    public <T> T readXml(MultipartFile file, Class<T> clazz) {
        try {
            return mapper.readValue(file.getBytes(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
