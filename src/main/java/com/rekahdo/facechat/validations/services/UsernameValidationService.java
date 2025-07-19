package com.rekahdo.facechat.validations.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.passay.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsernameValidationService implements Api_ValidationService<String>{

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMin = 3;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int lenMax = 15;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int startWith = 2;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int alphabetCount = 3;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final int digitCount = 0;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final String regex = String.format("^[a-zA-Z].{%d,}", startWith);

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final PasswordValidator usernameValidator;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private RuleResult result;

    public UsernameValidationService() {
        usernameValidator = new PasswordValidator(
                new LengthRule(lenMin, lenMax),
                new CharacterRule(EnglishCharacterData.Alphabetical, alphabetCount),
                new AllowedRegexRule(regex),
                new WhitespaceRule()
        );
    }

    @Override
    public List<String> validate(String username) {
        errors.clear();
        if(username == null) return errors;

        result = usernameValidator.validate(new PasswordData(username));
        if(!result.isValid()) {
            result.getDetails().forEach(detail -> {
                String errorCode = detail.getErrorCode();
                String message = null;

                if(errorCode.equals(LengthRule.ERROR_CODE_MIN) || errorCode.equals(LengthRule.ERROR_CODE_MAX))
                    message = String.format("Username should be between %d and %d characters long", lenMin, lenMax);

                else if (errorCode.equals(EnglishCharacterData.Alphabetical.getErrorCode()))
                    message = String.format("Username should at least contain %d alphabet letters", alphabetCount);

                else if (errorCode.equals(AllowedRegexRule.ERROR_CODE))
                    message = String.format("Username should start with %d or more alphabet letters", startWith+1);

                else if (errorCode.equals(WhitespaceRule.ERROR_CODE))
                    message = "Username should not contain whitespace";

                if (message != null) errors.add(message);
            });
        }

        return errors;
    }

}
