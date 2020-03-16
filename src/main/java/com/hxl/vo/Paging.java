package com.hxl.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 自己封装的分页对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 11:46
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Paging<T> {

    // 所有的总数据量
    private Long total;

    // 当前返回数量
    private Integer count;

    // 当前返回页
    private Integer page;

    // 总共有多少页
    private Integer totalPage;

    // 返回的真正的 data 数据
    private List<?> items;

    public Paging(Page<T> pageT) {
        this.initPageParameters(pageT);
        this.items = pageT.getContent();
    }

    void initPageParameters(Page<T> pageT) {
        this.total = pageT.getTotalElements();
        this.count = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }

}
