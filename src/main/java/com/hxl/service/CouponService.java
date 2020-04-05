package com.hxl.service;

import com.hxl.model.Coupon;
import com.hxl.repository.CouponRepository;
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

    public List<Coupon> getByCategory(Long categoryId) {
        Date now = new Date();
        return couponRepository.findByCategory(categoryId, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }
}
