package com.VM.MockProject.validation.user;

import com.VM.MockProject.Service.Interface.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UserIDExistsValidator implements ConstraintValidator<UserIDExists, Integer> {

    @Autowired
    private IUserService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(id)) {
            return true;
        }

        return service.isUserExistsByID(id);
    }
}