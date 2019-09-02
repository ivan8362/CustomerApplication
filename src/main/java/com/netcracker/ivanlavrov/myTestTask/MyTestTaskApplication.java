package com.netcracker.ivanlavrov.myTestTask;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
public class MyTestTaskApplication /*implements CommandLineRunner*/ {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MyTestTaskApplication.class, args);
	}

	/*
	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;
	}

	 */
	/*
	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("AliceInc", "Inc", "a@a.com" , "1 Smith street, London"));
		repository.save(new Customer("BobInc", "inc", "b@b.com", "2 Smith street, London"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByName("Alice"));

		System.out.println("Customers found with findByAddress('2 Smith street, London'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByAddress("2 Smith street, London"));
	}*/
}
