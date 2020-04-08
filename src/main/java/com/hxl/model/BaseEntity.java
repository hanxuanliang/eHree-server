package com.hxl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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

    /**
     * 要把模型默认的插入禁止掉，即使是标注了@JsonIgnore，也会默认为null，
     * 只要模型有值插入，就不会触发数据库的自更新
     *
     * @date: 2020/4/8 11:31
     */
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Date createTime;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Date updateTime;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Date deleteTime;
}
