package com.hxl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Banner 数据表映射
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 9:07
 */
@Entity
@Table(name = "ehree_banner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

    @Id
    private Long id;

    private String name;

    private String description;

    private String img;

    private String title;

    @OneToMany
    @JoinColumn(name = "bannerId")
    // 外键需要标识在一对多的 "一" 上的 “多” 的字段上
    // 【这种方式是单向标注一对多，因为只在 "一" 上注解】
    // 不建议使用这种实体类的方式反向生成表字段，很受限制而且还不受自己控制
    private List<BannerItem> bannerItems;
}
