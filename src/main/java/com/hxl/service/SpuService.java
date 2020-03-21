package com.hxl.service;

import com.hxl.model.Spu;
import com.hxl.repository.SpuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Spu service 业务层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/15 22:15
 */
@Service
@Slf4j
public class SpuService {

    @Resource
    private SpuRepository spuRepository;

    public Spu getSpu(Long id) { return spuRepository.findOneById(id); }

    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("updateTime").descending());
        return spuRepository.findAll(page);
    }

    public Page<Spu> getByCategoryId(Long cid, Boolean isRoot, Integer pageNum, Integer count) {
        Pageable page = PageRequest.of(pageNum, count);
        if (isRoot) {
            return spuRepository.findByRootCategoryIdOrderByUpdateTime(cid, page);
        } else {
            return spuRepository.findByCategoryIdOrderByUpdateTimeDesc(cid, page);
        }
    }
}
