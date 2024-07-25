package com.VM.MockProject.validation.user;

import com.VM.MockProject.Service.Interface.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UsernameNotExistsValidator implements ConstraintValidator<UsernameNotExists, String> {

    @Autowired
    private IUserService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(username)) {
            return true;
        }

        return !service.isUserExistsByUsername(username);
    }

}
