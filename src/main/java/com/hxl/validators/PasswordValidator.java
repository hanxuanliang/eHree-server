package com.hxl.validators;

import com.hxl.dto.PersonDTO;
import com.hxl.validators.annotations.PasswordRepeatEqual;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description: 密码 验证器
 * @Author: hanxuanliang
 * @Date: 2020/2/5 20:34
 */
public class PasswordValidator implements ConstraintValidator<PasswordRepeatEqual, PersonDTO> {

    private int min;
    private int max;

    @Override
    public void initialize(PasswordRepeatEqual constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password = personDTO.getPassword();
        String repeatPassword = personDTO.getRepeatPassword();

        return password.equals(repeatPassword);
    }
}
