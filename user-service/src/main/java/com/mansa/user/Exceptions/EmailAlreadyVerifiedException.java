package com.mansa.user.Exceptions;

public class EmailAlreadyVerifiedException extends RuntimeException {
    public EmailAlreadyVerifiedException(String email) {
        super(ExceptionMessages.EMAIL_ALREADY_VERIFIED.getMessage(email));
    }
}
