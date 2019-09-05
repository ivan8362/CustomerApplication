package com.netcracker.ivanlavrov.myTestTask.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
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

    @Override
    public String toString() {
        return "{" +
                "\"id\" : \"" + id + "\"" +
                ", \"name\" : \"" + name + "\"" +
                ", \"description\" : \"" + description + "\"" +
                ", \"email\" : \"" + email + "\"" +
                ", \"address\" : \"" + address + "\"" +
                "\n}";
    }
}
