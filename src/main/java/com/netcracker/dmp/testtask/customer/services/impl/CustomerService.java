package com.netcracker.dmp.testtask.customer.services.impl;

import com.netcracker.dmp.testtask.customer.clients.EmployeeClient;
import com.netcracker.dmp.testtask.customer.constants.MessageConstants;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerAlreadyExistsException;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerNotFoundException;
import com.netcracker.dmp.testtask.customer.services.CustomerApi;
import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.repositories.CustomerRepository;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeMath.log;

@Service
public class CustomerService implements CustomerApi {
    private static Logger log = Logger.getLogger(EmployeeClient.class.getName());

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeClient employeeClient;

    public Customer createCustomer(String name, String description, String email, String address) {
        CustomerDTO customerDTO = new CustomerDTO(name, description, email, address);
        Optional<Customer> customer = customerRepository.findByEmail(customerDTO.getEmail());

        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException(customerDTO.getEmail());
        }

        Customer newCustomer = new Customer(customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(),
                customerDTO.getAddress());
        customerRepository.insert(newCustomer);
        log.info("Customer with ID : " + newCustomer.getId() + " was successfully created.");

        return newCustomer;
    }

    public Customer updateCustomer(String id, String name, String description, String email, String address) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(id);
        }

        Optional<Customer> customerByEmail = customerRepository.findByEmail(email);

        if (customerByEmail.isPresent()) {
            throw new CustomerAlreadyExistsException(id);
        }

        Customer updateCustomer = customer.get();

        if (name != null && !name.trim().isEmpty() && !updateCustomer.getName().equals(name)) {
            updateCustomer.setName(name);
            log(Level.DEBUG, "Customer's name property was updated.");
        }

        if (description != null && !description.trim().isEmpty() && !updateCustomer.getDescription().equals(description)) {
            updateCustomer.setDescription(description);
            log(Level.DEBUG,"Customer's description property was updated.");

        }

        if (email != null && !email.trim().isEmpty() && !updateCustomer.getEmail().equals(email)) {
            updateCustomer.setEmail(email);
            log(Level.DEBUG,"Customer's email property was updated.");
        }

        if (address != null && !address.trim().isEmpty() && !updateCustomer.getAddress().equals(address)) {
            updateCustomer.setAddress(address);
            log(Level.DEBUG,"Customer's address property was updated.");
        }

        Customer returnCustomer = customerRepository.save(updateCustomer);
        log.info("Customer with ID: " + id + " was successfully updated.");

        return returnCustomer;
    }

    public Customer getCustomerById(String id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()){
            throw new CustomerNotFoundException(id);
        }

        return customer.get();
    }

    public void deleteCustomer(String id){
        Customer customer = getCustomerById(id);

        if (customer != null){
            employeeClient.deleteAllEmployeesForCustomer(id);
            log.info("All employees for the customer with ID: " + id +" was successfully removed.");

            customerRepository.deleteById(id);
            log.info("Customer with ID: " + id + " was successfully removed.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ErrorMessages.CUSTOMER_DOES_NOT_EXIST);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();

        return customers;
    }
}
