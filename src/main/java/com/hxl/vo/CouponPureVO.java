package com.hxl.vo;

import com.hxl.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 前端传来的coupon VO对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 11:15
 */
@ToString
@Getter
@Setter
public class CouponPureVO {

    private Long id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    private Integer type;

    private Long activityId;

    private String remark;

    private Boolean wholeStore;

    public CouponPureVO(Coupon coupon) {
        BeanUtils.copyProperties(coupon, this);
    }
}
