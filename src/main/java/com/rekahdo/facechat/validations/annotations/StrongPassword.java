package com.rekahdo.facechat.validations.annotations;

import com.rekahdo.facechat.validations.validators.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "Password is not strong enough";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}