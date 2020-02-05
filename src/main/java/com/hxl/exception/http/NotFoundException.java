package com.hxl.exception.http;

/**
 * @Description: Not Found 异常类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:20
 */
public class NotFoundException extends  HttpException{

    public NotFoundException(Integer code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
