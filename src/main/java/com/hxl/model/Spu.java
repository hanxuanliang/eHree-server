package com.hxl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Spu【商品】 数据表映射
 *
 * 整理一下 Spu Sku Spec 之间的关系：
 * 1. Spu -- Spec_Key  多对多
 *      【spu会有多种规格，而一种规则也会在不同的spu下出现】
 * 2. Sku -- Spec_Value  多对多
 *      【sku对应的是不同的具体的规格值，一种具体的规格值也会出现在不同的sku上】
 * 2. Spu -- Sku  一对多
 *      【spu是总商品，sku是携带具体规格的商品】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 11:45
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Spu extends BaseEntity{

    @Id
    private Long id;

    private String title;

    private String subTitle;

    private Long categoryId;

    private Long rootCategoryId;

    private byte online;

    private String price;

    private String discountPrice;

    private Long sketchSpecId;

    private Long defaultSkuId;

    private String img;

    private String description;

    private String tags;

    private Byte isTest;

    /**
     * 至于什么时候加上这个一对多，多对多的导航关联关系，看你是否需要跨表查询，
     * 如果需要，则可以加上一对多这些注解。设置呢，一般是设置是 “一” 这一方。
     * 记得把关联的键加上：@JoinColumn(name = "yourKeyName")。
     *
     * 因为你设置关联导航关系，JPA就会帮你识别出来，而不需要你自己去书写SQL就可以
     * 达到跨表查询，这个是及其方便的。
     *
     * 下面还有一个问题：就是设置了 LAZY加载，虽然在代码里面没有显示去触发skuList
     * 的获取，但是在序列化到前端的过程是会触发 getSkuList()，所以也就是设置也会
     * 读取。
     *
     * @date: 2020/3/16 9:00
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

}
