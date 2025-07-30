package com.wangyonglin.webflux.controller;


import com.wangyonglin.webflux.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author CoderJia
 * @create 2024/10/19 下午 08:05
 * @Description
 **/
//@RestController
//@RequestMapping("/api")
//@Repository
@Slf4j
public class WebFluxController  {



//    @GetMapping("/mono")
//    public Mono<String> getMono() {
//        return Mono.just("");
//    }
//
//    @GetMapping("/flux")
//    public Flux<String> getFlux() {
//        return Flux.just("Hello", "World", "From", "WebFlux", "Controller", "in", "Spring Boot 3!");
//    }

    public Mono<ServerResponse> bodyValue(Object body){
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(new MediaType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .bodyValue(Optional.ofNullable(body).orElse(""));
    }

    public Mono<ServerResponse> errorValue(Throwable throwable){
        log.error("Error:", throwable);
        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(new MediaType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .bodyValue(R.fail(throwable.getMessage()));
    }

    public boolean isNotEmpty(String param){
        return param != null && param.trim().length() > 0;
    }

}

//Content-Type: application/x-www-form-urlencoded
//return request.formData().flatMap(form -> {
//String name = form.getFirst("name");
//    Assert.isTrue(isNotEmpty(name), "The parameter 'name' cannot be empty or null!");
//String ip = form.getFirst("ip");
//    Assert.isTrue(isNotEmpty(ip), "The parameter 'ip' cannot be empty or null!");
//        return bodyValue(ipService.getIps());
//        }).onErrorResume(this::errorValue);
