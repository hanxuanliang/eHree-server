package com.hxl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Spu【商品】 数据表映射
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 11:45
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Spu extends BaseEntity{

    @Id
    private Long id;

    private String title;

    private String subTitle;

    private Long categoryId;

    private Long rootCategoryId;

    private byte online;

    private String price;

    private String discountPrice;

    private Long sketchSpecId;

    private Long defaultSkuId;

    private String img;

    private String description;

    private String tags;

    private Byte isTest;

    private String forThemeImg;

}
