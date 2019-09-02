package com.netcracker.ivanlavrov.myTestTask;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Customer, String> {
    //
}
