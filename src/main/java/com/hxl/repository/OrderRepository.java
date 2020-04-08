package com.hxl.repository;

import com.hxl.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/8 8:44
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
