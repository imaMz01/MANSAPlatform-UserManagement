package com.mansa.user.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    ROLE_NOT_FOUND("Role not found"),
    INVITATION_ALREADY_ACCEPTED("Invitation already accepted"),
    INVITATION_NOT_FOUND("Invitation with id %s not found"),
    USER_HAS_ALREADY_THIS_ROLE("user with id %s already has the role %s"),
    TOKEN_INVALID("Token invalid"),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later"),
    EMAIL_ALREADY_VERIFIED("Email %s already verified"),
    TOKEN_EXPIRED("Your token has expired. A new verification link has been sent to your email."),
    EMAIL_ALREADY_EXIST("Email %s already exist"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    EMAIL_NOT_VERIFIED("Your email is not verified yet. Please check your inbox and follow the instructions to complete the verification process."),
    USER_NOT_FOUND("User with id %s not found"),
    YOUR_TOKEN_EXPIRED("Your token has expired."),
    ACCESS_DENIED("You do not have permission to access this resource."),
    ROLE_ALREADY_EXIST("Role %s already exist");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
