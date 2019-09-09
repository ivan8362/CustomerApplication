package com.netcracker.ivanlavrov.myTestTask.service.impl;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants.ErrorMessages;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerAlreadyExistsException;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerManagementException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.CustomerService;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import com.netcracker.ivanlavrov.myTestTask.web.transformer.DTOToDomainTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByName(customerDTO.getName());

        if (customer != null) {
            throw new CustomerAlreadyExistsException(HttpStatus.CONFLICT, ErrorMessages.CUSTOMER_ALREADY_EXISTS);
        }
        Customer customerToPersist = DTOToDomainTransformer.transform(customerDTO);
        customerRepository.insert(customerToPersist);
    }

    public Customer updateCustomer(String id, String name, String description, String email, String address) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }
        Customer customerToPersist = customer.get();
        if (!customerToPersist.getName().equals(name)) {
            customerToPersist.setName(name);
        }
        if (!customerToPersist.getDescription().equals(description)) {
            customerToPersist.setDescription(description);
        }
        if (!customerToPersist.getEmail().equals(email)) {
            customerToPersist.setEmail(email);
        }
        if (!customerToPersist.getAddress().equals(address)) {
            customerToPersist.setAddress(address);
        }

        return customerRepository.save(customerToPersist);
    }

    public Customer getCustomerById(String id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }

        return customer.get();
    }

    public void deleteCustomer(String id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customerRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }
    }
}
