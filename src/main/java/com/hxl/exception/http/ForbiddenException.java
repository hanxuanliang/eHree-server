package com.hxl.exception.http;

/**
 * @Description: forbidden 异常类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:23
 */
public class ForbiddenException extends HttpException {

    public ForbiddenException(Integer code) {
        this.httpStatusCode = 403;
        this.code = code;
    }
}
