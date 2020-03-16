package com.hxl.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * PageCounter page业务对象
 * <p>
 * 【service 和 controller 之间的对象传递交换，隶属于 业务对象】
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/16 11:38
 */
@Getter
@Setter
@Builder
public class PageCounter {

    private Integer page;

    private Integer count;

}
