package com.hxl.vo;

import com.hxl.model.Category;
import com.hxl.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/6 21:27
 */
@Getter
@Setter
@ToString
public class CouponCategoryVO extends CouponPureVO {

    public List<CategoryPureVO> categoryPureVOList = new ArrayList<>();

    public CouponCategoryVO(Coupon coupon) {
        super(coupon);
        // 1. coupon 中就已经携带了对应类目的list，取出来即可
        List<Category> categories = coupon.getCategoryList();
        // 2. 然后在 foreach 中去把获取的 categories 中的每一项加到 categoryPureVOList 中
        categories.forEach(category -> {
            CategoryPureVO categoryPureVO = new CategoryPureVO(category);
            categoryPureVOList.add(categoryPureVO);
        });
    }

}
