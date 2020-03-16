package com.hxl.utils;

import com.hxl.bo.PageCounter;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/16 11:33
 */
public class CommonUtil {

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;

        return PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
    }
}
