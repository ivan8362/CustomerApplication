package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceTestConfiguration.class})
@WebAppConfiguration
public class CustomerServiceTestAdd {
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerServiceMock;

    /*@Test
    public void add_NewCustomerEntry_ShouldAddCustomerEntryAndReturnJson() throws Exception {
        CustomerDTO dto = new CustomerDTO("name", "description", "email", "address");

//        when(customerServiceMock.createCustomer(any(CustomerDTO.class))).thenReturn(added);

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtil.convertObjectToJsonBytes(dto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("Customer successfully added.")));

        ArgumentCaptor<CustomerDTO> dtoCaptor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(customerServiceMock, times(1)).createCustomer(dtoCaptor.capture());
        verifyNoMoreInteractions(customerServiceMock);

        CustomerDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getDescription(), is("description"));
        assertThat(dtoArgument.getTitle(), is("title"));
    }*/
}
