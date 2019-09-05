package com.netcracker.ivanlavrov.myTestTask;

import com.mongodb.client.result.UpdateResult;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

    /*
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long updateCustomer(String id, String name, String description, String email, String address){
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name", name);
        update.set("description", description);
        update.set("email", email);
        update.set("address", address);

        UpdateResult result = this.mongoTemplate.updateFirst(query, update, Customer.class);

        if (result != null) {
            return result.getModifiedCount();
        }

        return 0;
    }

     */
}
