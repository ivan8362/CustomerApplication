package com.netcracker.dmp.testtask.customer.exceptions;

public class EmployeesAreNotDeletedException extends RuntimeException {
    public EmployeesAreNotDeletedException(String customerId) {
        super("No employees are deleted for customer with ID: " + customerId);
    }
}
