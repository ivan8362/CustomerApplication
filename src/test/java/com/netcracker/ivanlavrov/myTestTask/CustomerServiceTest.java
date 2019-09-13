package com.netcracker.ivanlavrov.myTestTask;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerAlreadyExistsException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.impl.CustomerService;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
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
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceTestConfiguration.class})
@SpringBootTest(classes = MyTestTaskApplication.class)
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

    /*@Test(expected = CustomerAlreadyExistsException.class)
    public void whenCustomerIsCreated_thenSuccessMessage() {
        Customer customerMock = null;

        Mockito.when(customerRepositoryMock.findByName(customerDTOMock.getName())).thenReturn(customerMock);
        willThrow(new CustomerAlreadyExistsException(
                HttpStatus.CONFLICT,
                MessageConstants.ErrorMessages.CUSTOMER_ALREADY_EXISTS)
                ).given(customerServiceMock)
                .addCustomer(customerDTOMock);
        // Act
        try {
            customerServiceMock.addCustomer(customerDTOMock);
            fail("Should throw exception");
        } catch (CustomerAlreadyExistsException ex) {}
        // Assert
        then(customerRepositoryMock).should(never()).insert(customerMock);
    }*/

    @Test
    public void findAll_CustomersFound_ShouldReturnFoundCustomerEntries() throws Exception {
        Customer first = new Customer("name", "description", "email", "address");
        Customer second = new Customer("name1", "description1", "email1", "address1");

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(first);
        customers.add(second);
        when(customerServiceMock.findAll()).thenReturn(customers.toString());

        mockMvc.perform(get("api/v1/customers/"))
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

        verify(customerServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getById_CustomerEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(customerServiceMock.getCustomerById("1")).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageConstants.ErrorMessages.CUSTOMER_DOES_NOT_EXIST));

        mockMvc.perform(get("/api/v1/customers/{id}", "1"))
                .andExpect(status().isNotFound());

        verify(customerServiceMock, times(1)).getCustomerById("1");
        verifyNoMoreInteractions(customerServiceMock);
    }

    @Test
    public void getById_CustomerEntryFound_ShouldReturnFoundCustomerEntry() throws Exception {
        Customer found = new Customer("name", "description", "email", "address");

        when(customerServiceMock.getCustomerById("1")).thenReturn(found);

        mockMvc.perform(get("/api/v1/customers/{id}", "1"))
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
//        customerDTO.getName() != null &&
//        customerDTO.getDescription() != null &&
//        customerDTO.getEmail() != null &&
//        customerDTO.getAddress() != null &&