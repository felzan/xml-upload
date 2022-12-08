package com.felzan.iapps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class CouldNotLoadFileException extends RuntimeException{

    public CouldNotLoadFileException() {
        super("Could not load file.");
    }
}
