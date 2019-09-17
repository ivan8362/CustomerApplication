package com.netcracker.dmp.testtask.customer.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    private String name;
    private String description;
    private String email;
    private String address;

    public Customer(String name, String description, String email, String address) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.email = email;
    }
}
