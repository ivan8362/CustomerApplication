package com.netcracker.ivanlavrov.myTestTask.service.impl;

import com.netcracker.ivanlavrov.myTestTask.constants.MessageConstants.ErrorMessages;
import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.exception.CustomerManagementException;
import com.netcracker.ivanlavrov.myTestTask.repository.CustomerRepository;
import com.netcracker.ivanlavrov.myTestTask.service.CustomerService;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;
import com.netcracker.ivanlavrov.myTestTask.web.transformer.DTOToDomainTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(CustomerDTO customer) {
        Customer dbCustomer = customerRepository.findByName(customer.getName());

        if (dbCustomer != null) {
            throw new CustomerManagementException(ErrorMessages.CUSTOMER_ALREADY_EXISTS);
        }
        Customer customerToPersist = DTOToDomainTransformer.transform(customer);
        customerRepository.insert(customerToPersist);
    }

    public void updateCustomer(CustomerDTO customer) {
        Customer dbCustomer = customerRepository.findByName(customer.getName());
        if (dbCustomer == null) {
            throw new CustomerManagementException(ErrorMessages.CUSTOMER_DOES_NOT_EXISTS);
        }
        Customer customerToPersist = DTOToDomainTransformer.transform(customer);
        customerRepository.updateCustomer(customerToPersist);
    }
}
