package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.clients.EmployeeClient;
import com.netcracker.dmp.testtask.customer.constants.MessageConstants;
import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerAlreadyExistsException;
import com.netcracker.dmp.testtask.customer.repositories.CustomerRepository;
import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceTestConfiguration.class})
@SpringBootTest(classes = CustomerApplication.class)
@WebAppConfiguration
public class CustomerServiceTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Mock
    private CustomerService customerServiceMock;

    @Autowired
    private CustomerRepository customerRepositoryMock;

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private CustomerDTO customerDTOMock;

    // Not sure if it's needed.
    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(customerServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Before // arrange
    public void init() {
        customerServiceMock = mock(CustomerService.class);
        customerDTOMock = mock(CustomerDTO.class);
        customerRepositoryMock = mock(CustomerRepository.class);
    }

    @Test(expected = CustomerAlreadyExistsException.class)
    public void whenCustomerIsCreated_thenSuccessMessage() {
        Customer customerMock = null;
        Optional<Customer> optionalCustomer = new Optional<>(customerMock);

        Mockito.when(customerRepositoryMock.findByEmail(customerDTOMock.getEmail())).thenReturn(optionalCustomer);
        willThrow(new CustomerAlreadyExistsException(anyString()))
                .given(customerServiceMock)
                .createCustomer(
                        customerDTOMock.getName(),
                        customerDTOMock.getDescription(),
                        customerDTOMock.getEmail(),
                        customerDTOMock.getAddress());
        // Act
        try {
            customerServiceMock.createCustomer(
                    customerDTOMock.getName(),
                    customerDTOMock.getDescription(),
                    customerDTOMock.getEmail(),
                    customerDTOMock.getAddress());
            fail("Should throw exceptions");
        } catch (CustomerAlreadyExistsException ex) {}
        // Assert
        then(customerRepositoryMock).should(never()).insert(customerMock);
    }

    @Test
    public void findAll_CustomersFound_ShouldReturnFoundCustomerEntries() throws Exception {
        Customer first = new Customer("name", "description", "email", "address");
        Customer second = new Customer("name1", "description1", "email1", "address1");

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(first);
        customers.add(second);
        when(customerServiceMock.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].email", is("email")))
                .andExpect(jsonPath("$[0].address", is("address")))
                .andExpect(jsonPath("$[1].name", is("name1")))
                .andExpect(jsonPath("$[1].description", is("description1")))
                .andExpect(jsonPath("$[1].email", is("email1")))
                .andExpect(jsonPath("$[1].address", is("address1")));

        verify(customerServiceMock, times(1)).getAllCustomers();
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getById_CustomerEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(customerServiceMock.getCustomerById("1")).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageConstants.ErrorMessages.CUSTOMER_DOES_NOT_EXIST));

        mockMvc.perform(get("/v1/customers/{id}", "1"))
                .andExpect(status().isNotFound());

        verify(customerServiceMock, times(1)).getCustomerById("1");
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getById_CustomerEntryFound_ShouldReturnFoundCustomerEntry() throws Exception {
        Customer found = new Customer("name", "description", "email", "address");

        when(customerServiceMock.getCustomerById("1")).thenReturn(found);

        mockMvc.perform(get("/v1/customers/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.address", is("address")));

        verify(customerServiceMock, times(1)).getCustomerById("1");
        verifyNoMoreInteractions(customerServiceMock);
    }
}