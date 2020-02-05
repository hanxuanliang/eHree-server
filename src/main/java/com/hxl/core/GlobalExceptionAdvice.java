package com.hxl.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Description: 全局异常处理，异常增强
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:48
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public void handlerGeneralException() {

    }
}
