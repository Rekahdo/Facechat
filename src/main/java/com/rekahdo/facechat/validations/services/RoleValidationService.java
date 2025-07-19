package com.rekahdo.facechat.validations.services;

import com.rekahdo.facechat.enums.AuthorityRole;
import com.rekahdo.facechat.utilities.StringFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleValidationService implements Api_ValidationService<String>{

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private List<String> validRoles;

    @Value("${adminKey}")
    private String adminKey;

    public RoleValidationService() {
        this.validRoles = new ArrayList<>(Arrays.stream(AuthorityRole.values()).map(AuthorityRole::getValue).toList());
        this.validRoles.remove(AuthorityRole.USER.getValue());
    }

    @Override
    public List<String> validate(String roles) {
        errors.clear();
        if(roles == null) return errors;

        Arrays.stream(StringFormat.split(roles))
                .forEach(role -> {
                    if(!validRoles.contains(role))
                        errors.add(String.format("Invalid Role: '%s'. Valid Roles Are: %s. Is Case Sensitive!!!", role, validRoles));
                });

        return errors;
    }

}
