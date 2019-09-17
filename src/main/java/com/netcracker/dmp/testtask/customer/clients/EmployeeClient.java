package com.netcracker.dmp.testtask.customer.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@Component
public class EmployeeClient {
//    private static Logger log = Logger.getLogger(EmployeeClient.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${employee.host}")
    private String host;

    @Value("${employee.port}")
    private Integer port;

    public void deleteAllEmployeesForCustomer(String customerId) {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                    "http://" + host + ":" + port + "/v1/employees/customers/"
                    + customerId)
                    .build();
            restTemplate.delete(uriComponents.toUri());
    }
}
