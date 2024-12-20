package com.mansa.company.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    COMPANY_ALREADY_EXIST("Company with name %s already exist."),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later."),
    COMPANY_NOT_FOUND("Company with id %s not found.");

    private final String message;

    public String getMessage(){
        return message;
    }

    public String getMessage(Object... args){
        return String.format(message,args);
    }

}
