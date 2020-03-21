package com.hxl.service;

import com.hxl.model.Category;
import com.hxl.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/20 19:27
 */
@Service
@Slf4j
public class CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    public Map<String, List<Category>> getAllCategory() {
        List<Category> roots = categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = categoryRepository.findAllByIsRootOrderByIndexAsc(false);

        Map<String, List<Category>> categories = new HashMap<>(4);
        categories.put("roots", roots);
        categories.put("subs", subs);
        return categories;
    }

}
