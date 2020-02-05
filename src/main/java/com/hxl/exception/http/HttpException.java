package com.hxl.exception.http;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: HTTP 异常 基类
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HttpException extends RuntimeException {

    protected Integer code;

    protected Integer httpStatusCode;

}
