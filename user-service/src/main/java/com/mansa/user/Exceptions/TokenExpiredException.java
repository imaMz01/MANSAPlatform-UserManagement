package com.mansa.user.Exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super(ExceptionMessages.TOKEN_EXPIRED.getMessage());
    }
}