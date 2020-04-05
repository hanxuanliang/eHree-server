package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 商品分类 模型表
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 13:12
 */
@Entity
@Getter
@Setter
@Table(name = "category")
public class Category extends BaseEntity{

    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean isRoot;

    private String img;

    private Long parentId;

    private Long index;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coupon_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> couponList;

}
