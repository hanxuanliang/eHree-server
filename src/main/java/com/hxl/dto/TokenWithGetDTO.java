package com.hxl.dto;

import com.hxl.core.enums.LoginType;
import com.hxl.validators.annotations.TokenPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 用来接收前端传来进行获取token的对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 15:56
 */
@ToString
@Setter
@Getter
public class TokenWithGetDTO {

    @NotBlank(message = "account不允许为空")
    private String account;

    @TokenPassword(max = 20, message = "{token.password}")
    private String password;

    private LoginType loginType;

}
