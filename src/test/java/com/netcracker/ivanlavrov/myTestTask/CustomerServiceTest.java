package com.netcracker.ivanlavrov.myTestTask;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerAlreadyExistsException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.impl.CustomerService;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyTestTaskApplication.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private CustomerDTO customerDTO;

    @Test(expected = CustomerAlreadyExistsException.class)
    public void whenCustomerIsCreated_thenSuccessMessage() {
        // mock
        /*CustomerService customerServiceMock = mock(CustomerService.class);
        CustomerDTO customerDTOMock = mock(CustomerDTO.class);
        Customer customerMock = null;
        Mockito.when(customerRepository.findByName(customerDTOMock.getName())).thenReturn(customerMock);
        BDDMockito.given(customerServiceMock.addCustomer(customerDTOMock)).willThrow(
                new CustomerAlreadyExistsException(HttpStatus.CONFLICT,
                MessageConstants.ErrorMessages.CUSTOMER_ALREADY_EXISTS)
                );*/
        // real test

    }
}
//        customerDTO.getName() != null &&
//        customerDTO.getDescription() != null &&
//        customerDTO.getEmail() != null &&
//        customerDTO.getAddress() != null &&