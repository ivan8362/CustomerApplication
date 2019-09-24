package com.netcracker.dmp.testtask.customer.config;

import com.netcracker.dmp.testtask.customer.clients.EmployeeClient;
import com.netcracker.dmp.testtask.customer.repositories.CustomerRepository;
import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class CustomerServiceTestConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public EmployeeClient employeeClient(){
        return Mockito.mock(EmployeeClient.class);
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }
}
