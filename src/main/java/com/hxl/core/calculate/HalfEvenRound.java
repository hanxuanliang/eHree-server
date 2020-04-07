package com.hxl.core.calculate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 向下取整
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 9:34
 */
@Component
public class HalfEvenRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actualMoney = original.multiply(discount);
        return actualMoney.setScale(2, RoundingMode.HALF_EVEN);
    }
}