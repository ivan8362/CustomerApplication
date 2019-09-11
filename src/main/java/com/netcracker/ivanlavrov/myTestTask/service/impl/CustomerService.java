package com.netcracker.ivanlavrov.myTestTask.service.impl;

import com.netcracker.ivanlavrov.myTestTask.EmployeeClient;
import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants.ErrorMessages;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerAlreadyExistsException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.CustomerApi;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomerService implements CustomerApi {
    private static Logger log = Logger.getLogger(EmployeeClient.class.getName());

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeClient employeeClient;

    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByName(customerDTO.getName());

        if (customer != null) {
            throw new CustomerAlreadyExistsException(HttpStatus.CONFLICT, ErrorMessages.CUSTOMER_ALREADY_EXISTS);
        }

        Customer customerToPersist = new Customer(customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(),
                customerDTO.getAddress());
        customerRepository.insert(customerToPersist);
        log.info("Customer was successfully added to DB.");
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

        log.info("Customer was successfully updated in DB.");

        return customerRepository.save(customerToPersist);
    }

    public Customer getCustomerById(String id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }

        log.info("Customer was queried by ID.");

        return customer.get();
    }

    public void deleteCustomer(String id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()){
            customerRepository.deleteById(id);
            employeeClient.deleteAllEmployeesForCustomer(id);
            log.info("Customer was successfully removed from DB.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }
    }
}
