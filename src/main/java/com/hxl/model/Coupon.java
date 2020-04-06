package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠劵 模型表
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 9:11
 */
@Entity
@Setter
@Getter
public class Coupon extends BaseEntity {

    @Id
    private Long id;

    private Long activityId;

    // 优惠卷的标题
    private String title;

    // 有效开始日期
    private Date startTime;

    // 有效结束日期
    private Date endTime;

    // 优惠卷的描述
    private String description;

    // 满金额
    private BigDecimal fullMoney;

    // 减多少
    private BigDecimal minus;

    // 折扣
    private BigDecimal rate;

    // 优惠卷的类型：满减劵，满减折扣劵，折扣卷，无门槛劵，有效期劵（领取时间开始30天内有效）-加一有效期天数的字段，领取日+有效期天数判断是否过期
    private Integer type;

    // 优惠劵的说明
    private String remark;

    // 是否为全场劵
    private Boolean wholeStore;

    // mappedBy 是做双向多对多的连接【而且是被维护端】
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;

}
