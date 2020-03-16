package com.hxl.repository;

import com.hxl.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Banner dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 10:43
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {

    /**
     * 通过 bannerid 来查找 对应的banner
     *
     * @param id banner id
     * @return banner
     * @date: 2020/3/14 10:45
     */
    Banner findOneById(Long id);

    /**
     * 通过 bannername 来查找 对应的banner
     *
     * @param name banner name
     * @return banner
     * @date: 2020/3/14 10:46
     */
    Banner findOneByName(String name);
}
