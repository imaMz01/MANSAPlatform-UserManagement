package com.mansa.company.Exceptions;

public class YouAreNotAuthorizedException extends RuntimeException {
    public YouAreNotAuthorizedException() {
        super(ExceptionMessages.YOU_ARE_NOT_AUTHORIZED_TO_MAKE_THIS_ACTION.getMessage());
    }
}
