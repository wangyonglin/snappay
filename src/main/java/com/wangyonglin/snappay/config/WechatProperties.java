package com.wangyonglin.snappay.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:wechatpay.properties")
@ConfigurationProperties(prefix = "wechatpay")
@Data
public class WechatProperties {
    private String appId;
    private String appSecret;
    private String merchantId;
    private String privateKeyPath;
    private String publicKeyPath;
    private String publicKeyId;
    private String merchantSerialNumber;
    private String apiV3Key;
    private String notifyUrl;
}