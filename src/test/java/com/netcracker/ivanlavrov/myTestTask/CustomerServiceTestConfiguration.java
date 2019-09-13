package com.netcracker.ivanlavrov.myTestTask;

import com.netcracker.ivanlavrov.myTestTask.service.impl.CustomerService;
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

    // Not sure why this is needed.
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    @Primary
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
