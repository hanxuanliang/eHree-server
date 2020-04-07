package com.hxl.service;

import com.hxl.model.Sku;
import com.hxl.repository.SkuRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * sku service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/7 0:07
 */
@Service
public class SkuService {

    @Resource
    private SkuRepository skuRepository;

    public List<Sku> getSkuListByIds(List<Long> ids) {
        return skuRepository.findAllByIdIn(ids);
    }
}
