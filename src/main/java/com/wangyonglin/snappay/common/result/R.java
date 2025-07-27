package com.wangyonglin.snappay.common;

import com.wangyonglin.snappay.common.constant.ErrorMessage;
import com.wangyonglin.snappay.common.constant.HttpStatus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Timer;

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
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.ERROR;

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
    public static <T> R<T> error(ErrorMessage error)
    {

        return construct(null, error.getCode(), error.getMessage());
    }



    private static <T> R<T> construct(T data, int code, String message)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
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
}