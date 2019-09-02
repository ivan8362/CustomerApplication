package com.netcracker.ivanlavrov.myTestTask.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResponseDTO {

    @NotNull
    @JsonProperty("status")
    private Status status;

    @JsonProperty("message")
    private String message;

    public ResponseDTO(@JsonProperty("status") Status status, @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }

    public enum Status {
        SUCCESS, FAIL
    }
}
