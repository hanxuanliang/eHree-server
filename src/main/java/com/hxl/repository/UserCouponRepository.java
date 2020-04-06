package com.hxl.repository;

import com.hxl.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/6 15:08
 */
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findFirstByUserIdAndAndCouponId(Long uid, Long couponId);
}
