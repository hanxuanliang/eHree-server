package com.hxl.service;

import com.hxl.model.Spu;
import com.hxl.repository.SpuRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Spu service 业务层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/15 22:15
 */
@Service
public class SpuService {

    @Resource
    private SpuRepository spuRepository;

    public Spu getSpu(Long id) {
        return spuRepository.findOneById(id);
    }

    public List<Spu> getLatestPagingSpu() {
        return spuRepository.findAll();
    }
}
