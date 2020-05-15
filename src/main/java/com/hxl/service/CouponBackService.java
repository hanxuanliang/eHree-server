package com.hxl.service;

import com.hxl.bo.OrderMessageBO;
import com.hxl.core.enums.OrderStatus;
import com.hxl.exception.ServerErrorException;
import com.hxl.model.Order;
import com.hxl.repository.OrderRepository;
import com.hxl.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 优惠券返还service
 *
 * @author hxuan
 */
@Service
public class CouponBackService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private UserCouponRepository userCouponRepository;

    @Transactional(rollbackFor = Exception.class)
    // @EventListener 订阅监听事件，一旦触发就会执行
    public void returnBack(OrderMessageBO bo) {
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();

        if (couponId == -1) {
            //没有使用优惠券
            return;
        }
        Optional<Order> orderOptional = this.orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        if (order.getStatusEnum().equals(OrderStatus.UNPAID) || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            userCouponRepository.returnBack(couponId, uid);
        }
    }
}
