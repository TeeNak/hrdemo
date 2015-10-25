package com.github.teenak77.hrdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by teenak on 2015/02/22.
 */

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ResourceNotFoundException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}