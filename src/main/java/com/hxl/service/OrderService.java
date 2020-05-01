package com.hxl.service;

import com.hxl.core.LocalUser;
import com.hxl.core.calculate.IMoneyDiscount;
import com.hxl.core.enums.OrderStatus;
import com.hxl.dto.OrderDTO;
import com.hxl.dto.SkuInfoDTO;
import com.hxl.exception.ForbiddenException;
import com.hxl.exception.NotFoundException;
import com.hxl.exception.ParameterException;
import com.hxl.exception.ServerErrorException;
import com.hxl.logic.CouponChecker;
import com.hxl.logic.OrderChecker;
import com.hxl.model.*;
import com.hxl.repository.CouponRepository;
import com.hxl.repository.OrderRepository;
import com.hxl.repository.SkuRepository;
import com.hxl.repository.UserCouponRepository;
import com.hxl.utils.CommonUtil;
import com.hxl.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private SkuRepository skuRepository;

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
            // 检测 coupon_status = 1 的优惠券
            UserCoupon userCoupon = userCouponRepository
                    .findFirstByUserIdAndCouponIdAndStatusAndOrderId(uid, couponId, 1, null)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }

        OrderChecker orderChecker = new OrderChecker(
                orderDTO, skuList, couponChecker, maxSkuLimit);
        orderChecker.isOk();
        return orderChecker;
    }

    /**
     * 下单业务逻辑
     * 加事务的原因：存在多个数据库调用操作，需要让多个操作一并成功，而不是只成功一部分，失败就回滚
     *
     * @date: 2020/4/8 10:26
     */
    @Transactional(rollbackFor = ServerErrorException.class)
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        // 当前时间 + 订单支付时间限制 = 订单过期时间，订单插入到db的瞬间插入，
        // 之后的判断就是 now 与 expiredTime 比较，看是否要取消该订单
        Calendar now = Calendar.getInstance();
        // 当前的时间会在 now.add() 后改变，所以要在此处做一次拷贝
        Calendar nowClone = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSeconds(now, payTimeLimit).getTime();

        Order order = Order.builder()
                .orderNo(OrderUtil.uuOrderNo())
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime)
                .placedTime(nowClone.getTime())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        // 之所以在这要手动赋值，是因为 createTime,expiredTime 的基准时间要一致，不然会有相对误差，倒是订单状态有误
        // expiredTime = createTime + payTimeLimit 这个逻辑才是对的，所以createtime要和这个now一致，
        // 既然一致，那就是在一个地方生成，那就都依靠java代码生成，而不是一个需要数据库，一个需要java代码
        // FIXME CreateTime 这个手动设置是无效的，因为在数据库中已经设置了自动赋值，所以手动赋值是无效的
        // order.setCreateTime(nowClone.getTime());

        orderRepository.save(order);
        // 1. 预减库存
        reduceStock(orderChecker);
        // 2. 核销优惠券
        if (orderDTO.getCouponId() != null) {
            writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        // TODO 3. 加入消息队列，做延迟消息处理
        return order.getId();
    }

    // 获取未支付的订单List，分页返回
    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getLocalUser().getId();
        Date now = new Date();
        return orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.value(), uid, pageable);
    }

    // 根据状态来查询相关订单
    public Page<Order> getOrderByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getLocalUser().getId();
        if (status == OrderStatus.All.value()) {
            return orderRepository.findByUserId(uid, pageable);
        }
        return orderRepository.findByUserIdAndStatus(uid, status, pageable);
    }

    public Optional<Order> getDetailOfOrder(Long orderId) {
        Long uid = LocalUser.getLocalUser().getId();
        return orderRepository.findFirstByUserIdAndId(uid, orderId);
    }

    public void updateOrderPrepayId(Long orderId, String prepayId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setPrepayId(prepayId);
            orderRepository.save(o);
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }

    /**
     * 预减库存。可能会说，之前的orderchecker不是做了库存检测吗？
     * 之前的库存检测只是检测，并没有减库存，如果同时一个时刻有多个订单到来，都通过了检测。
     * 但是实际总的订单中sku数量远远大于库存，就会有超卖的现象。
     * 因为下单这种多线程的情况，去访问一个共享变量，就一定会出现数量不一致的情况。【JMM，联系这个】
     * 而且多线程情况下，还有可能会有乱序执行的问题。
     * 所以在此处要做库存预减。
     * 【这个地方一定要注意多线程的问题，不要再查询再判断，本质并没有解决并发的问题】
     *
     * 第二点：
     *      java中加锁，在分布式的情况下是不能保证数据库的资源顺序访问的；
     *      在多个java执行中，只能保证在单个java程序的顺序执行，在多个java执行中不行；
     *      所以也就不能保证数据库访问的顺序性。
     *      所以，要保证数据库的资源访问有序性，必须要在数据库上加锁。
     * 第三点：
     *      事务 != 锁，事务只是保证执行完成性，锁是资源访问有序性
     *
     * @date: 2020/4/8 9:08
     */
    private void reduceStock(OrderChecker orderChecker) {
        // 1. 正常
        // 2. 负数
        // 3. 多个订单来的时候，有的订单不成功
        // 4. 一个订单加锁，其他订单排队【数据库行锁，java加锁】
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku: orderSkuList) {
            int result = skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    /**
     * 关注 usercoupon 这个模型表，看表需要什么字段，然后修改什么字段set什么字段就知道需求了
     *
     * @date: 2020/4/8 10:36
     */
    private void writeOffCoupon(Long couponId, Long orderId, Long uid) {
        int result = userCouponRepository.writeOff(couponId, orderId, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }
}
