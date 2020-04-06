package com.hxl.exception;

/**
 * 更新成功的返回结果
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 16:29
 */
public class UpdateSuccess extends HttpException {

    public UpdateSuccess(int code) {
        this.httpCode = 200;
        this.code = code;
    }
}
