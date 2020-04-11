package com.hxl.service;

import com.hxl.core.LocalUser;
import com.hxl.exception.NotFoundException;
import com.hxl.model.Order;
import com.hxl.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * 支付 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/9 10:24
 */
@Service
public class WxPaymentService {

    @Resource
    private OrderRepository orderRepository;

    public Map<String, String> preOrder(Long orderId) {
        Long uid = LocalUser.getLocalUser().getId();
        Optional<Order> optionalOrder = orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = optionalOrder.orElseThrow(() -> new NotFoundException(50009) );
        // 再次判断此时的订单过期时间，有以下几种情况：
        // 1. 买完 --> 立即过期。
        // 2. 历史订单 --> 是否多次支付，后者是多次拉起支付，中途会断开支付流程。
        // 3. postman测试，避开前端button置灰的禁止。
        return null;
    }

}
