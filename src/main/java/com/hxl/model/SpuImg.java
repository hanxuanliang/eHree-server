package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * spu 具体展示页面的轮播图使用img
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:55
 */
@Entity
@Getter
@Setter
@Table(name = "spu_img")
public class SpuImg extends BaseEntity{

    @Id
    private Long id;

    private String img;

    private Long spuId;

}
