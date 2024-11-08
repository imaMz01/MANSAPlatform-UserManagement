package com.mansa.user.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    TOKEN_INVALID("Token invalid"),
    EMAIL_ALREADY_VERIFIED("Email %s already verified"),
    TOKEN_EXPIRED("The token has expired"),
    EMAIL_ALREADY_EXIST("Email %s already exist"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    USER_NOT_FOUND("User with id %s not found");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
