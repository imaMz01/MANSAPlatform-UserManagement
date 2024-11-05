package com.mansa.user.Validation;

import com.mansa.user.Annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(Objects.equals(value, "")){
            return false;
        }
        if(value.length()<8 || value.length()>20){
            return false;
        }
        return value.chars().anyMatch(Character::isUpperCase) &&
                value.chars().anyMatch(Character::isLowerCase) &&
                value.chars().anyMatch(Character::isDigit);
    }
}
