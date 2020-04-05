package com.hxl.vo;

import com.hxl.model.Activity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 携带coupon的活动 VO对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 11:14
 */
@ToString
@Setter
@Getter
public class ActivityCouponVO extends ActivityPureVO{

    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity) {
        super(activity);
        coupons = activity.getCouponList().stream()
                .map(CouponPureVO::new).collect(Collectors.toList());
    }

}
