package com.hxl.repository;

import com.hxl.model.Sku;
import com.hxl.model.Spu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * sku dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 0:08
 */
public interface SkuRepository extends JpaRepository<Sku, Long> {

    List<Sku> findAllByIdIn(List<Long> ids);
}
