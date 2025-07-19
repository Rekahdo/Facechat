package com.rekahdo.facechat.validations.annotations;

import com.rekahdo.facechat.validations.validators.RoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

    String message() default "Valid Role(s) Provider";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}