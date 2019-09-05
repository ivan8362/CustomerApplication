package com.netcracker.ivanlavrov.myTestTask.web.rest.controller;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerManagementException;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerNotFoundException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.CustomerService;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import com.netcracker.ivanlavrov.myTestTask.web.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO addCustomer(@Valid @RequestBody CustomerDTO customer){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_ADDED_SUCCESSFULLY);

        try {
            customerService.addCustomer(customer);
        } catch (Exception e) {
            responseDTO.setStatus(ResponseDTO.Status.FAIL);
            responseDTO.setMessage(e.getMessage());
        }

        return responseDTO;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/show-all", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public String showAllCustomers() {

    List<Customer> customers = this.customerRepository.findAll();

    String html = "[";
    for (Customer customer : customers) {
        html += customer + ",\n";
    }

    html += "]";

    return html;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = {
            MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO updateCustomer(
            @Valid @RequestBody CustomerDTO customerDTO,
            @PathVariable(name = "id") String id
            ){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_UPDATED_SUCCESSFULLY);

        try {
            customerService.updateCustomer(
                    id, customerDTO.getName(), customerDTO.getDescription(),
                    customerDTO.getEmail(), customerDTO.getAddress());
        } catch (Exception e) {
            responseDTO.setStatus(ResponseDTO.Status.FAIL);
            responseDTO.setMessage(e.getMessage());
        }

        return responseDTO;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "id") String id){
        Customer customer = null;

        try {
            customer = customerService.getCustomerById(id);
            return customer;
        } catch (CustomerManagementException e) {
//            ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.FAIL,
//                    e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The customer doesn't exist.", e);
        }
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO deleteCustomer(@PathVariable(name = "id") String id){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.CUSTOMER_DELETED_SUCCESSFULLY);

        try {
            customerService.deleteCustomer(id);
        } catch (Exception e) {
            responseDTO.setStatus(ResponseDTO.Status.FAIL);
            responseDTO.setMessage(e.getMessage());
        }

        return responseDTO;
    }

    @ExceptionHandler({ CustomerManagementException.class })
    public void handleException() {
            return;
        }
}
