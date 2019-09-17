package com.netcracker.dmp.testtask.customer.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String email){
        super("Customer with email \"" + email + "\" already exists.");
    }
}
