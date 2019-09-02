package com.netcracker.ivanlavrov.myTestTask.repository;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    Customer findByName(final String name);

//    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
//    Customer findByAddress(String address);

//    void deleteCustomer(String id);

    @Query( "{ '_id' : {}}" )
    void updateCustomer(final Customer customer);

}
