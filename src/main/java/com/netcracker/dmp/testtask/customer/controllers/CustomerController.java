package com.netcracker.dmp.testtask.customer.controllers;

import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.services.CustomerApi;
import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeMath.log;

@RestController
@RequestMapping("v1/customers")
public class CustomerController {
    private static Logger log = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerApi customerApi;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public Customer createCustomer(@RequestBody CustomerDTO customerDTO){
        log(Level.DEBUG, "A client called POST /v1/customers/ REST API with parameters: " + customerDTO);

        Customer customer = customerApi.createCustomer(customerDTO.getName(), customerDTO.getDescription(),
                customerDTO.getEmail(), customerDTO.getAddress());
        log(Level.DEBUG, "Customer with ID: " + customer.getId() + " was successfully added to DB.");
        return customer;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Customer> getAllCustomers() {
        log(Level.DEBUG, "A client called GET /v1/customers/ REST API with no parameters.");

        return customerApi.getAllCustomers();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public Customer updateCustomer(
            @PathVariable(name = "id") String id,
            @RequestBody CustomerDTO customerDTO){
        log(Level.DEBUG, "A client called PUT /v1/customers/" + id + "/ REST API with parameters: " + customerDTO);

        Customer customer = customerApi.updateCustomer(id, customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(), customerDTO.getAddress());

        return customer;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "id") String id){
        log(Level.DEBUG, "A client called GET /v1/customers/" + id + "/ REST API with no parameters.");

        return customerApi.getCustomerById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCustomer(@PathVariable(name = "id") String id){
        log(Level.DEBUG, "A client called DELETE /v1/customers/" + id + "/ REST API with no parameters.");

        customerApi.deleteCustomer(id);

    }
}
