package com.netcracker.ivanlavrov.myTestTask.repository;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    Customer findByName(final String name);

    @Query( "{ '_id' : {}}" )
    void updateCustomer(final Customer customer, String id);

}
