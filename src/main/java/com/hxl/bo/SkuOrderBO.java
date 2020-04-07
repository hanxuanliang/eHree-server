package com.hxl.bo;

import com.hxl.dto.SkuInfoDTO;
import com.hxl.model.Sku;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 订单中关于 sku 的业务对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 10:21
 */
@ToString
@Setter
@Getter
public class SkuOrderBO {

    private BigDecimal actualPrice;

    private Integer count;

    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return actualPrice.multiply(new BigDecimal(String.valueOf(count)));
    }
}
