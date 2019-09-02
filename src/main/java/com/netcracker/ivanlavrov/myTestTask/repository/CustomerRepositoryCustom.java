package com.netcracker.ivanlavrov.myTestTask.repository;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;

public interface CustomerRepositoryCustom {
        long updateCustomer(String id, String name, String description, String email, String address);
}
