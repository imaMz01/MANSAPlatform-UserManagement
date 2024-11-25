package com.mansa.user.Exceptions;

public class InvitationNotFountException extends RuntimeException {
    public InvitationNotFountException(String id) {
        super(ExceptionMessages.INVITATION_NOT_FOUND.getMessage(id));
    }
}