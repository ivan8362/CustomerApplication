package com.netcracker.ivanlavrov.myTestTask.service;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;

public interface CustomerService {
    void addCustomer(final CustomerDTO customer);
    Customer updateCustomer(String id, String name, String description, String email, String address);
    Customer getCustomerById(String id);
    void deleteCustomer(String id);
}
