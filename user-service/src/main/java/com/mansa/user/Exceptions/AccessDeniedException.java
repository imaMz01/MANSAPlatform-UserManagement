package com.mansa.user.Exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super(ExceptionMessages.ACCESS_DENIED.getMessage());
    }
}