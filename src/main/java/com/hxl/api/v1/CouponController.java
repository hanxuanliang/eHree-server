package com.hxl.api.v1;

import com.hxl.core.LocalUser;
import com.hxl.core.annotations.ScopeLevel;
import com.hxl.model.Coupon;
import com.hxl.model.User;
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

    // 根据具体商品类目查询【categoryId 是二级分类：具体见 `coupon_category` 这个表的数据】
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

    /**
     * 针对个人的优惠券【此处的id为优惠券的id】
     *
     * 1. 不能是 uid，会出现超权的问题，因为你登录了，不代表你就可以访问一个别人的uid；
     * 2. 而且我们知道，在token的map里面其实已经携带了uid，所以没必要显示去传uid
     * 3. 所以我们应该是从token中去获取uid来查询该uid下的coupon
     *
     * @date: 2020/4/6 14:20
     */
    @ScopeLevel
    @PostMapping("/collect/{id}")
    public void collectCoupon(@PathVariable Long id) {
        Long uid = LocalUser.getLocalUser().getId();
    }
}
