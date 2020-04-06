package com.hxl.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 多对多形成的第三张表
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 10:51
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户ID
    private Long userId;

    // 优惠劵ID
    private Long couponId;

    // 用户领取优惠劵时间
    private Date createTime;

    // 用户使用优惠劵的状态：已领取未使用 已领取已使用 已过期
    private Integer status;

    // 订单ID
    private Long orderId;
}
