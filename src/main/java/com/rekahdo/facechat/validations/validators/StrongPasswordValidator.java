package com.rekahdo.facechat.validations.validators;

import com.rekahdo.facechat.validations.annotations.StrongPassword;
import com.rekahdo.facechat.validations.services.StrongPasswordValidationService;

public class StrongPasswordValidator extends Api_Validator<StrongPassword, String> {

    public StrongPasswordValidator() {
        super(new StrongPasswordValidationService());
    }

}
