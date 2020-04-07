package com.hxl.core.calculate;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 暴力向上取整，不管小数是多少都往上取整
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 9:38
 */
@Component
@Primary
public class UpRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actualMoney = original.multiply(discount);
        return actualMoney.setScale(2, RoundingMode.UP);
    }
}
