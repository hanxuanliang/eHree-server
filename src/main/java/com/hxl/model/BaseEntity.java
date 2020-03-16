package com.hxl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 实体类基类：包含一些基本的属性
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/14 16:07
 */
@Setter
@Getter
// 表名此类是映射基类：Superclass
@MappedSuperclass
public abstract class BaseEntity {

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Date deleteTime;
}
