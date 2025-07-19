package com.rekahdo.facechat.validations.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.passay.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrongPasswordValidationService implements Api_ValidationService<String> {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMin = 8;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMax = 18;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int alphabetCount = 5;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int uppercaseCount = 2;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int digitCount = 1;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int specialCount = 1;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final PasswordValidator passwordValidator;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private RuleResult result;

    public StrongPasswordValidationService() {
        passwordValidator = new PasswordValidator(
                new LengthRule(lenMin, lenMax),
                new CharacterRule(EnglishCharacterData.Alphabetical, alphabetCount),
                new CharacterRule(EnglishCharacterData.UpperCase, uppercaseCount),
                new CharacterRule(EnglishCharacterData.Digit, digitCount),
                new CharacterRule(EnglishCharacterData.Special, specialCount),
                new WhitespaceRule()); // No whitespace;
    }

    @Override
    public List<String> validate(String password) {
        errors.clear();
        if(password == null) return errors;

        result = passwordValidator.validate(new PasswordData(password));

        if(!result.isValid()) {
            result.getDetails().forEach(detail -> {
                String errorCode = detail.getErrorCode();
                String message = null;

                if (errorCode.equals(LengthRule.ERROR_CODE_MIN) || errorCode.equals(LengthRule.ERROR_CODE_MAX))
                    message = String.format("Password should be between %d and %d characters long", lenMin, lenMax);

                else if (errorCode.equals(EnglishCharacterData.Alphabetical.getErrorCode()))
                    message = String.format("Password should at least contain %d alphabet letters", alphabetCount);

                else if (errorCode.equals(EnglishCharacterData.UpperCase.getErrorCode()))
                    message = String.format("Password should at least contain %d uppercase letter", uppercaseCount);

                else if (errorCode.equals(EnglishCharacterData.Digit.getErrorCode()))
                    message = String.format("Password should at least contain %d digit", digitCount);

                else if (errorCode.equals(EnglishCharacterData.Special.getErrorCode()))
                    message = String.format("Password should at least contain %d special character", specialCount);

                else if (errorCode.equals(WhitespaceRule.ERROR_CODE))
                    message = "Password should not contain whitespace";

                if (message != null) errors.add(message);
            });
        }

        return errors;
    }

}
