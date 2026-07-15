package com.javagym.microservice_three.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestAPIException extends RuntimeException {
    private HttpStatus errorCode;
    private String errorMessage;
    private List<String> errors;
}
