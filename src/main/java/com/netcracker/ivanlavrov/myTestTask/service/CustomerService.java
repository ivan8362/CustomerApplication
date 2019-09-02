package com.netcracker.ivanlavrov.myTestTask.service;

import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;

public interface CustomerService {
    void addCustomer(final CustomerDTO customer);
    void updateCustomer(final CustomerDTO customer);
}
