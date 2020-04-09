package com.hxl.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/9 8:55
 */
@ToString
@Setter
@Getter
public class OrderSimpleVO {

    private Long id;

    private String orderNo;

    private BigDecimal totalPrice;

    private Long totalCount;

    private String snapImg;

    private String snapTitle;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Date expiredTime;

    private Date placedTime;

    // pay_time_limit，给前端做倒计时的辅助计算字段
    private Long period;
}
