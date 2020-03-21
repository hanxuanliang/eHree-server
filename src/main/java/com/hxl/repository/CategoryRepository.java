package com.hxl.repository;

import com.hxl.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 分类 dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/20 19:28
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 传入 True ==> 全部根节点
     * 传入 False ==> 全部的二级节点
     * 这样就可以巧妙的在多次查询中获取你想要的数
     *
     * @param isRoot 是不是root节点
     * @return List<Category>
     * @date: 2020/3/21 9:05
     */
    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);
}
