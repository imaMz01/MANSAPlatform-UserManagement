package com.mansa.user.Annotations;

import com.mansa.user.Validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "Invalid password!, your password must be between 8 and" +
            " 20 characters long, include at least one uppercase letter," +
            " one lowercase letter, and one number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
