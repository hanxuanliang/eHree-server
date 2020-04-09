package com.hxl.vo;

import com.hxl.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/4/9 9:25
 */
@ToString
@Setter
@Getter
public class OrderPureVO extends Order {

    private Long period;

    // TODO 待考虑返不返回
    private Date createTime;

    public OrderPureVO(Order order, Long period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }

}
