package com.hxl.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回给前端的 theme VO对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 10:31
 */
@Setter
@Getter
@ToString
public class ThemePureVO {

    private Long id;

    private String title;

    private String description;

    private String name;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private String tplName;

    private Boolean online;

}
