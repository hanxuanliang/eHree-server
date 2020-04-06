package com.hxl.repository;

import com.hxl.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 活动 dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 10:58
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * 通过活动name来查找活动
     *
     * @param name 活动name
     * @return Activity活动
     * @date: 2020/4/5 11:00
     */
    Activity findByName(String name);

    Optional<Activity> findByCouponListId(Long couponId);
}
