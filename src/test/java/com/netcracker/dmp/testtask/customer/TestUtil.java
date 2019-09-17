package com.netcracker.dmp.testtask.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

import java.io.IOException;

public class TestUtil {
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // When writing to json strings/bytes nulls will be ignored.
        return mapper.writeValueAsBytes(object);
    }
}
