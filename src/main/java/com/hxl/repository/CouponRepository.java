package com.hxl.repository;

import com.hxl.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * coupon dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 13:43
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * 通过【商品分类】以及使用【活动时间】(有一个过期时间) 查询特定的优惠劵
     * <p>
     * 原生SQL：
     * 【select * from coupon
     * join coupon_category  on coupon.id = coupon_category.coupon_id
     * join category  on coupon_category.category_id = category.id
     * where category.id = 15】
     *
     * @param categoryId 优惠券类别
     * @param now        时间段
     * @return 特定的优惠券列表
     * @date: 2020/4/5 13:44
     */
    @Query("select c from Coupon c\n" +
            "join c.categoryList clist \n" +
            "join Activity a on a.id = c.activityId\n" +
            "where clist.id = :categoryId\n" +
            "and a.startTime < :now and a.endTime > :now")
    List<Coupon> findByCategory(Long categoryId, Date now);


    /**
     * 全场券 使用【活动时间】(有一个过期时间) 查询特定的优惠劵
     *
     * @param isWhole 全场券标志
     * @param now     时间段
     * @return 特定的优惠券列表
     * @date: 2020/4/5 13:57
     */
    @Query("select c from Coupon c join Activity a on a.id = c.activityId\n" +
            " where c.wholeStore = :isWhole and a.startTime < :now and a.endTime > :now")
    List<Coupon> findByWholeStore(Boolean isWhole, Date now);


    @Query("select c from Coupon c \n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where uc.status = 1 \n" +
            "and u.id = :uid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null ")
    List<Coupon> findMyAvailable(Long uid, Date now);


    @Query("select c from Coupon c \n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null \n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(Long uid, Date now);


    @Query("select c from Coupon c \n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.orderId is null\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(Long uid, Date now);
}
