package com.hxl.repository;

import com.hxl.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * sku dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 0:08
 */
public interface SkuRepository extends JpaRepository<Sku, Long> {

    List<Sku> findAllByIdIn(List<Long> ids);

    // 使用了乐观锁，有效解决预减库存
    @Modifying
    @Query("update Sku s set s.stock = s.stock - :quantity\n" +
            "where s.id = :skuId\n" +
            "and s.stock >= :quantity")
    int reduceStock(Long skuId, Long quantity);

    @Modifying
    @Query("update Sku s set s.stock=s.stock+(:quantity) where s.id = :sid")
    void recoverStock(@Param(value = "sid") Long sid, @Param(value = "quantity") Long quantity);
}
