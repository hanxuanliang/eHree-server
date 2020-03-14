package com.hxl.exception;

/**
 * @Description: Not Found 异常类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:20
 */
public class NotFoundException extends  HttpException{

    public NotFoundException(Integer code) {
        this.code = code;
        this.httpCode = 404;
    }
}
