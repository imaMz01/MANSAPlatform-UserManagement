package com.mansa.subscription.Exceptions;



public class SubscriptionNotFound extends RuntimeException {
    public SubscriptionNotFound(String id) {
        super(ExceptionMessages.SUBSCRIPTION_NOT_FOUND.getMessage(id));
    }
}