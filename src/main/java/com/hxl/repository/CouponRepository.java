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
     * 通过优惠券类别以及使用时间查询特定的优惠劵
     *
     * @param categoryId 优惠券类别
     * @param now       时间段
     * @return 特定的优惠券列表
     * @date: 2020/4/5 13:44
     */
    @Query("select c from Coupon c\n" +
            " join c.categoryList clist join Activity a on a.id = c.activityId\n" +
            " where clist.id = :categoryId\n" +
            " and a.startTime < :now and a.endTime > :now")
    List<Coupon> findByCategory(Long categoryId, Date now);

    /**
     * 全场券
     *
     * @param isWhole 全场券标志
     * @param now    时间段
     * @return 特定的优惠券列表
     * @date: 2020/4/5 13:57
     */
    @Query("select c from Coupon c join Activity a on a.id = c.activityId\n" +
            " where c.wholeStore = :isWhole and a.startTime < :now and a.endTime > :now")
    List<Coupon> findByWholeStore(Boolean isWhole, Date now);
}
