package com.hxl.core.calculate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 向上取整
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 9:30
 */
@Component
public class HalfUpRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal origin, BigDecimal discount) {
        BigDecimal actualMoney = origin.multiply(discount);
        // 保留 2 位小数，模式：四舍五入
        return actualMoney.setScale(2, RoundingMode.HALF_UP);
    }
}
