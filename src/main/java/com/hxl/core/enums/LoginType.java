package com.hxl.core.enums;

/**
 * 登陆类型
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 16:05
 */
public enum LoginType {

    USER_WX(0, "微信登陆"),

    USER_EMAIL(1, "邮箱登陆"),
    ;

    private Integer loginCode;

    LoginType(Integer loginCode, String description) {
        this.loginCode = loginCode;
    }

}
