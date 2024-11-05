package com.mansa.user.Exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super(ExceptionMessages.USER_NOT_FOUND.getMessage(id));
    }
}
