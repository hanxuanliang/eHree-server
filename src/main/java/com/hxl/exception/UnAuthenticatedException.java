package com.hxl.exception;

/**
 * 未授权 异常
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 16:56
 */
public class UnAuthenticatedException extends HttpException {

    public UnAuthenticatedException(int code) {
        this.httpCode = 500;
        this.code = code;
    }
}
