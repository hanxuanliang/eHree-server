package com.hxl.exception.http;

import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @Description: HTTP 异常 基类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:16
 */
@Getter
public class HttpException extends RuntimeException {

    protected Integer code;

    @NotNull
    protected Integer httpStatusCode;

}
