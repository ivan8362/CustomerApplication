package com.netcracker.ivanlavrov.myTestTask.web.rest.controller;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.CustomerApi;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import com.netcracker.ivanlavrov.myTestTask.web.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerApi customerApi;

    @Autowired
    private CustomerRepository customerRepository;

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO addCustomer(@Valid @RequestBody CustomerDTO customer){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_ADDED_SUCCESSFULLY);
        customerApi.addCustomer(customer);

        return responseDTO;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String showAllCustomers() {
        return customerApi.findAll();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO updateCustomer(
            @Valid @RequestBody CustomerDTO customerDTO,
            @PathVariable(name = "id") String id){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_UPDATED_SUCCESSFULLY);

        customerApi.updateCustomer(id, customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(), customerDTO.getAddress());

        return responseDTO;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "id") String id){
            return customerApi.getCustomerById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO deleteCustomer(@PathVariable(name = "id") String id){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_DELETED_SUCCESSFULLY);
        customerApi.deleteCustomer(id);

        return responseDTO;
    }
}
