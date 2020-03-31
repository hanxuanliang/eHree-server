package com.hxl.validators.annotations;

import com.hxl.validators.PasswordEqualValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 两次密码输入 相等注解
 *
 * @Author: hanxuanliang
 * @Date: 2020/2/5 20:10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = {PasswordEqualValidator.class})
public @interface PasswordRepeatEqual {

    String message() default "两次密码不相等";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
