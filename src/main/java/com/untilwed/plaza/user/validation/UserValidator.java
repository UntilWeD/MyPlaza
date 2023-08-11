package com.untilwed.plaza.user.validation;

import com.untilwed.plaza.user.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        //username
        if(user.getUsername().length() > 10 || user.getUsername().length() <= 0){
            errors.rejectValue("username", "length");
        }

        //id
        if(user.getId().length() >= 10 || user.getId().length() <= 1){
            errors.rejectValue("id", "length", new Object[]{1, 10}, "");
        }

        //pasword
        if(user.getPassword().length() > 20 || user.getPassword().length()  < 6){
            errors.rejectValue("password", "length");
        }

        //TODO
        //email

        //전체 검증
        if(user.getUsername() == "" || user.getId() == "" || user.getPassword() == "" ||
                user.getEmail() == "" ){
            errors.reject("totaluser");
        }
    }
}
