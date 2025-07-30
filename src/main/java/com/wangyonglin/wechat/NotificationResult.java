package com.wangyonglin.wechat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Getter
@Setter
public class NotificationResult {
    private String code="SYSTEM_ERROR";
    private String message="系统失败";

   public static NotificationResult ok(){
        NotificationResult result = new NotificationResult();
        result.code="SUCCESS";
        result.message="OK";
       return result;
   }

    public static NotificationResult fail(){
        NotificationResult result = new NotificationResult();
        result.code="SYSTEM_ERROR";
        result.message="系统失败";
        return result;
    }

    public Mono<ServerResponse> build() {
        if (getCode().equals("SUCCESS")) {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(this);
        }else{
            return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(this);
        }
    }
}
