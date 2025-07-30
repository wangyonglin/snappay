package com.wangyonglin.snappay.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wangyonglin.snappay.service.WechatPayService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor // Lombok注解，自动生成构造函数注入代码。
public class WechatPayServiceImpl implements WechatPayService {


    @Override
    public PrepayWithRequestPaymentResponse prepay(Config config,
                                                   String appId,
                                                   String merchantId,
                                                   String notifyUrl,
                                                   Integer total,
                                                   String productTitle,
                                                   String outTradeNo,
                                                   String userOpenId) throws ServiceException{

        // 构建service
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).signType("RSA").build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        // 金额
        amount.setTotal(total);
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        // 支付项目名称
        request.setDescription(productTitle);
        // 回调地址
        request.setNotifyUrl(notifyUrl);
        // 交易编号
        request.setOutTradeNo(outTradeNo);
        Payer payer = new Payer();
        // 用户openid
        payer.setOpenid(userOpenId);
        request.setPayer(payer);
        log.info("请求预支付下单，请求参数：{}", JSONObject.toJSONString(request));
        // 调用下单方法，得到应答
        PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse = service.prepayWithRequestPayment(request);
        return prepayWithRequestPaymentResponse;
    }

    @Override
    public void close(String mchid, String outTradeNo) {

    }

    @Override
    public Transaction query(String mchid, String outTradeNo) {
        return null;
    }


}
