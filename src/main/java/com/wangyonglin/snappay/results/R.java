package com.wangyonglin.snappay.results;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author ry.yqkj
 */
@Getter
@Setter
public class R<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = 200;

    /** 失败 */
    public static final int FAIL = 500;

    private int code;
    private String message;
    private T data;

    public static <T> R<T> ok()
    {
        return construct(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> error()
    {
        return construct(null, FAIL, "操作失败");
    }
    public static <T> R<T> error(int error)
    {
        return construct(null, error, "未知错误");
    }



    private static <T> R<T> construct(T data, int code, String message)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }

    public static <T> R<T> fail()
    {
        return construct(null, 500, "未知错误");
    }
    public static <T> R<T> fail(String message) {
        return construct(null,500,message);
    }
    public static <T> R<T> fail(int code,String message) {
        return construct(null,code,message);
    }

    public static <T> R<T> ok(T t)
    {
        return construct(t, SUCCESS, "操作成功");
    }

    public static <T> Boolean isError(R<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret)
    {
        return R.SUCCESS == ret.getCode();
    }

    public   <T> R<T> message(String message){

        return construct(null,getCode(),message);

    }
    public  <T> R<T> data(T t){

        return construct(t,getCode(),getMessage());
    }
    public byte[] toJSONBytes(){
        return JSON.toJSONBytes(this);
    }

    public Mono<ServerResponse> build(){
        if(getCode()==200){
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this);
        }else{
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this);
        }

    }
}