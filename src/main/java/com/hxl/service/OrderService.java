package com.hxl.service;

import com.hxl.core.calculate.IMoneyDiscount;
import com.hxl.core.enums.OrderStatus;
import com.hxl.dto.OrderDTO;
import com.hxl.dto.SkuInfoDTO;
import com.hxl.exception.NotFoundException;
import com.hxl.exception.ParameterException;
import com.hxl.logic.CouponChecker;
import com.hxl.logic.OrderChecker;
import com.hxl.model.Coupon;
import com.hxl.model.Order;
import com.hxl.model.Sku;
import com.hxl.model.UserCoupon;
import com.hxl.repository.CouponRepository;
import com.hxl.repository.OrderRepository;
import com.hxl.repository.UserCouponRepository;
import com.hxl.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 23:57
 */
@Service
public class OrderService {

    @Resource
    private SkuService skuService;

    @Resource
    private CouponRepository couponRepository;

    @Resource
    private UserCouponRepository userCouponRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private IMoneyDiscount iMoneyDiscount;

    @Value("${ehree.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${ehree.order.pay-time-limit}")
    private Integer payTimeLimit;

    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0.01")) <= 0) {
            throw new ParameterException(50001);
        }

        // 1. 获取 dto 中的 sku_list 中所有的id，方便到db中查询一组 sku
        List<Long> skuIdList = orderDTO.getSkuInfoDTOList().stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());
        List<Sku> skuList = skuService.getSkuListByIds(skuIdList);

        CouponChecker couponChecker = null;
        Long couponId = orderDTO.getCouponId();
        if (couponId != null) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));
            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }

        OrderChecker orderChecker = new OrderChecker(
                orderDTO, skuList, couponChecker, maxSkuLimit);
        orderChecker.isOk();
        return orderChecker;
    }

    // 下单业务逻辑
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        Order order = Order.builder()
                .orderNo(OrderUtil.uuOrderNo())
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());

        orderRepository.save(order);
        return order.getId();
    }
}
