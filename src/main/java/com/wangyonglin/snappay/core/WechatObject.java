package com.wangyonglin.snappay.core;

import com.wangyonglin.snappay.config.WechatProperties;
import com.wangyonglin.snappay.results.R;
import com.wangyonglin.snappay.exception.WechatException;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Repository
public class WechatObject extends WechatProperties {


    public RSAAutoCertificateConfig getRSAAutoCertificateConfig() throws WechatException {
        // 这里把Config作为配置Bean是为了避免多次创建资源，一般项目运行的时候这些东西都确定了
        // 具体的参数改为申请的数据，可以通过读配置文件的形式获取
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(getMerchantId())
                .privateKeyFromPath(getPublicKeyPath())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
    }
    public Mono<ServerResponse> bodyValue(Object body){
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(new MediaType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .bodyValue(Optional.ofNullable(body).orElse(""));
    }

    public Mono<ServerResponse> errorValue(Throwable throwable){
        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(new MediaType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .bodyValue(R.fail(throwable.getMessage()));
    }
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
