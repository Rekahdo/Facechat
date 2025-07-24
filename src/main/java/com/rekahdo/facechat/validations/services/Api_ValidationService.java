package com.rekahdo.facechat.validations.services;

import com.rekahdo.facechat._dtos.entities.AppUserDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public interface Api_ValidationService<T> {

    List<String> errors = new ArrayList<>();

    List<String> validate(T value);

    default void throwMethodArgumentNotValidException(List<String> errors, Class<?> callingClass, String callingMethodName, AppUserDto dto, String dtoBeanName,
            String updatingFieldName, Class<?>... methodCalledParameterTypes)  throws MethodArgumentNotValidException, NoSuchMethodException {

            MethodParameter parameter = new MethodParameter(
                    callingClass.getDeclaredMethod(callingMethodName, methodCalledParameterTypes),
                    methodCalledParameterTypes.length-1
            );

            BindingResult bindingResult = new BeanPropertyBindingResult(dto, dtoBeanName);

            errors.forEach(error -> {
                bindingResult.rejectValue(updatingFieldName, String.format("Invalid-%s", updatingFieldName), error);
            });

            throw new MethodArgumentNotValidException(parameter, bindingResult);

    };

}
