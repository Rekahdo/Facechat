package com.rekahdo.facechat.validations.validators;

import com.rekahdo.facechat.validations.annotations.Role;
import com.rekahdo.facechat.validations.services.RoleValidationService;

public class RoleValidator extends Api_Validator<Role, String> {

    public RoleValidator() {
        super(new RoleValidationService());
    }

}