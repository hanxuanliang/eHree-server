package com.hxl.model;

import com.hxl.utils.SuperGenericAndJson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

/**
 * sku【规格商品】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:40
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sku extends BaseEntity {

    @Id
    private Long id;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Boolean online;

    private String img;

    private String title;

    private Long spuId;

    private Long categoryId;

    private Long rootCategoryId;

    @Convert(converter = SuperGenericAndJson.class)
    private List<Spec> specs;

    private String code;

    private Long stock;

}
