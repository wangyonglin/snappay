package com.wangyonglin.snappay.controller;

import com.wangyonglin.webflux.controller.WebFluxController;
import com.wangyonglin.snappay.config.WechatConfiguration;
import com.wangyonglin.modules.SnowflakeIdGenerator;
import com.wangyonglin.webflux.result.R;

import com.wangyonglin.snappay.impl.WechatPayServiceImpl;
import com.wangyonglin.snappay.service.WechatPayService;
import com.wangyonglin.wechat.NotificationResult;
import com.wangyonglin.wechat.WechatException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@Repository
@RequiredArgsConstructor
public class WechatController  extends WebFluxController {

    private final WechatConfiguration configuration;
    private SnowflakeIdGenerator snowflake = new SnowflakeIdGenerator(1,12);
    private  WechatPayService service= new WechatPayServiceImpl();

    public Mono<ServerResponse> prepay(ServerRequest request) {
        return request.formData().flatMap(form -> {
             String outTradeNo= String.valueOf(snowflake.nextId());
        Assert.isTrue(isNotEmpty(outTradeNo), "The parameter 'outTradeNo' cannot be empty or null!");
        String userOpenId= request.queryParam("userOpenId").orElse("");
        Assert.isTrue(isNotEmpty(userOpenId), "The parameter 'userOpenId' cannot be empty or null!");
        try {
            PrepayWithRequestPaymentResponse   response=service.prepay(
                    configuration,
                    100,
                    "wangyonglin",
                    outTradeNo,
                    userOpenId);
            return bodyValue(response);
        } catch (ServiceException e) {
            return Mono.error(e);
        }
        }).onErrorResume(this::errorValue);

    }


    @PostMapping("/notify")
    public Mono<ServerResponse> notifyUrl(ServerRequest request)  {
        NotificationParser parser = new NotificationParser(configuration.rsaAutoCertificateConfig());
       return request.bodyToMono(String.class).flatMap(val->{
           log.info(val);
           String signature = request.headers().firstHeader("Wechatpay-Signature");
           Assert.isTrue(isNotEmpty(signature), "The parameter 'Wechatpay-Signature' cannot be empty or null!");
           String serial = request.headers().firstHeader("Wechatpay-Serial");
           Assert.isTrue(isNotEmpty(serial), "The parameter 'Wechatpay-Serial' cannot be empty or null!");
           String nonce = request.headers().firstHeader("Wechatpay-Nonce");
           Assert.isTrue(isNotEmpty(nonce), "The parameter 'Wechatpay-Nonce' cannot be empty or null!");
           String timestamp = request.headers().firstHeader("Wechatpay-Timestamp");
           Assert.isTrue(isNotEmpty(timestamp), "The parameter 'Wechatpay-Timestamp' cannot be empty or null!");

           RequestParam requestParam=
              new RequestParam.Builder()
                    .serialNumber(serial)
                    .nonce(nonce)
                    .signature(signature)
                    .timestamp(timestamp)
                    .body(val) // HTTP 请求体 body
                    .build();
            try {
                Transaction transaction = parser.parse(requestParam, Transaction.class);
                Transaction.TradeStateEnum tradeState = transaction.getTradeState();
            if (tradeState.equals(Transaction.TradeStateEnum.SUCCESS)) {
                return NotificationResult.ok().build();
            }
            } catch (ValidationException e) {
                return Mono.error(e);
            }
           return NotificationResult.fail().build();
       }).onErrorResume(e->{
           log.error("sign verification failed: "+ e.getMessage());
           return NotificationResult.fail().build();
       });

    }
}
