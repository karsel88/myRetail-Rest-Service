package com.myRetail.demo.restservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@AllArgsConstructor
public class ProductException extends RuntimeException {

    public ProductException(String message, String id ) {
        super(String.format(message + id));
    }
}
