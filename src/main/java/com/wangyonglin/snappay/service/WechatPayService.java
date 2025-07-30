package com.wangyonglin.snappay.service;


import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

public interface WechatPayService {

    public PrepayWithRequestPaymentResponse prepay(Config config,
                                                   String appId,
                                                   String merchantId,
                                                   String notifyUrl,
                                                   Integer total,
                                                   String productTitle,
                                                   String outTradeNo,
                                                   String userOpenId);
    void close(String mchid, String outTradeNo);

    Transaction query(String mchid, String outTradeNo);
}
