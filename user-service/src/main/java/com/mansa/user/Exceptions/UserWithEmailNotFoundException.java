package com.mansa.user.Exceptions;

public class UserWithEmailNotFoundException extends RuntimeException {
    public UserWithEmailNotFoundException(String email) {
        super(ExceptionMessages.USER_WITH_EMAIL_NOT_FOUND.getMessage(email));
    }
}