package com.wangyonglin.snappay.impl;
import com.alibaba.fastjson.JSONObject;
import com.wangyonglin.snappay.common.R;
import com.wangyonglin.snappay.core.WechatObject;
import com.wangyonglin.snappay.service.WechatPayService;


import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor // Lombok注解，自动生成构造函数注入代码。
public class WechatPayServiceImpl implements WechatPayService {
    private final static Logger log = LoggerFactory.getLogger(WechatPayServiceImpl.class);
    private final WechatObject object;



    public R<PrepayWithRequestPaymentResponse> createOrder(Integer total,
                                                           String productTitle,
                                                           String outTradeNo,
                                                           String userOpenId,
                                                           String description) {
        JsapiServiceExtension service =
                new JsapiServiceExtension.Builder()
                        .config(object.rsaAutoCertificateConfig())
                        .signType("RSA") // 不填默认为RSA
                        .build();
        PrepayWithRequestPaymentResponse response = new PrepayWithRequestPaymentResponse();
        try {

            PrepayRequest request = new PrepayRequest();
            request.setAppid(object.getAppId());
            request.setMchid(object.getMerchantId());
            request.setDescription(description);
            request.setOutTradeNo(outTradeNo);
            request.setNotifyUrl(object.getNotifyUrl());
            Amount amount = new Amount();
            // 微信支付的单位是分，这里都需要乘以100
//            BigDecimal result = original.multiply(BigDecimal.valueOf(100));
//            int value = result.setScale(0, RoundingMode.HALF_UP).intValueExact();

            amount.setTotal(order.getAmount().multiply(new BigDecimal("100")).intValue());
            request.setAmount(amount);
            Payer payer = new Payer();
            payer.setOpenid(userOpenId);
            request.setPayer(payer);
            log.info("请求预支付下单，请求参数：{}", JSONObject.toJSONString(request));
            // 调用预下单接口
            response = service.prepayWithRequestPayment(request);
            log.info("订单【{}】发起预支付成功，返回信息：{}", outTradeNo, response);
        } catch (HttpException e) { // 发送HTTP请求失败
            log.error("微信下单发送HTTP请求失败，错误信息：{}", e.getHttpRequest());
            return R.error().message("下单失败");
        } catch (ServiceException e) { // 服务返回状态小于200或大于等于300，例如500
            log.error("微信下单服务状态错误，错误信息：{}", e.getErrorMessage());
            return R.error().message("下单失败");
        } catch (MalformedMessageException e) { // 服务返回成功，返回体类型不合法，或者解析返回体失败
            log.error("服务返回成功，返回体类型不合法，或者解析返回体失败，错误信息：{}", e.getMessage());
            return R.error().message("下单失败");
        }

        // 更新订单状态
        // 这里就可以更新订单状态为待支付之类的

        return R.ok().data(response);
    }

}
