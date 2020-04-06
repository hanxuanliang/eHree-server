package com.hxl.exception;

/**
 * 创建成功的结果
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 16:07
 */
public class CreateSuccess extends HttpException {

    public CreateSuccess(int code) {
        this.httpCode = 201;
        this.code = code;
    }
}
