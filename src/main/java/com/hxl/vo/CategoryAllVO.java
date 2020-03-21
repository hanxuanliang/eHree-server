package com.hxl.vo;

import com.hxl.model.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取全部的分类 VO
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/20 20:31
 */
@Setter
@Getter
@ToString
public class CategoryAllVO {

    private List<CategoryPureVO> roots;

    private List<CategoryPureVO> subs;

    public CategoryAllVO(Map<String, List<Category>> categories) {
        this.roots = categories.get("roots").stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());

        this.subs = categories.get("subs").stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
    }
}
