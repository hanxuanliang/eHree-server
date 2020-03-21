package com.hxl.api.v1;

import com.hxl.exception.NotFoundException;
import com.hxl.model.Category;
import com.hxl.model.GridCategory;
import com.hxl.service.CategoryService;
import com.hxl.service.GridCategoryService;
import com.hxl.vo.CategoryAllVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * spu分类 API接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/20 19:26
 */
@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoryAllVO getAllCategory() {
        Map<String, List<Category>> categories = categoryService.getAllCategory();

        CategoryAllVO categoryAllVO = new CategoryAllVO(categories);
        if (categoryAllVO == null) {
            throw new NotFoundException(30001);
        }

        return categoryAllVO;
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getAllGridCategory() {
        List<GridCategory> gridCategories = gridCategoryService.getAllGridCategory();
        if (gridCategories.isEmpty()) {
            throw new NotFoundException(30009);
        }

        return gridCategories;
    }
}
