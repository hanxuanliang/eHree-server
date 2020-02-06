package com.hxl.validators.annotations;

import com.hxl.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description: 两次密码输入 相等注解
 * @Author: hanxuanliang
 * @Date: 2020/2/5 20:10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = {PasswordValidator.class})
public @interface PasswordRepeatEqual {

    String message() default "两次密码不相等";

    int min() default 6;

    int max() default 16;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
