package com.hxl.service;

import com.hxl.model.Banner;
import com.hxl.repository.BannerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Banner Service 实现类
 *
 * 【本次开发采取不使用 接口+实现类 ，因为后期不会对service的代码进行太多修改】
 * @Author: hanxuanliang
 * @Date: 2020/3/14 8:54
 */
@Service
public class BannerService {

    @Resource
    private BannerRepository bannerRepository;

    /**
     * 通过 bannerName 来查找 Banner
     *
     * @param name bannerName
     * @return banner
     * @date: 2020/3/14 8:56
     */
    public Banner getByName(String name) {
        return bannerRepository.findOneByName(name);
    }

}
