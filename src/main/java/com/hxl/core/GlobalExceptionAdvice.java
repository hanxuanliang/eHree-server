package com.hxl.core;

import com.hxl.core.configuration.ExceptionCodeConfiguration;
import com.hxl.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 全局异常处理，异常增强
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:48
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @Resource
    private ExceptionCodeConfiguration codeConfiguration;

    /**
     * 未知异常，下面的异常都处理不了，会到此处
     * @param request 上下文request
     * @param exception 无法处理的异常
     * @return UnifyResponse 统一响应返回
     * @date: 2020/2/6 11:26
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handlerGeneralException(HttpServletRequest request, Exception exception) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.info("exception: {}", exception.getMessage());
        return new UnifyResponse(999, "服务器未知异常", method + " " + requestUri);
    }

    /**
     * 由我们自己手动抛出的异常，在此处被捕获然后处理，返回统一响应体
     * @param request 上下文request
     * @param exception HTTP请求异常
     * @return ResponseEntity<UnifyResponse> 由Spring提供的响应体包装统一响应返回
     * @date: 2020/2/6 11:24
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handlerHttpException(HttpServletRequest request, HttpException exception) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        UnifyResponse message = new UnifyResponse(
                exception.getCode(),
                codeConfiguration.getOfMessage(exception.getCode()),
                method + " " + requestUri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(exception.getHttpCode());

        return new ResponseEntity<>(message, headers, Objects.requireNonNull(httpStatus));
    }

    /**
     * 请求体内的参数验证
     * @param request 上下文request
     * @param exception 请求体参数异常
     * @return UnifyResponse 统一响应返回
     * @date: 2020/2/6 11:23
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handlerBeanValidation(HttpServletRequest request, MethodArgumentNotValidException exception) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        String messages = formatAllErrorMessages(errors);
        return new UnifyResponse(10001, messages, method + " " + requestUrl);
    }

    /**
     * url 中的参数验证
     * @param request 上下文request
     * @param exception url参数异常
     * @return UnifyResponse 统一响应返回
     * @date: 2020/2/6 11:22
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handlerConstraintException(HttpServletRequest request, ConstraintViolationException exception) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();

        StringBuilder errorMsg = new StringBuilder();
        for (ConstraintViolation<?> error : exception.getConstraintViolations()) {
            String msg = error.getMessage();
            String m = error.getPropertyPath().toString();
            String name = m.split("[.]")[1];
            errorMsg.append(name).append(" ").append(msg).append(";");
        }

        return new UnifyResponse(10001, errorMsg.substring(0, errorMsg.toString().length() - 1), method + " " + requestUrl);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuilder errorMsg = new StringBuilder();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString().substring(0, errorMsg.length() - 1);
    }
}
