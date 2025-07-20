package com.wangyonglin.snappay.core;

import com.wangyonglin.snappay.config.WechatPayConfig;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@RequiredArgsConstructor
public class WechatObject extends WechatPayConfig{


//OhQkzBWo8xNSASVz1evP9KL4eEw8pzeK
    public Config rsaPublicKeyConfig(){
        return new RSAPublicKeyConfig.Builder()
                .merchantId(getMerchantId())
                .privateKeyFromPath(getPrivateKeyPath())
                .publicKeyFromPath(getPublicKeyPath())
                .publicKeyId(getPublicKeyId())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
    }

    /**
     * 初始化商户配置
     * @return
     */
    @Bean
    public RSAAutoCertificateConfig rsaAutoCertificateConfig() {
        // 这里把Config作为配置Bean是为了避免多次创建资源，一般项目运行的时候这些东西都确定了
        // 具体的参数改为申请的数据，可以通过读配置文件的形式获取
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(getMerchantId())
                .privateKeyFromPath(getPrivateKeyPath())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
        return config;
    }

}
