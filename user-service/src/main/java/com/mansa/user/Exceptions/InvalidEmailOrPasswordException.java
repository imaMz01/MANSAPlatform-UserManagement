package com.mansa.user.Exceptions;

public class InvalidEmailOrPasswordException extends RuntimeException {
    public InvalidEmailOrPasswordException() {
        super(ExceptionMessages.INVALID_EMAIL_OR_PASSWORD.getMessage());
    }
}
