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

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userMessage", ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        hashMap.put("status", "404");
        hashMap.put("error", "Not Found");
        StringBuilder builder = new StringBuilder("{\n \"error\" : {\n");

        for (Map.Entry<String, String> entry: hashMap.entrySet()) {
            builder.append("\"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"\n");
        }

        builder.append("}\n}");

        return handleExceptionInternal(ex, builder, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler (value = {CustomerAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflictExists(RuntimeException ex, WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userMessage", ErrorMessages.CUSTOMER_ALREADY_EXISTS);
        hashMap.put("status", "409");
        hashMap.put("error", "Conflict");
        StringBuilder builder = new StringBuilder("{\n \"error\" : {\n");

        for (Map.Entry<String, String> entry: hashMap.entrySet()) {
            builder.append("\"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"\n");
        }

        builder.append("}\n}");

        return handleExceptionInternal(ex, builder, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
