package com.hxl.validators;

import com.hxl.validators.annotations.TokenPassword;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/30 16:16
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {

    private Integer min;

    private Integer max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String message, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isEmpty(message);
    }
}
