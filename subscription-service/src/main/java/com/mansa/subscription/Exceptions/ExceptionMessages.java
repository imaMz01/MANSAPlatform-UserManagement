package com.mansa.subscription.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    FAILED_TO_FIND_SERVICE("Oups something wrong, try later"),
    SUBSCRIPTION_NOT_FOUND("Subscription with id %s not found");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
