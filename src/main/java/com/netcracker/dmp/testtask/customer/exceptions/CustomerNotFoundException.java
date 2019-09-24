package com.netcracker.dmp.testtask.customer.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerId) {
        super("Could not find customer with ID : " + customerId);
    }
}
