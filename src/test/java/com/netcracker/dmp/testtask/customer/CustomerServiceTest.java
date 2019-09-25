package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.clients.EmployeeClient;
import com.netcracker.dmp.testtask.customer.config.CustomerServiceTestConfiguration;
import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerAlreadyExistsException;
import com.netcracker.dmp.testtask.customer.exceptions.CustomerNotFoundException;
import com.netcracker.dmp.testtask.customer.repositories.CustomerRepository;
import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceTestConfiguration.class})
@TestPropertySource(properties = {"employee.host=127.0.0.1", "employee.port=8090"})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @Test
    public void testCreateCustomer() {
        String name = "Test Customer";
        String description = "Test Customer for customer application.";
        String email = "test@gmail.com";
        String address = "2 Oxford st. London UK";

        Customer customer = customerService.createCustomer(name, description, email, address);

        verify(customerRepository).findByEmail(email);
        assertEquals("Name property value does not match an expected value.", name, customer.getName());
        assertEquals("Description property value does not match an expected value.",
                description, customer.getDescription());
        assertEquals("Email property value does not match an expected value.", email, customer.getEmail());
        assertEquals("Address property value does not match an expected value.", address, customer.getAddress());
    }

    @Test(expected = CustomerAlreadyExistsException.class)
    public void createCustomerExceptionTest() {
        String name = "Test Customer";
        String description = "Test Customer for customer application.";
        String email = "test@gmail.com";
        String address = "2 Oxford st. London UK";
        Customer customer = new Customer(name, description, email, address);

        Optional<Customer> customerOptional = Optional.of(customer);
        Mockito.when(customerRepository.findByEmail(email)).thenReturn(customerOptional);

        customerService.createCustomer(name, description, email, address);
    }

    @Test
    public void getAll_CustomersFound_ShouldReturnFoundCustomerEntries() {
        Customer customerFirst = new Customer("Test customer 1",
                "Test customer 1 description", "c1@gmail.com",
                "2 Oxford st. London");
        Customer customerSecond = new Customer("Test customer 2",
                "Test customer 2 description", "c2@gmail.com",
                "3 Oxford st. London");

        List<Customer> customers = new ArrayList<>();
        customers.add(customerFirst);
        customers.add(customerSecond);

        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> allCustomers = customerService.getAllCustomers();

        verify(customerRepository).findAll();
        assertArrayEquals("The list of customers given to the method does not match returned list",
                customers.toArray(), allCustomers.toArray());
    }

    @Test
    public void getById_CustomerEntryFound() {
        // Arrange.
        Customer customer = new Customer("Test customer 1",
                "Test customer 1 description", "c1@gmail.com",
                "2 Oxford st. London");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        when(customerRepository.findById("1")).thenReturn(optionalCustomer);

        // Act.
        Customer customerActual = customerService.getCustomerById("1");

        // Assert.
        verify(customerRepository, times(1)).findById("1");

        assertEquals("The list of customers given to the method does not match returned list",
                customer, customerActual);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void getByID_CustomerEntryNotFoundTest() {
        Optional<Customer> customerOptional = Optional.empty();
        Mockito.when(customerRepository.findById("1")).thenReturn(customerOptional);
        // Act
        customerService.getCustomerById("1");
    }

    @Test
    public void updateCustomerTest() {
        String id = "1";
        String name = "Test Customer";
        String description = "Test Customer for customer application.";
        String email = "test@gmail.com";
        String address = "2 Oxford st. London UK";
        Customer tempCustomer = new Customer(name, description, email, address);
        tempCustomer.setId(id);
        Optional<Customer> optionalCustomer = Optional.of(tempCustomer);

        Mockito.when(customerRepository.findById(id)).thenReturn(optionalCustomer);
        Mockito.when(customerRepository.save(tempCustomer)).thenReturn(tempCustomer);

        // Act
        Customer customer = customerService.updateCustomer(id, name, description, email, address);

        verify(customerRepository).findById(id);
        assertEquals("Name property value does not match an expected value.", name, customer.getName());
        assertEquals("Description property value does not match an expected value.",
                description, customer.getDescription());
        assertEquals("Email property value does not match an expected value.", email, customer.getEmail());
        assertEquals("Address property value does not match an expected value.", address, customer.getAddress());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void updateCustomerExceptionTest() {
        String id = "1";
        String name = "Test Customer";
        String description = "Test Customer for customer application.";
        String email = "test@gmail.com";
        String address = "2 Oxford st. London UK";

        Optional<Customer> customerOptional = Optional.empty();
        Mockito.when(customerRepository.findById(id)).thenReturn(customerOptional);

        customerService.updateCustomer(id, name, description, email, address);
    }

    @Test
    public void delete_CustomerEntryFound() {
        // Arrange.
        Customer customer = new Customer("Test customer 1",
                "Test customer 1 description", "c1@gmail.com",
                "2 Oxford st. London");
        customer.setId("1");
        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(customerRepository.findById("1")).thenReturn(optionalCustomer);

        // Act.
        customerService.deleteCustomer("1");

        // Assert.
        verify(employeeClient).deleteAllEmployeesForCustomer("1");
        verify(customerRepository).deleteById("1");
    }
}
