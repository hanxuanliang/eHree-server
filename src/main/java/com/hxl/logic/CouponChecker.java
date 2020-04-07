package com.hxl.logic;

import com.hxl.bo.SkuOrderBO;
import com.hxl.core.calculate.IMoneyDiscount;
import com.hxl.core.enums.CouponType;
import com.hxl.exception.ForbiddenException;
import com.hxl.exception.ParameterException;
import com.hxl.model.Category;
import com.hxl.model.Coupon;
import com.hxl.model.UserCoupon;
import com.hxl.utils.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券 业务验证类
 *
 * 如果在此类上注解 @Component 就会被 springboot 接管导致为单例模式，这个有什么问题？
 * 而它的私有成员变量就没有意义了，因为这是单例的，可以关注到 service，它的私有变量也是被springboot接管的。
 * 同时你要知道，这个验证类必须不是单例的，因为每个订单都有自己的一个验证。所以一定要是多例。
 * 而且我们在 OrderService 中还 new CouponChecker() 如果交给springboot托管，我们是不需要自己new的。
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 0:19
 */
public class CouponChecker {

    private Coupon coupon;

    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    @Deprecated
    public CouponChecker(Coupon coupon, UserCoupon userCoupon) {
        this.coupon = coupon;
    }

    @Deprecated
    public CouponChecker(Long couponId, Long uid) { }

    // 基本检测，校验一下优惠券是否过期【一定不能使用status，一定要用时间去过滤判断】
    public void isOk() {
        Date now = new Date();
        Boolean isExpired = CommonUtil.isInTimeLime(now, coupon.getStartTime(), coupon.getEndTime());
        if (!isExpired) {
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 最终成交价是否正确
     *
     * @param orderFinalTotalPrice 前端计算的最终价
     * @param serverTotalPrice     服务端计算的原价【需要orderService的sku计算总价】
     * @date: 2020/4/7 9:04
     */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        // 服务端计算的最终价
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(coupon.getType())) {
            // 两个满减 分支合并
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                // 本身就是满减券，他设置的门槛就是不会让你支付的金额为最小值，所以在这不需要做额外的判断，但是如果你想更为严格的判断可以加
                serverFinalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                // 如果折扣 0.01 抛出异常即可
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0.01")) < 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = iMoneyDiscount.discount(serverTotalPrice, coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        int compareTwo = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compareTwo != 0) {
            throw new ForbiddenException(50008);
        }

    }

    // 当前的优惠券是否真的能被使用，主要是是分类优惠券的使用限度
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        // 所需数据：sku-price，sku-count，sku-categor，coupo-category
        BigDecimal orderCategoryPrice;

        // 1. 全场券逻辑，所有的分类都是可以使用的
        if (coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            // 2. 普通方法去做：先for类别，再在类别里面for sku 计算金额
            List<Long> cidList = coupon.getCategoryList().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = getSumByCategoryList(skuOrderBOList, cidList);
        }
        couponCanBeUsed(orderCategoryPrice);
    }

    // 优惠券是不是能用
    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compareTwo = coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compareTwo > 0) {
                    throw new ParameterException(40008);
                }
                break;
            // 无门槛劵当然到处都是能用的啦！不做特殊处理
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    // 循环 coupon 中携带的 categoryIdList，执行每一个品类下的sku金额计算
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {

        return cidList.stream()
                .map(cid -> getSumByCategoryId(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    private BigDecimal getSumByCategoryId(List<SkuOrderBO> skuOrderBOList, Long cid) {

        return skuOrderBOList.stream()
                .filter(sku -> sku.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }
}
