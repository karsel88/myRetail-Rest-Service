package com.myRetail.demo.restservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message, String id ) {
        super(String.format(message + id));
    }
}
