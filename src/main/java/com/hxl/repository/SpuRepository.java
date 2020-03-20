package com.hxl.repository;

import com.hxl.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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

    /**
     * 通过类型id，以及根据更新时间倒序排列
     *
     * @param cid      类型id
     * @param pageable 分页信息
     * @return Page<Spu>
     * @date: 2020/3/16 17:32
     */
    Page<Spu> findByCategoryIdOrderByUpdateTimeDesc(Long cid, Pageable pageable);

    /**
     * 通过类型id，以及根据更新时间排列
     *
     * @param cid       类型id
     * @param pageable  分页信息
     * @return Page<Spu>
     * @date: 2020/3/16 17:39
     */
    Page<Spu> findByRootCategoryIdOrderByUpdateTime(Long cid, Pageable pageable);
}
