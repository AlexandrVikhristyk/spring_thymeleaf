package com.example.demo.service.validation;

import com.example.demo.entity.CustomUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.validation.Validator;

@Service
public class UserValidator implements org.springframework.validation.Validator {

    private Validator validator;
    private String passwordPattern;
    private String emailPattern;

    public UserValidator(Validator validator) {
        this.validator = validator;
        passwordPattern = "^(?=(.*[a-z])+)(?=(.*[\\d])+)(?=(.*[\\W])+)(?!.*\\s).{7,30}$";
        emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Set<ConstraintViolation<Object>> validates = validator.validate(o);
//
//        for (ConstraintViolation<Object> constraintViolation : validates) {
//            String propertyPath = constraintViolation.getPropertyPath().toString();
//            String message = constraintViolation.getMessage();
//            errors.rejectValue(propertyPath, "", message);
//        }

        CustomUser user = (CustomUser) o;

        if (!user.getEmail().matches(emailPattern)){
            errors.rejectValue("email", "email.valid");
        } else if (!user.getPassword().matches(passwordPattern)){
            errors.rejectValue("password", "password.valid");
        } else if (!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password", "password.dosentmutch");
        }
    }
}
