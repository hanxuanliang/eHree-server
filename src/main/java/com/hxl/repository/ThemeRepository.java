package com.hxl.repository;

import com.hxl.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Theme Dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 10:23
 */
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    /**
     * 传入一组 主题name
     *【query里面写的其实是一个个JPQL，操作的其实是model：Theme】
     * @param names 主题names
     * @return List<Theme>
     * @date: 2020/3/21 10:25
     */
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(List<String> names);

    /**
     * 获取特定name的主题
     *
     * @param name 主题name
     * @return Optional<Theme>
     * @date: 2020/3/21 10:27
     */
    Optional<Theme> findByName(String name);
}
