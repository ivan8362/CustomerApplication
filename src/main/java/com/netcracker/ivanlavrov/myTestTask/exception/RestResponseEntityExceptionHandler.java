package com.netcracker.ivanlavrov.myTestTask.exception;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants.ErrorMessages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "{\n \"errors\":\n[{\n\"userMessage\": \"" + ErrorMessages.CUSTOMER_DOES_NOT_EXIST
                + "\",\n\"status\": 404,\n\"error\": \"Not Found\"\n}\n}]";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler (value = {CustomerAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflictExists(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "{\n \"errors\":\n[{\n\"userMessage\": \"" + ErrorMessages.CUSTOMER_ALREADY_EXISTS
                + "\",\n\"status\": 409,\n\"error\": \"Conflict\"\n}\n}]";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
