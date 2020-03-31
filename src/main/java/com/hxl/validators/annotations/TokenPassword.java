package com.hxl.validators.annotations;

import com.auth0.jwt.interfaces.Payload;
import com.hxl.validators.TokenPasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Token password 注解验证
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 16:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {

    int min() default 6;

    int max() default 32;

    String message() default "字段不符合要求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
