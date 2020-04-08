package com.hxl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/6 23:28
 */
@ToString
@Getter
@Setter
public class OrderAddressDTO {

    private String username;

    private String province;

    private String city;

    private String county;

    private String mobile;

    // 国家码
    private String nationalCode;

    // 邮政编码
    private String postalCode;

    // 地址的详情信息
    private String detail;

}
