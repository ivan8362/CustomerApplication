package com.netcracker.ivanlavrov.myTestTask.web.transformer;

import com.netcracker.ivanlavrov.myTestTask.domain.Customer;
import com.netcracker.ivanlavrov.myTestTask.web.dto.CustomerDTO;

public class DTOToDomainTransformer {

    public static Customer transform(final CustomerDTO customerDTO) {
        return new Customer(customerDTO.getName(),
                customerDTO.getDescription(),
                customerDTO.getEmail(),
                customerDTO.getAddress());
    }
}
