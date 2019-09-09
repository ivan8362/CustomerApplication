package com.netcracker.ivanlavrov.myTestTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerAlreadyExistsException extends ResponseStatusException {
    public CustomerAlreadyExistsException(HttpStatus httpStatus, String errorMessage){
        super(httpStatus, errorMessage);
    }
}
