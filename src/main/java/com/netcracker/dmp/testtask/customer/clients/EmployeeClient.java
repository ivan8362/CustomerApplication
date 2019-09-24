package com.netcracker.dmp.testtask.customer.clients;

import com.netcracker.dmp.testtask.customer.exceptions.EmployeesAreNotDeletedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class EmployeeClient {
    private static Logger logger = LoggerFactory.getLogger(EmployeeClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${employee.host}")
    private String host;

    @Value("${employee.port}")
    private Integer port;

    public void deleteAllEmployeesForCustomer(String customerId) {
        /*UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://" + host + ":" + port + "/v1/employees/customers/"
                + customerId)
                .build();
        restTemplate.delete(uriComponents.toUri());*/

        String url = "http://" + host + ":" + port + "/v1/employees/customers/" + customerId;
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            logger.debug("All employees for Customer " + customerId + " deleted successfully.");
        } else {
            throw new EmployeesAreNotDeletedException(customerId);
        }
    }
}
