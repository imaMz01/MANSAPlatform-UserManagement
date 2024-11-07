package com.mansa.user.Exceptions;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String email) {
        super(ExceptionMessages.EMAIL_ALREADY_EXIST.getMessage(email));
    }
}
