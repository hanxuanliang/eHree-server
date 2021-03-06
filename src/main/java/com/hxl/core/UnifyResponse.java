package com.hxl.core;

import com.hxl.exception.CreateSuccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 统一响应返回对象
 * @Author: hanxuanliang
 * @Date: 2020/2/5 10:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifyResponse {

    private Integer code;

    private String msg;

    private String request;

    public static void createSuccess(int code) {
        throw new CreateSuccess(0);
    }

}
