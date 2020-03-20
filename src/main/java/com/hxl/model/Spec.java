package com.hxl.model;

import lombok.Getter;
import lombok.Setter;

/**
 * spec 规格值
 *
 * 这个模型是没有标注 @Entity 是没有表示一个具体的表
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:41
 */
@Getter
@Setter
public class Spec {

    /**
     * 至于为什么需要 规格值id，规格名id：
     * 这个就关联到 sku 中的 code，这个是选取一个完整的sku规格的完整路径，
     * 而其中包含的就是 规格值id，规格名id 的连接字符串，如：15$1-19#6-28
     */
    private Long keyId;

    // 规格名【颜色，尺寸。。。】
    private String key;

    // 规格值id
    private Long valueId;

    // 规格值【青芒色，M/S/XL。。。】
    private String value;

}
