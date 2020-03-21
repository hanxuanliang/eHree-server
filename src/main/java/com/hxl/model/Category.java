package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class Category {

    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean isRoot;

    private String img;

    private Long parentId;

    private Long index;

}
