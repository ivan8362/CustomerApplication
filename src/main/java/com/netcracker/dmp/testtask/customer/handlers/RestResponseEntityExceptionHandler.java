package com.netcracker.dmp.testtask.customer.handlers;

import com.netcracker.dmp.testtask.customer.exceptions.CustomerAlreadyExistsException;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerNotFoundException;
import com.netcracker.dmp.testtask.customer.exceptions.EmployeesAreNotDeletedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (value = {CustomerNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userMessage", ex.getMessage());
        hashMap.put("status", "404");
        hashMap.put("error", "Not Found");

        return handleExceptionInternal(ex, hashMap, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler (value = {CustomerAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflictExists(RuntimeException ex, WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userMessage", ex.getMessage());
        hashMap.put("status", "409");
        hashMap.put("error", "Conflict");

        return handleExceptionInternal(ex, hashMap, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler (value = {EmployeesAreNotDeletedException.class})
    protected ResponseEntity<Object> handleConflictEmployeesNotDeleted(RuntimeException ex, WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userMessage", ex.getMessage());
        hashMap.put("status", "404");
        hashMap.put("error", "Not Found");

        return handleExceptionInternal(ex, hashMap, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
