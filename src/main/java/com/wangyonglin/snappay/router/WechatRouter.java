package com.wangyonglin.snappay.router;

import com.wangyonglin.snappay.controller.WechatPayController;
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
    public RouterFunction<ServerResponse> routerFunctions(WechatPayController handler)
    {
        //MediaType.APPLICATION_JSON 设置响应类型 ，  handler::viewBooks  是 lambda 中的方法引用
        RouterFunction<ServerResponse> route =
                RouterFunctions
                        //这里就映射到 BookHandler 类里面的 viewBook 方法，/viewBookabc/{id}这个是我们这边给的访问路径
                        .route(RequestPredicates.GET("/viewBookabc/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::viewBook)

                        //这里就映射到 BookHandler 类里面的 viewBookHtml 方法，/viewBookHtml/{id}这个是我们这边给的访问路径
                        .andRoute(RequestPredicates.GET("/viewBookHtml/{id}")
                                .and(RequestPredicates.accept(MediaType.TEXT_HTML)), handler::viewBookHtml);
        return route;
    }
}
