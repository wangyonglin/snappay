package com.wangyonglin.snappay.controller;

import com.wangyonglin.snappay.common.result.R;

import com.wangyonglin.snappay.impl.WechatPayServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
//@RequestMapping("/wechatpay")
@Repository
public class WechatPayController {


    private WechatPayServiceImpl service;

    @GetMapping("/unifiedOrder")
    public Mono<R> unifiedOrder() {
         R<String> dd= R.ok().data("wangyonglin");
        return Mono.just(dd);
    }

   // @GetMapping("/test")
    public Mono<ServerResponse> test(ServerRequest request) {

        java.util.Optional<String> id= request.queryParam("id");
        if(!id.isEmpty()){

        }
        Mono<ServerResponse> render = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(id.get());

        return render;
    }


//    public Mono<ServerResponse> getUserById(ServerRequest request) {
//        try {
//            Integer id = Integer.valueOf(request.pathVariable("id"));
//            return userService.getUserById(id)
//                    .flatMap(user -> ServerResponse.ok()
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .body(BodyInserters.fromValue(user)))
//                    .switchIfEmpty(ServerResponse.notFound().build());
//        } catch (NumberFormatException e) {
//            return ServerResponse.badRequest()
//                    .body(BodyInserters.fromValue("Invalid ID format"));
//        }
//    }

}
