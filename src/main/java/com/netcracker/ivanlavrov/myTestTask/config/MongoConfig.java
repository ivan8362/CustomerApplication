package com.netcracker.ivanlavrov.myTestTask.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.netcracker.ivanlavrov.myTestTask.repository")
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Override
    protected String getDatabaseName(){
        return database;
    }

    @Override
    public MongoClient mongoClient(){
        return new MongoClient(host,port);
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.netcracker.ivanlavrov.myTestTask";
    }
}
