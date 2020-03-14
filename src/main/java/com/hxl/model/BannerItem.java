package com.hxl.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Banner Item 属于 Banner 子项
 * @Author: hanxuanliang
 * @Date: 2020/3/14 10:20
 */
@Entity
@Table(name = "ehree_banner_item")
public class BannerItem {

    @Id
    private Long id;

    private Long bannerId;

    private String img;

    private String keyword;

    private Short type;

    private String name;
}
