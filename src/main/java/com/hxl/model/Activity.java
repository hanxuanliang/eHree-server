package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 活动 模型表【优惠券的基本组织单位】
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/4 21:57
 */
@Entity
@Setter
@Getter
public class Activity extends BaseEntity {

    @Id
    private Long id;

    private String title;

    private String description;

    // 领取优惠劵的开始日期
    private Date startTime;

    // 领取优惠劵的结束日期
    private Date endTime;

    // 活动说明
    private String remark;

    // 是否线上
    private Boolean online;

    // 活动封面图片
    private String entranceImg;

    // 活动顶部图片
    private String internalTopImg;

    // 活动名称
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = ("activityId"))
    private List<Coupon> couponList;

}
