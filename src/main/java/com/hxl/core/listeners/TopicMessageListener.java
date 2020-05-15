package com.hxl.core.listeners;

import com.hxl.bo.OrderMessageBO;
import com.hxl.service.CouponBackService;
import com.hxl.service.OrderCancelService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.annotation.Resource;

/**
 * @author hxuan
 */
public class TopicMessageListener implements MessageListener {

    @Resource
    private OrderCancelService orderCancelService;
    @Resource
    private CouponBackService couponBackService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        // TODO
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String expiredKey = new String(body);
        String topic = new String(channel);
        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);
        // 此处要发布一个事件，才能搭配使用 @EventListener 来订阅、监听、触发
        // TopicMessageListener.publisher.publishEvent(orderMessageBO);
        orderCancelService.cancel(orderMessageBO);
        couponBackService.returnBack(orderMessageBO);
    }
}
