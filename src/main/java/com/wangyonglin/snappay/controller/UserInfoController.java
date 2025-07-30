package com.wangyonglin.snappay.controller;

import com.wangyonglin.snappay.core.WechatObject;

import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class UserInfoController extends WechatObject {



    public Mono<ServerResponse> openid(ServerRequest request) {
        String js_code= request.queryParam("js_code").orElse("");
        Assert.isTrue(isNotEmpty(js_code), "The parameter 'js_code' cannot be empty or null!");
        String appid= getAppId();
        Assert.isTrue(isNotEmpty(appid), "The parameter 'appid' cannot be empty or null!");
        String appSecret= getAppSecret();
        Assert.isTrue(isNotEmpty(appSecret), "The parameter 'appSecret' cannot be empty or null!");

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.weixin.qq.com")
               // .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
               // .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("name", "张和");
//        formData.add("color", "blue");

        return    webClient.method(HttpMethod.GET)
                .uri("/sns/oauth2/access_token?appid={appid}&secret={appSecret}&code={js_code}&grant_type=authorization_code",
                        appid, appSecret,js_code)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class).flatMap(response->{
                    return bodyValue(response);
                }).onErrorResume(this::errorValue);
    }
}
