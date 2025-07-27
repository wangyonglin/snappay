package com.wangyonglin.snappay.controller;

import com.wangyonglin.snappay.config.WechatConfiguration;
import com.wangyonglin.snappay.exception.WechatException;
import com.wangyonglin.snappay.modules.SnowflakeIdGenerator;
import com.wangyonglin.snappay.result.R;

import com.wangyonglin.snappay.impl.WechatPayServiceImpl;
import com.wangyonglin.snappay.service.WechatPayService;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@Repository
@RequiredArgsConstructor
public class WechatController  extends WebFluxController{

    private final WechatConfiguration configuration;
    private SnowflakeIdGenerator snowflake = new SnowflakeIdGenerator(1,12);
    private  WechatPayService service= new WechatPayServiceImpl();


    public Mono<ServerResponse> prepay(ServerRequest request) {
        PrepayWithRequestPaymentResponse response;
        String outTradeNo= String.valueOf(snowflake.nextId());
       String userOpenId= request.queryParam("userOpenId").get();
        log.info("outTradeNo:"+outTradeNo);
        log.info("userOpenId:"+userOpenId);
        log.info("privateKeyPath:"+configuration.getPrivateKeyPath());
        try {
             response=service.prepay(
                    configuration,
                    100,
                    "wangyonglin",
                    outTradeNo,
                    userOpenId);
        } catch (ServiceException e) {
            e.printStackTrace();
            return R.fail(e.getMessage()).build();
        }
        return R.ok().data(response).build();
    }

    public Mono<ServerResponse> notifyUrl(ServerRequest request){
        return R.ok().data("支付成功").build();
    }
}
