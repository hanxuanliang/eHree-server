package com.hxl.dto;

import com.hxl.utils.SuperGenericAndJson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * 前端传来的 order dto对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 23:26
 */
@ToString
@Setter
@Getter
public class OrderDTO {

    // 一定要知道：前端传来的价格data是绝对不可行的
    // 1. 被未知原因篡改
    // 2. sku 的数据有可能随时会被修改，需要拒绝该笔订单
    // 3. 但是还是要前端自己计算一次，是为了方便给用户显示
    @DecimalMin(value = "0.01", message = "最低￥0.01")
    @DecimalMin(value = "99999.99", message = "最高￥99999.99")
    private BigDecimal totalPrice;

    private BigDecimal finalTotalPrice;

    private Long couponId;

    private List<SkuInfoDTO> skuInfoDTOList;

    private OrderAddressDTO address;

}
