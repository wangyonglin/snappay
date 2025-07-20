package com.wangyonglin.snappay.controller;

import com.wangyonglin.snappay.common.R;
import com.wangyonglin.snappay.core.WechatObject;
import com.wangyonglin.snappay.impl.WechatPayServiceImpl;
import com.wangyonglin.snappay.service.WechatPayService;



import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wechatpay")
@RequiredArgsConstructor
public class WechatPayController {
    private final WechatObject object;
    private  WechatPayService service=new WechatPayServiceImpl(object);

    @GetMapping("/unifiedOrder")
    public Mono<R> unifiedOrder() {

        try {
            PrepayWithRequestPaymentResponse response =service.prepay();
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

        R.ok().data("data",response);
        return Mono.just(r);
    }



}
