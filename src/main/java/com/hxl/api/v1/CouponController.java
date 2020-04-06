package com.hxl.api.v1;

import com.hxl.core.LocalUser;
import com.hxl.core.UnifyResponse;
import com.hxl.core.annotations.ScopeLevel;
import com.hxl.core.enums.CouponStatus;
import com.hxl.exception.ParameterException;
import com.hxl.model.Coupon;
import com.hxl.model.User;
import com.hxl.service.CouponService;
import com.hxl.vo.CouponCategoryVO;
import com.hxl.vo.CouponPureVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @PostMapping("/collect/{couponId}")
    public void collectCoupon(@PathVariable Long couponId) {
        Long uid = LocalUser.getLocalUser().getId();
        couponService.collectOneCoupon(uid, couponId);
        // 要注意的问题是内部是以报错的形式返回的，如果在报错机制有log收集，这里就会出现成功的结果会在收集错误的log中出现
        UnifyResponse.createSuccess(0);
    }

    /**
     * status 不能很清楚的去表示当前的优惠券的状态
     * 因为：延时订单支付 --> 库存，优惠券归还机制【未付款在订单支付过期后返回给user】
     * 但是这种延时异步修改，不一定成功，所以不能完全相信
     *
     * @date: 2020/4/6 18:03
     */
    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponByStatus(@PathVariable Integer status) {
        Long uid = LocalUser.getLocalUser().getId();
        List<Coupon> couponList = new ArrayList<>();
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        return CouponPureVO.getList(couponList);
    }

    /**
     * 在结算的时候，有些商品是不满足优惠券下的商品类别限制的，也就不能单纯对价格进行加和
     *
     * @date: 2020/4/6 22:46
     */
    @ScopeLevel
    @PostMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory() {
        User user = LocalUser.getLocalUser();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(CouponCategoryVO::new).collect(Collectors.toList());
    }
}
