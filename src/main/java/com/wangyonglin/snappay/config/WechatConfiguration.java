package com.wangyonglin.wechat.pay;

import com.wangyonglin.snappay.exception.WechatException;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class WechatConfiguration extends WechatProperties {


    public RSAAutoCertificateConfig rsaAutoCertificateConfig() throws WechatException {
        // 这里把Config作为配置Bean是为了避免多次创建资源，一般项目运行的时候这些东西都确定了
        // 具体的参数改为申请的数据，可以通过读配置文件的形式获取
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(getMerchantId())
                .privateKeyFromPath(getPublicKeyPath())
                .merchantSerialNumber(getMerchantSerialNumber())
                .apiV3Key(getApiV3Key())
                .build();
    }

}
