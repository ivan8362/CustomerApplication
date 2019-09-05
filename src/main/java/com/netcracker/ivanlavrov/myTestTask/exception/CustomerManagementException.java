package com.netcracker.ivanlavrov.myTestTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer noy found.")
public class CustomerManagementException extends RuntimeException {
    private static final long serialVersionUID = 4269316494280751012L;

    public CustomerManagementException(final String message) {
        super(message);
    }

    public CustomerManagementException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
