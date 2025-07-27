package com.wangyonglin.snappay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*

wechatpay.appId=x
wechatpay.merchantId=1614902012
wechatpay.privateKeyPath=apiclient_cert.pem
wechatpay.publicKeyPath=apiclient_key.pem
wechatpay.publicKeyId=
wechatpay.merchantSerialNumber=14F72464AB58C9ECC7F5B9EDE4448BF0A3DEF45C
wechatpay.apiV3Key=Ab4hTsxX6peCtWjECIENPhulrxoxlm80
wechatpay.notifyUrl=https://wangyonglin.com/notify/pay
*/

/*
new RSAAutoCertificateConfig.Builder()
                .merchantId(getMerchantId())
                .privateKeyFromPath(getPrivateKeyPath())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
 */
@Configuration
@PropertySource("classpath:wechatpay.properties")
@ConfigurationProperties(prefix = "wechatpay")
@Data
public class WechatProperties {
    private String appId;
    private String merchantId;
    private String privateKeyPath;
    private String publicKeyPath;
    private String publicKeyId;
    private String merchantSerialNumber;
    private String apiV3Key;
    private String notifyUrl;
}