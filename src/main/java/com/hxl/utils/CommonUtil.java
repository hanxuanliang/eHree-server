package com.hxl.utils;

import com.hxl.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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

    public static Boolean isInTimeLime(Date now, Date start, Date end) {
        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime < endTime && nowTime > startTime;
    }

    public static Boolean isOutOfDate(Date startTime, Long period) {
        long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMill = period * 1000;
        return now > startTimeStamp + periodMill;
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        long now = Calendar.getInstance().getTimeInMillis();
        long expiredStamp = expiredTime.getTime();
        return now > expiredStamp;
    }

    public static Calendar addSeconds(Calendar now, int seconds) {
        now.add(Calendar.SECOND, seconds);
        return now;
    }

    public static String yuanTofenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toString();
    }

    // 10 位时间戳
    public static String timestamp10() {
        long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13str = Long.toString(timestamp13);
        return timestamp13str.substring(0, timestamp13str.length() - 3);
    }
}
