package com.hxl.api.v1;

import com.hxl.core.LocalUser;
import com.hxl.core.annotations.ScopeLevel;
import com.hxl.dto.OrderDTO;
import com.hxl.service.OrderService;
import com.hxl.vo.OrderIdVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * order api接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 21:52
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Resource
    private OrderService orderService;

    @ScopeLevel
    @PutMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getLocalUser().getId();

        return null;
    }

}
