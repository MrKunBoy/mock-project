package com.VM.MockProject.validation.user;

import com.VM.MockProject.Service.Interface.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UserPhoneNotExistsValidator implements ConstraintValidator<UserPhoneNotExists, String> {

    @Autowired
    private IUserService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(phone)) {
            return true;
        }

        return !service.isUserExistsByPhone(phone);
    }

}