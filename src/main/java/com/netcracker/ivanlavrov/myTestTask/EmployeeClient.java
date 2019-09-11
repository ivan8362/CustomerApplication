package com.netcracker.ivanlavrov.myTestTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@Component
public class EmployeeClient {
    private static Logger log = Logger.getLogger(EmployeeClient.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${employee.host}")
    private String host;

    @Value("${employee.port}")
    private Integer port;

    public void deleteAllEmployeesForCustomer(String customerId) {
        String[] employees = getEmployeeIdsForCustomer(customerId);

        if (employees == null) {
            return;
        }

        for (String employeeId : employees) {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                    "http://" + host + ":" + port + "/rest/api/v1/employees/"
                    + employeeId)
                    .build();
            restTemplate.delete(uriComponents.toUri());
            log.info("Employee with id " + employeeId + " was deleted.");
        }
    }

    private String[] getEmployeeIdsForCustomer(String customerId) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://" + host + ":" + port + "/rest/api/v1/employees/customers/"
                + customerId)
                .build();
        String response = restTemplate.getForObject(uriComponents.toUri(), String.class);

        if (response.equals("[]")) {
            log.info("No employees deleted for the current customer.");

            return null;
        } else {
            String[] employees = response
                    .substring(1, (response.length() - 1))
                    .trim()
                    .replace("\n", "")
                    .replace("\r", "")
                    .split(",");

            return employees;
        }
    }

}
