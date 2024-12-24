package com.mansa.company.Exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(ExceptionMessages.DATA_NOT_FOUND.getMessage(message));
    }
}
