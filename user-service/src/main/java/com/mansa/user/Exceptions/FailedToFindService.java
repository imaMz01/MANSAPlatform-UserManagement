package com.mansa.user.Exceptions;

public class FailedToFindService extends RuntimeException {
    public FailedToFindService() {
        super(ExceptionMessages.FAILED_TO_FIND_SERVICE.getMessage());
    }
}