package com.felzan.iapps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class FileNotValidException extends RuntimeException{

    public FileNotValidException(String message) {
        super(message);
    }
}
