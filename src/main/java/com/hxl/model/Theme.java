package com.hxl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 主题 模型表【关联spu，但是返回给前端的spu和之前的spu模型是不一致的，要做新的封装处理】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 10:16
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "theme")
public class Theme extends BaseEntity{

    @Id
    private Long id;

    private String title;

    private String description;

    private String name;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Boolean online;

    // 不同的主题对应的模板不一样，呈现的前端效果也不一样
    private String tplName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="theme_spu",joinColumns = @JoinColumn(name="theme_id"),
            inverseJoinColumns = @JoinColumn(name="spu_id"))
    private List<Spu> spuList;
}
