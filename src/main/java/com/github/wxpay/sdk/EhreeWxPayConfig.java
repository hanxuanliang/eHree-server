package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * 项目中使用的 wx_pay 配置类
 * 【大概重要的函数就是 前3个 】
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/11 16:05
 */
public class EhreeWxPayConfig extends WXPayConfig {

    // 小程序的 appId
    @Override
    String getAppID() {
        return null;
    }

    // 商户号，小程序的商户平台中获取【目前无】
    @Override
    String getMchID() {
        return null;
    }

    // 商户平台的 key【目前无】
    @Override
    String getKey() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 6 * 1000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 8 * 1000;
    }

    // 微信证书，本项目中不使用
    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return null;
    }
}
