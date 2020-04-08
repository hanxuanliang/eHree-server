package com.hxl.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * 订单模块的辅助函数
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 21:29
 */
@Component
public class OrderUtil {

    private static String[] yearCodes;

    @Value("${ehree.year-codes}")
    public void setYearCodes(String yearCodes) {
        OrderUtil.yearCodes = yearCodes.split(",");
    }

    // 生成相对唯一的订单号：B 323 0651812529 [year-day-random]
    public static String uuOrderNo() {
        StringBuilder joiner = new StringBuilder();
        Calendar calendar = Calendar.getInstance();

        String mills = String.valueOf(calendar.getTimeInMillis());
        String micro = LocalDateTime.now().toString();
        String random = String.valueOf(Math.random() * 1000).substring(0, 2);
        joiner.append(OrderUtil.yearCodes[calendar.get(Calendar.YEAR) - 2013])
                .append(Integer.toHexString(calendar.get(Calendar.MONTH) + 1).toUpperCase())
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(mills.substring(mills.length() - 5))
                .append(micro.substring(micro.length() - 5))
                .append(random);
        return joiner.toString();
    }
}
