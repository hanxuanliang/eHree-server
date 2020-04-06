package com.hxl.exception;

/**
 * 删除成功返回的结果
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 16:28
 */
public class DeleteSuccess extends HttpException{

    public DeleteSuccess(int code) {
        this.httpCode = 200;
        this.code = code;
    }
}
