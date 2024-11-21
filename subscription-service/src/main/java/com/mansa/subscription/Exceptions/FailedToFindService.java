package com.mansa.subscription.Exceptions;

public class FailedToFindService extends RuntimeException {
    public FailedToFindService() {
        super(ExceptionMessages.FAILED_TO_FIND_SERVICE.getMessage());
    }
}