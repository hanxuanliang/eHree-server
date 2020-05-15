package com.hxl.service;

import com.hxl.bo.OrderMessageBO;
import com.hxl.exception.ServerErrorException;
import com.hxl.model.Order;
import com.hxl.repository.OrderRepository;
import com.hxl.repository.SkuRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 订单取消service
 *
 * @author hxuan
 */
@Service
public class OrderCancelService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private SkuRepository skuRepository;

    // @EventListener
    // 订阅监听事件，一旦触发就会执行
    @Transactional(rollbackFor = Exception.class)
    public void cancel(OrderMessageBO orderMessageBO) {
        if (orderMessageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }
        this.cancel(orderMessageBO.getOrderId());
    }

    private void cancel(Long oid) {
        Optional<Order> orderOptional = this.orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        int res = orderRepository.cancelOrder(oid);
        if (res != 1) {
            return;
        }
        order.getSnapItems().forEach(
            orderSku -> skuRepository.recoverStock(orderSku.getId(), orderSku.getCount().longValue()));
    }
}
