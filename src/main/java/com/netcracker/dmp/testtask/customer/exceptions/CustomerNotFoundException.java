package com.netcracker.dmp.testtask.customer.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4269316494280751012L;

    public CustomerNotFoundException(String customerId) {
        super("Could not find customer with ID : " + customerId);
    }
}
