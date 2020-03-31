package com.hxl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用来接收前端传来进行验证的封装对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 17:20
 */
@ToString
@Setter
@Getter
public class TokenWithVerifyDTO {

    private String token;
}
