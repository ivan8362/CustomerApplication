package com.netcracker.dmp.testtask.customer.controllers;

import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.services.CustomerApi;
import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/customers")
public class CustomerController {
    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerApi customerApi;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public Customer createCustomer(@RequestBody CustomerDTO customerDTO){
        logger.debug("A client called POST /v1/customers/ REST API with parameters: " + customerDTO);

        Customer customer = customerApi.createCustomer(customerDTO.getName(), customerDTO.getDescription(),
                customerDTO.getEmail(), customerDTO.getAddress());
        logger.debug("Customer with name: " + customer.getName()
                + " and ID: " + customer.getId() + " was successfully created.");

        return customer;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Customer> getAllCustomers() {
        logger.debug("A client called GET /v1/customers/ REST API with no parameters.");

        return customerApi.getAllCustomers();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public Customer updateCustomer(
            @PathVariable(name = "id") String id,
            @RequestBody CustomerDTO customerDTO){
        logger.debug("A client called PUT /v1/customers/" + id + "/ REST API with parameters: "
                + customerDTO);

        Customer customer = customerApi.updateCustomer(id, customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(), customerDTO.getAddress());
        logger.debug("Customer with ID: " + id + " was successfully updated.");

        return customer;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "id") String id){
        logger.debug("A client called GET /v1/customers/" + id + "/ REST API with no parameters.");

        return customerApi.getCustomerById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCustomer(@PathVariable(name = "id") String id){
        logger.debug("A client called DELETE /v1/customers/" + id + "/ REST API with no parameters.");

        customerApi.deleteCustomer(id);
    }
}
