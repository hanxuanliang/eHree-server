package com.hxl.core.enums;

import java.util.stream.Stream;

/**
 * 优惠券枚举
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 9:07
 */
public enum CouponType {

    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛减除券"),
    ;

    private Integer value;

    CouponType(Integer value, String descirption) {
        this.value = value;
    }

    public static CouponType toType(Integer value) {
        return Stream.of(CouponType.values())
                .filter(couponType -> couponType.value.equals(value))
                .findAny()
                .orElse(null);
    }

}
