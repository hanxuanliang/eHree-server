package com.hxl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hxl.core.enums.OrderStatus;
import com.hxl.dto.OrderAddressDTO;
import com.hxl.utils.CommonUtil;
import com.hxl.utils.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    // 订单过期时间，用来判断是 未支付 还是 已取消
    private Date expiredTime;

    // 下单时间，手动单独控制【而 createTime 为记录创建时间，下面这个才是真正的下单时间】
    private Date placedTime;

    // 留给微信支付【微信支付的时候会在自己的服务器中下一个订单，返回的就是这个 prepayid，
    // 然后把这个值存储在 order模型下的这个字段】
    // 而之所以要留这个字段就是给延迟支付做准备的，不要每一次发起支付都调用微信的api
    private String prepayId;

    // 这笔订单最终的价格
    private BigDecimal finalTotalPrice;

    // 订单状态：下单，支付 。。。
    private Integer status;

    // obj ==> json  json ==> obj
    public OrderAddressDTO getSnapAddress() {
        if (snapAddress == null) {
            return null;
        }
        return GenericAndJson.jsonToObject(snapAddress,
                new TypeReference<OrderAddressDTO>() {});
    }

    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if (orderSkuList == null) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems() {
        return GenericAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {});
    }

    @JsonIgnore
    public OrderStatus getStatusEnum() {
        return OrderStatus.toType(this.status);
    }

    // 充血模型，在模型类中不应该只有纯粹的属性，还应该有业务方法，扩展模型类的领域范围
    public Boolean needCancel() {
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)) {
            return false;
        }
        return CommonUtil.isOutOfDate(this.getExpiredTime());
    }

}
