package com.mansa.company.Exceptions;

public class CompanyNotFountException extends RuntimeException {
    public CompanyNotFountException(String id) {
        super(ExceptionMessages.COMPANY_NOT_FOUND.getMessage(id));
    }
}
