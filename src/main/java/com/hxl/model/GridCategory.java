package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 九宫格 模型表
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 9:46
 */
@Entity
@Setter
@Getter
@Table(name = "grid_category")
public class GridCategory extends BaseEntity{

    @Id
    private Long id;

    private String title;

    private String img;

    private String name;

    private Integer categoryId;

    private Integer rootCategoryId;

}
