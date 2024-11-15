package com.mansa.user.Exceptions;

public class UserHasAlreadyThisRoleExistException extends RuntimeException {
    public UserHasAlreadyThisRoleExistException(String id, String role) {
        super(ExceptionMessages.USER_HAS_ALREADY_THIS_ROLE.getMessage(id,role));
    }
}
