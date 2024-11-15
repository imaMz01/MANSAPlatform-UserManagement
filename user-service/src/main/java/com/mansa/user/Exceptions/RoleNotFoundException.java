package com.mansa.user.Exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(ExceptionMessages.ROLE_NOT_FOUND.getMessage());
    }
}