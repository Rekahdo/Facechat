package com.rekahdo.facechat.validations.validators;

import com.rekahdo.facechat.validations.annotations.Username;
import com.rekahdo.facechat.validations.services.UsernameValidationService;

public class UsernameValidator extends Api_Validator<Username, String> {

    public UsernameValidator() {
        super(new UsernameValidationService());
    }

}
