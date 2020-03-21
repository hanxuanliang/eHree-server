package com.hxl.repository;

import com.hxl.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 九宫格分类数据 dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 9:45
 */
public interface GirdCategoryRepository extends JpaRepository<GridCategory, Long> {

}
