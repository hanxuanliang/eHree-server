package com.hxl.api.v1;

import com.hxl.core.annotations.ScopeLevel;
import com.hxl.model.Coupon;
import com.hxl.service.CouponService;
import com.hxl.vo.CouponPureVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 【本次的重构，将优惠劵作为api嵌入ehree中，不会作为模块单独输出】
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 13:41
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    // 根据具体商品类目查询
    @GetMapping("/by/category/{categoryId}")
    public List<CouponPureVO> getCouponListByCategory(@PathVariable Long categoryId) {
        List<Coupon> couponList = couponService.getByCategory(categoryId);
        if (couponList.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(couponList);
    }

    // 全场券
    @GetMapping("/whole_store")
    public List<CouponPureVO> geWholeStoreCouponList() {
        List<Coupon> couponList = couponService.getWholeStoreCoupons();
        if (couponList == null) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(couponList);
    }

    @ScopeLevel()
    @PostMapping("/collect/{uid}")
    public void collectCoupon(@PathVariable Long uid) {

    }
}
