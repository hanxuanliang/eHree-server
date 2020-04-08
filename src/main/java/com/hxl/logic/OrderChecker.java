package com.hxl.logic;

import com.hxl.bo.SkuOrderBO;
import com.hxl.dto.OrderDTO;
import com.hxl.dto.SkuInfoDTO;
import com.hxl.exception.ParameterException;
import com.hxl.model.OrderSku;
import com.hxl.model.Sku;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

/**
 * 订单 验证类
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 0:20
 */
// 非要使用 多例模式，并且交由spring容器管理
// @Component
// @Scope(value = "prototype", proxyMode = TARGET_CLASS)
public class OrderChecker {

    // 前端传来的 order
    private OrderDTO orderDTO;

    // 根据前端 skuIds 后端查询的 skuList
    private List<Sku> serverSkuList;

    // OrderChecker -> CouponChecker
    private CouponChecker couponChecker;

    // 为 order 中的 snapItems 单独准备的模型类：OrderSku 的 list
    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    // 配置中单个sku购买上线【这就涉及springboot组件托管机制】
    private Integer maxSkuLimit;

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList,
                        CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public void isOk() {
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();

        // 1. 当前sku是否已经下架
        skuNotOnsale(orderDTO.getSkuInfoDTOList().size(), serverSkuList.size());

        // 2. 对server端查询出来的skuList进行循环
        for (int i = 0; i < serverSkuList.size(); i++) {
            // 2-1. 拿到server端的sku，前端传来的order中的skuinfo【简单信息】
            Sku sku = serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = orderDTO.getSkuInfoDTOList().get(i);
            // 2-2. 判断是否下架，超卖，超过单个 sku 的数量限制
            containSoldoutSku(sku);
            beyondSkuStock(sku, skuInfoDTO);
            beyondMaxSkuLimit(skuInfoDTO);
            // 2-3. 后端计算出order中所有sku的总价格
            serverTotalPrice = serverTotalPrice.add(calculateSkuOrderPrice(sku, skuInfoDTO));

            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            orderSkuList.add(new OrderSku(skuInfoDTO, sku));
        }

        // 3. 前端价格和后端价格比较，如不相等，抛出异常
        totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
        // 4. 优惠劵的校验控制
        if (couponChecker != null) {
            couponChecker.isOk();
            couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    /**
     * 下面3个给外部的api都是为组装 Order 业务表 做准备的
     * 1. 获取第一个skuimg作为 leaderimg
     * 2. 获取第一个skutitle作为 leadertitle
     * 3. 获取整个购物车的 sku总数
     *
     * @date: 2020/4/7 21:02
     */
    public String getLeaderImg() {
        return serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return orderDTO.getSkuInfoDTOList().stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    // 计算一个sku的购买总价格
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(String.valueOf(skuInfoDTO.getCount())));
    }

    // 前端总价与后端计算总价比较
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    /**
     * 为什么可以通过数量来检测呢？在 skumodel 上标注了 @Where，
     * 查询来的sku本身就是线上的，只要比较前端传来的数量与server端【查询出来的】的数量是否一致就知道是否有sku下架了
     *
     * @date: 2020/4/7 12:12
     */
    private void skuNotOnsale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    /**
     * 检测 sku 的库存是否已经没有了。但是这是预判，因为只要当订单实际插入到db中，才会扣库存；
     * 但是这是有IO时延的，所以不知道是否在插入的时候库存没了，判断的时候确实存在的。
     *
     * @date: 2020/4/7 12:11
     */
    private void containSoldoutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    /**
     * 超卖：订单中的sku数量，与db中sku数量进行比较；
     * db中的少，就说明超卖了。
     *
     * @date: 2020/4/7 12:14
     */
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50003);
        }
    }

    public void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }

}
