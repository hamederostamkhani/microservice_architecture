package com.javagym.api_gateway.exception;

import lombok.Data;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class MSNotFound extends RuntimeException {
    private int errorCode = HttpStatus.SC_NOT_FOUND;
    private String errorMessage = "Some of the microservices not found";
}

