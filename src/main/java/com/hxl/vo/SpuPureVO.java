package com.hxl.vo;

import lombok.*;

/**
 * SpuVO 返回给前端的Spu模型数据，根据自己的需求去添加字段
 *
 * 【VO层：返回给前端的专属对象】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 9:25
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpuPureVO {

    private Long id;

    private String title;

    private String subtitle;

    private String img;

    private String forThemeImg;

    private String price;

    private String discountPrice;

    private String description;

    private String tags;

    private String sketchSpecId;

}
