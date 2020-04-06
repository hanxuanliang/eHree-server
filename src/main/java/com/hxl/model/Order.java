package com.hxl.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单 模型表
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 23:07
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "delete_time is null")
// Order 为保留字啊，这里要做 table_name 指向
@Table(name = "`Order`")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 唯一订单id
    private String orderNo;

    // 唯一订单id
    private Long userId;

    // 订单原始的价格
    private BigDecimal totalPrice;

    // 订单中总共有多少个【sku】，注意不是【spu】
    private Long totalCount;

    // 下面几个 snap_ 快照属性【历史状态，这是订单下单瞬间的状态】
    private String snapImg;

    private String snapTitle;

    // 订单中 sku 数据
    //【整体存储在这，而之所以为快照状态，可能会出现sku的价格掉价了，但是snap状态下的data是不改变的】
    private String snapItems;

    // 收货地址
    private String snapAddress;

    // 留给微信支付
    private String prepayId;

    // 这笔订单最终的价格
    private BigDecimal finalTotalPrice;

    // 订单状态：下单，支付 。。。
    private Integer status;

}
