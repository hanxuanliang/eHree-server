package com.hxl.repository;

import com.hxl.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/8 8:44
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 【待支付】status = unpaid, expireddTime > now, uid
    // 这个 ExpiredTime 是要有值的！
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, Integer status, Long uid, Pageable pageable);

    Page<Order> findByUserId(Long uid, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long uid, Integer status, Pageable pageable);

    Optional<Order> findFirstByUserIdAndId(Long uid, Long orderId);

}
