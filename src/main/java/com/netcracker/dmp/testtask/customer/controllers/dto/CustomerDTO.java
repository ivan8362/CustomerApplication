package com.netcracker.dmp.testtask.customer.controllers.dto;

import lombok.Data;

/**
 * This class is a Data Transfer Object which is received as HTTP request body during customer creation or update.
 * name - customer's legal name.
 * description - short description of customer.
 * email - customer's email address. No validation.
 * address - customer's physical or legal address.
 */
@Data
public class CustomerDTO {
    private final String name;
    private final String description;
    private final String email;
    private final String address;
}
