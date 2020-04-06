package com.hxl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 23:27
 */
@ToString
@Getter
@Setter
public class SkuInfoDTO {

    // 当前的 sku_id
    private Long id;

    // 当前这个 sku 购买了多少个
    private Integer count;

}
