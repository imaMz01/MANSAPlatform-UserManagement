package com.mansa.company.Exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    COMPANY_ALREADY_EXIST("Company with name %s already exist."),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later."),
    COMPANY_NOT_FOUND("Company with %s not found."),
    CHECKER_AND_MAKER_ARE_IDENTICAL("You cannot act as both the maker and the checker for the same data."),
    YOU_ARE_NOT_AUTHORIZED_TO_MAKE_THIS_ACTION("You are not authorized to make this action."),
    DATA_NOT_FOUND("Data with %s not found.");

    private final String message;

    public String getMessage(){
        return message;
    }

    public String getMessage(Object... args){
        return String.format(message,args);
    }

}
