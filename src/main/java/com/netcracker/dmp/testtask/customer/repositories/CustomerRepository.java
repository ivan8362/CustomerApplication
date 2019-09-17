package com.netcracker.dmp.testtask.customer.repositories;

import com.netcracker.dmp.testtask.customer.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'email' : {$regex: ?0, $options: 'i' }}")
    Optional<Customer> findByEmail(final String email);
}
