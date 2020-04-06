package com.hxl.service;

import com.hxl.exception.NotFoundException;
import com.hxl.exception.ParameterException;
import com.hxl.model.Activity;
import com.hxl.model.Coupon;
import com.hxl.model.UserCoupon;
import com.hxl.repository.ActivityRepository;
import com.hxl.repository.CouponRepository;
import com.hxl.repository.UserCouponRepository;
import com.hxl.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * coupon service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 13:42
 */
@Service
public class CouponService {

    @Resource
    private CouponRepository couponRepository;

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long categoryId) {
        Date now = new Date();
        return couponRepository.findByCategory(categoryId, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public void collectOneCoupon(Long uid, Long couponId) {
        // 1. 先确定用户传递的 couponId 是合法的，也就是说可以在 db 中查询到数据
        couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));

        // 2. 确定这个查询的 coupon 是否过期，而这个过期是附加在 activity 中
        Activity activity = activityRepository.findByCouponListId(couponId).orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isExpire = CommonUtil.isInTimeLime(now, activity.getStartTime(), activity.getEndTime());
        if (!isExpire) {
            throw new ParameterException(40005);
        }

        // 3. 去检验一下 DB 中有没有 uid-couponId 连接的数据，如果有说明已经领取了，就报错
        userCouponRepository.findFirstByUserIdAndAndCouponId(uid, couponId)
                .orElseThrow(() -> new ParameterException(40006));

        // 4. 创建 usercoupon，写入 DB 中
        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .build();
        userCouponRepository.save(userCouponNew);
    }
}
