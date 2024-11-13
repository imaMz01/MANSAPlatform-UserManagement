package com.mansa.user.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    ROLE_NOT_FOUND("Role %s already exist"),
    ROLE_ALREADY_EXIST("user with id %s already has the role %s"),
    TOKEN_INVALID("Token invalid"),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later"),
    EMAIL_ALREADY_VERIFIED("Email %s already verified"),
    TOKEN_EXPIRED("Your token has expired. A new verification link has been sent to your email."),
    EMAIL_ALREADY_EXIST("Email %s already exist"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    EMAIL_NOT_VERIFIED("Your email is not verified yet. Please check your inbox and follow the instructions to complete the verification process."),
    USER_NOT_FOUND("User with id %s not found");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
