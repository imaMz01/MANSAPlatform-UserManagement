package com.mansa.authorization.Exceptions;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String role) {
        super(ExceptionMessages.ROLE_ALREADY_EXIST.getMessage(role));
    }
}
