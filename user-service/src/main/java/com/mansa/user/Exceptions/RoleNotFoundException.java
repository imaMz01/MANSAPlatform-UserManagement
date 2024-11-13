package com.mansa.user.Exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super(ExceptionMessages.ROLE_NOT_FOUND.getMessage(role));
    }
}