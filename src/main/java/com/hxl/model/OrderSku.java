package com.hxl.model;

import com.hxl.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: hanxuanliang
 * @Date: 2020/4/7 13:53
 */
@Setter
@Getter
public class OrderSku {

    private Long id;

    private Long spuId;

    // 单个sku购买的总价格
    private BigDecimal finalPrice;

    // 一个sku单价
    private BigDecimal singlePrice;

    // 当前的sku规格值
    private List<String> specValues;

    // 购物车的购买数量
    private Integer count;

    private String img;

    private String title;

    public OrderSku(SkuInfoDTO skuInfoDTO, Sku sku) {
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(String.valueOf(skuInfoDTO.getCount())));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.specValues = sku.getSpecValueList();
    }

}
