package com.netcracker.dmp.testtask.customer;

import com.netcracker.dmp.testtask.customer.services.impl.CustomerService;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

@Profile("test")
@Configuration
public class CustomerServiceTestConfiguration {

    // TODO find out why this is needed.
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages"); // TODO find out what id does.
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    @Primary
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
