package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.config.CustomerServiceTestConfiguration;
import com.netcracker.dmp.testtask.customer.controllers.dto.CustomerDTO;
import com.netcracker.dmp.testtask.customer.entities.Customer;
import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceTestConfiguration.class, WebAppContext.class})
@WebAppConfiguration
public class CustomerServiceTestAdd {
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerServiceMock;

    @Test
    public void add_NewCustomerEntry_ShouldAddCustomerEntryAndReturnJson() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("name", "description", "email", "address");

        Customer added = new Customer("name", "description", "email", "address");
        when(customerServiceMock.createCustomer(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(added);

        mockMvc.perform(post("/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtil.convertObjectToJsonBytes(customerDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(any(String.class))))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.address", is("address")));

        ArgumentCaptor<CustomerDTO> dtoCaptor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(customerServiceMock, times(1)).createCustomer(
                dtoCaptor.capture().getName(),
                dtoCaptor.capture().getDescription(),
                dtoCaptor.capture().getEmail(),
                dtoCaptor.capture().getAddress());
        verifyNoMoreInteractions(customerServiceMock);

        CustomerDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getDescription(), is("description"));
        assertThat(dtoArgument.getName(), is("name"));
        assertThat(dtoArgument.getEmail(), is("email"));
        assertThat(dtoArgument.getAddress(), is("address"));
    }
}
