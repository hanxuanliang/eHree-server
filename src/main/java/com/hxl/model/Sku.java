package com.hxl.model;

import com.hxl.utils.SuperGenericAndJson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * sku【带有具体规格的商品】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:40
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sku")
@Where(clause = "delete_time is NULL and online = 1")
public class Sku extends BaseEntity {

    @Id
    private Long id;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Boolean online;

    private String img;

    private String title;

    private Long spuId;

    // 冗余字段
    private Long categoryId;

    // 冗余字段
    private Long rootCategoryId;

    // 规格的设计【一个sku的规格是确定的，而且这里是数组里面包裹json字符串】
    @Convert(converter = SuperGenericAndJson.class)
    private List<Spec> specs;

    // 判断用户是否选择了一个完整的规格尺寸，看当前的code拼接和数据库的code字段是否相等
    private String code;

    private Long stock;

}
