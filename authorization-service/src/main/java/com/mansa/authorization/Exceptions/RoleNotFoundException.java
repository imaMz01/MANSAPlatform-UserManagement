package com.mansa.authorization.Exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String id) {
        super(ExceptionMessages.ROLE_NOT_FOUND.getMessage(id));
    }
}
