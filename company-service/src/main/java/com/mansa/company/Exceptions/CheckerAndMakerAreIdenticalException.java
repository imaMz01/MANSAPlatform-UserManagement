package com.mansa.company.Exceptions;

public class CheckerAndMakerAreIdenticalException extends RuntimeException {
    public CheckerAndMakerAreIdenticalException() {
        super(ExceptionMessages.CHECKER_AND_MAKER_ARE_IDENTICAL.getMessage());
    }
}
