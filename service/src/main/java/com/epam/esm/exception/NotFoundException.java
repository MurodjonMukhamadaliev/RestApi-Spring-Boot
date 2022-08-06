package com.epam.esm.exception;



public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
