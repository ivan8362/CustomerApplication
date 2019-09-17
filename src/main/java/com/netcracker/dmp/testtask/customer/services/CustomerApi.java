package com.netcracker.dmp.testtask.customer.services;

import com.netcracker.dmp.testtask.customer.entities.Customer;

import java.util.List;

public interface CustomerApi {

    /**
     * This method adds a customer to persistence level.
     * CustomerDTO is input from API consumer.
     * In case customer with the same name exists, API will return an error.
     *
     * @param name customer's legal name.
     * @param description short description of customer.
     * @param email customer's email address. No validation.
     * @param address customer's physical or legal address.
     * @return Customer object with ID and all other parameters.
     */
    Customer createCustomer(String name, String description, String email, String address);

    /**
     * This method updates customer's properties in persistence level.
     * You have to specify some parameters, some can be left unchanged.
     * In case customer with such customerId does not exists, API will return an error.
     * @param id - customer ID.
     * @param name - customer's legal name.
     * @param description - short description of customer.
     * @param email - customer's email address. No validation.
     * @param address - customer's physical or legal address.
     * @return Customer object with ID and all the parameters.
     */
    Customer updateCustomer(String id, String name, String description, String email, String address);

    /**
     * This method returns customer by the given customerId.
     * @param id - customer ID.
     * @return Customer object.
     */
    Customer getCustomerById(String id);

    /**
     * This method removes a customer from persistence level. All customer's employees will be removed as well.
     * @param id - customer ID.
     */
    void deleteCustomer(String id);

    /**
     * This method returns List of all customers with all details.
     * @return List<Customer>
     */
    List<Customer> getAllCustomers();
}
