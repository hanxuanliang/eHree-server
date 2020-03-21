package com.hxl.vo;

import com.hxl.model.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/20 20:32
 */
@Setter
@Getter
@ToString
public class CategoryPureVO {

    private Long id;

    private String name;

    private String description;

    private Boolean isRoot;

    private Long parentId;

    private String img;

    private Long index;


    public CategoryPureVO(Category category) {
        BeanUtils.copyProperties(category, this);
    }
}
