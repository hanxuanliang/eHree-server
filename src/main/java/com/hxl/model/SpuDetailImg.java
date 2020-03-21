package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * spu 详情页的具体下方展示图片
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:54
 */
@Entity
@Getter
@Setter
@Table(name = "spu_detail_img")
public class SpuDetailImg extends BaseEntity{

    @Id
    private Long id;

    private String img;

    private Long spuId;

    private Long index;

}
