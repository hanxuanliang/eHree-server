package com.hxl.repository;

import com.hxl.model.Spu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spu Dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 8:14
 */
public interface SpuRepository extends JpaRepository<Spu, Long> {

    /**
     * id 查找相应的 Spu
     *
     * @param id spuid
     * @return spu商品
     * @date: 2020/3/16 8:15
     */
    Spu findOneById(Long id);
}
