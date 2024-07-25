package com.VM.MockProject.validation.publisher;

import com.VM.MockProject.Service.Interface.IPublisherService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class PublisherIDExistsValidator implements ConstraintValidator<PublisherIDExists, Integer> {

    @Autowired
    private IPublisherService service;

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(id)) {
            return true;
        }

        return service.isPublisherExistsByID(id);
    }
}