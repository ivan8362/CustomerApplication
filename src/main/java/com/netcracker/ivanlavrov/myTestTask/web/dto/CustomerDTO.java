package com.netcracker.ivanlavrov.myTestTask.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * This class is a customer object created by some API consumer, but not from repository.
 * name - customer's legal name.
 * description - short description about customer.
 * email - customer's email address. No validation.
 * address - customer's postal address.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("address")
    private String address;

    public CustomerDTO(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("email") String email,
                       @JsonProperty("address") String address) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.address = address;
    }

}
