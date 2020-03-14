package com.hxl.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Spu 数据表映射
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 11:45
 */
@Entity
@Table(name = "ehree_spu")
public class Spu {

    @Id
    private Long id;

    private String title;

    private String subTitle;

    @ManyToMany
    private List<Theme> themeList;
}
