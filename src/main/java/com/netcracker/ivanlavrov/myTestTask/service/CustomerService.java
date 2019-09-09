package com.netcracker.ivanlavrov.myTestTask.service;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;

public interface CustomerService {
    /**
     * this method adds customer to DB.
     *
     * @param customer
     */
    void addCustomer(final CustomerDTO customer);

    /**
     * this method updates customer in DB. You have to specify some parameters, some can be null.
     *
     * @param id
     * @param name
     * @param description
     * @param email
     * @param address
     * @return
     */
    Customer updateCustomer(String id, String name, String description, String email, String address);

    /**
     * this method returns customer by customerId.
     * @param id
     * @return
     */
    Customer getCustomerById(String id);

    /**
     * this methiod removes a customer from DB with all its employees.
     * @param id
     */
    void deleteCustomer(String id);
}
