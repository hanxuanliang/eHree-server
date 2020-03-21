package com.hxl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Banner Item 属于 Banner 子项
 * @Author: hanxuanliang
 * @Date: 2020/3/14 10:20
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banner_item")
public class BannerItem extends BaseEntity{

    @Id
    private Long id;

    private String img;

    private String keyword;

    private Short type;

    private String name;

    private Long bannerId;
}
