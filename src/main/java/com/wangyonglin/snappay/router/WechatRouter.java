package com.wangyonglin.snappay.router;

import com.wangyonglin.snappay.controller.WechatController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration //配置类
public class WechatRouter
{
    //配置 Router Bean ，负责完成请求 URL 和 Handler 处理方法之间的映射。

    @Bean
    public RouterFunction<ServerResponse> routerFunctions(WechatController handler)
    {
               return RouterFunctions.route(RequestPredicates.GET("/wechat/prepay")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::prepay)
                        .andRoute(RequestPredicates.GET("/wechat/notifyurl")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::notifyUrl);
    }
}
