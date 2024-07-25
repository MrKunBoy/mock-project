package com.VM.MockProject.validation.user;

import com.VM.MockProject.Service.Interface.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UserEmailNotExistsValidator implements ConstraintValidator<UserEmailNotExists, String> {

    @Autowired
    private IUserService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(email)) {
            return true;
        }

        return !service.isUserExistsByEmail(email);
    }
}
