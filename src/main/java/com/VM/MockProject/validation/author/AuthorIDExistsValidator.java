package com.VM.MockProject.validation.author;

import com.VM.MockProject.Service.Interface.IAuthorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class AuthorIDExistsValidator implements ConstraintValidator<AuthorIDExists, Integer> {

    @Autowired
    private IAuthorService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(id)) {
            return true;
        }

        return service.isAuthorExistsByID(id);
    }
}