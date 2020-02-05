package com.hxl.core;

import com.hxl.exception.http.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 全局异常处理，异常增强
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:48
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handlerGeneralException(HttpServletRequest request, Exception exception) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.info("exception: {}", exception.getMessage());
        return new UnifyResponse(9999, "服务器异常", method + " " + requestUri);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handlerHttpException(HttpServletRequest request, HttpException exception) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        UnifyResponse message = new UnifyResponse(exception.getCode(), "不知名错误", method + " " + requestUri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(exception.getHttpStatusCode());

        return new ResponseEntity<>(message, headers, httpStatus);
    }
}
