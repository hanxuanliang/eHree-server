package com.hxl.exception;

/**
 * 参数 异常 ？
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 14:37
 */
public class ParameterException extends HttpException {

    public ParameterException(int code) {
        this.httpCode = 400;
        this.code = code;
    }
}
