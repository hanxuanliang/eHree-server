package com.hxl.exception;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:47
 */
public class ServerErrorException extends HttpException {

    public ServerErrorException(int code) {
        this.code = code;
        this.httpCode = 500;
    }
}
