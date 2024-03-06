package com.ecm.productscommand.exceptions;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    public String message;
    public HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}