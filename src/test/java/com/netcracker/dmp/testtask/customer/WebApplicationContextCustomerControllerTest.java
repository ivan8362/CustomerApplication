package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import com.netcracker.dmp.testtask.customer.repositories.CustomerRepository;
import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@ContextConfiguration(locations = {"classpath:testContext.xml", "classpath:exampleApplicationContext-web.xml"})
@WebAppConfiguration
public class WebApplicationContextCustomerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerDTO customerDTOMock;

    @Autowired
    private CustomerRepository customerRepositoryMock;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(customerServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        customerServiceMock = mock(CustomerService.class);
        customerDTOMock = mock(CustomerDTO.class);
        customerRepositoryMock = mock(CustomerRepository.class);
    }
}
