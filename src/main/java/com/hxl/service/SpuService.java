package com.hxl.service;

import com.hxl.model.Spu;
import com.hxl.repository.SpuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("updateTime").descending());
        return spuRepository.findAll(page);
    }
}
