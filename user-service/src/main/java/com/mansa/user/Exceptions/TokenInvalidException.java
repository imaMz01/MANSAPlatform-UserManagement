package com.mansa.user.Exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super(ExceptionMessages.TOKEN_INVALID.getMessage());
    }
}
