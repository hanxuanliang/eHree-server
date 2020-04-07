package com.hxl.core.calculate;

import java.math.BigDecimal;

/**
 * 金额计算 接口规范
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 9:27
 */
public interface IMoneyDiscount {

    BigDecimal discount(BigDecimal origin, BigDecimal discount);

}
