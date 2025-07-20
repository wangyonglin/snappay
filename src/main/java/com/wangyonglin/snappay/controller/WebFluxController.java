package com.wangyonglin.snappay.controller;

import com.wangyonglin.snappay.config.WechatPayConfig;
import com.wangyonglin.snappay.service.WechatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author CoderJia
 * @create 2024/10/19 下午 08:05
 * @Description
 **/
@RestController
@RequestMapping("/api")
@Repository
public class WebFluxController  {

    private final WechatPayConfig config;

    public WebFluxController(WechatPayConfig config) {
        this.config = config;
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just(config.getMchId());
    }

    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "World", "From", "WebFlux", "Controller", "in", "Spring Boot 3!");
    }
}