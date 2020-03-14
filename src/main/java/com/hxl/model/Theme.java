package com.hxl.model;

import javax.persistence.*;
import java.util.List;

/**
 * Theme 数据表映射
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 11:45
 */
@Entity
public class Theme {

    @Id
    private Long id;

    private String title;

    private String name;

    @ManyToMany
    @JoinTable(name = "theme_spu",
            joinColumns = @JoinColumn(name = "theme_id"),
            inverseJoinColumns = @JoinColumn(name = "spu_id"))
    private List<Spu> spuList;

}
