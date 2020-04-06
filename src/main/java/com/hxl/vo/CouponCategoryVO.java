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
        List<Category> categories = coupon.getCategoryList();
        categories.forEach(category -> {
            CategoryPureVO categoryPureVO = new CategoryPureVO(category);
            categoryPureVOList.add(categoryPureVO);
        });
    }

}
