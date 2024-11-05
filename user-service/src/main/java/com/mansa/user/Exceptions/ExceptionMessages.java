package com.mansa.user.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    USER_NOT_FOUND("User with id %s not found");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
