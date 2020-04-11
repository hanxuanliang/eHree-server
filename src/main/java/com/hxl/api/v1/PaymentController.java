package com.hxl.api.v1;

import com.hxl.core.annotations.ScopeLevel;
import com.hxl.service.WxPaymentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.util.Map;

/**
 * 支付 api
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/9 10:20
 */
@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {

    @Resource
    private WxPaymentService wxPaymentService;

    @ScopeLevel
    @PutMapping("/pay/order/{id}")
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long orderId) {
        return null;
    }
}
