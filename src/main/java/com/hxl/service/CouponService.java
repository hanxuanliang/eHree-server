package com.hxl.service;

import com.hxl.core.enums.CouponStatus;
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
        // 【需要注意的是，这里是 ifPresent，如果存在就报错，不存在就跳过异常】
        userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((userCoupon) -> {throw new ParameterException(40006);});

        // 4. 创建 usercoupon，写入 DB 中
        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCouponNew);
    }

    /**
     * 以下 3个method 为个人优惠券部分
     *
     * @date: 2020/4/6 18:11
     */
    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyExpired(uid, now);
    }
}
