package com.rekahdo.facechat.validations.validators;

import com.rekahdo.facechat.validations.services.Api_ValidationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.List;

@Getter
public abstract class Api_Validator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    Api_ValidationService<T> validationService;
    ConstraintValidatorContext context;

    public Api_Validator(Api_ValidationService<T> validationService) {
        this.validationService = validationService;
    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        this.context = context;
        List<String> errors = validationService.validate(value);

        if(!errors.isEmpty()) {
            errors.forEach(error -> {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            });
            return false;
        }

        return true;
    }

}
