package com.mansa.authorization.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    ROLE_NOT_FOUND("Role not found"),
    ROLE_ALREADY_EXIST("Role %s already exist");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
