package com.hxl.service;

import com.github.wxpay.sdk.EhreeWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.hxl.core.LocalUser;
import com.hxl.exception.ForbiddenException;
import com.hxl.exception.NotFoundException;
import com.hxl.exception.ServerErrorException;
import com.hxl.model.Order;
import com.hxl.repository.OrderRepository;
import com.hxl.utils.CommonUtil;
import com.hxl.utils.HttpRequestProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 支付 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/9 10:24
 */
@Service
public class WxPaymentService {

    @Resource
    private OrderRepository orderRepository;

    @Value("${ehree.order.pay-callback-host}")
    private String payCallbaclHost;

    @Value("${ehree.order.pay-callback-path}")
    private String payCallbackPath;

    private static EhreeWxPayConfig ehreeWxPayConfig = new EhreeWxPayConfig();

    public Map<String, String> preOrder(Long orderId) {
        Long uid = LocalUser.getLocalUser().getId();
        Optional<Order> optionalOrder = orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = optionalOrder.orElseThrow(() -> new NotFoundException(50009) );
        // 再次判断此时的订单过期时间，有以下几种情况：
        // 1. 买完 --> 立即过期。
        // 2. 历史订单 --> 是否多次支付，后者是多次拉起支付，中途会断开支付流程。
        // 3. postman测试，避开前端button置灰的禁止。
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = assembleWxPayConfig();
        wxPay.unifiedOrder();
        return null;
    }

    private WXPay assembleWxPayConfig() {
        WXPay wxPay;
        try {
            wxPay = new WXPay(WxPaymentService.ehreeWxPayConfig);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }

    // 准备 预调用api 的参数
    private Map<String, String> assemblePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        Map<String, String> data = new HashMap<>(16);
        data.put("body", "ehree");
        data.put("out_trade_no", orderNo);
        data.put("device_info", "ehree_device");
        data.put("fee_type", "CNY");
        data.put("trade_type", "JSAPI");

        data.put("totoal_fee", CommonUtil.yuanTofenPlainString(serverFinalPrice));
        data.put("open_id", LocalUser.getLocalUser().getOpenId());
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
        data.put("notify_url", this.payCallbaclHost + this.payCallbackPath);
        return data;
    }

}
