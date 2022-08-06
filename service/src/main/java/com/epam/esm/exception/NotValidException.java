package com.epam.esm.exception;


public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
