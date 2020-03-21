package com.hxl.service;

import com.hxl.model.GridCategory;
import com.hxl.repository.GirdCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 九宫格分类 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 9:52
 */
@Service
@Slf4j
public class GridCategoryService {

    @Resource
    private GirdCategoryRepository girdCategoryRepository;

    public List<GridCategory> getAllGridCategory() {
        return girdCategoryRepository.findAll();
    }
}
