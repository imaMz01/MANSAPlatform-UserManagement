package com.mansa.user.Exceptions;

public class TokenExpiredHandlerException extends RuntimeException {
    public TokenExpiredHandlerException() {
        super(ExceptionMessages.YOUR_TOKEN_EXPIRED.getMessage());
    }
}