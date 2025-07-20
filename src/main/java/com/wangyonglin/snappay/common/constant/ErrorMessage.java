package com.wangyonglin.snappay.common.constant;

public enum ErrorMessage {

    SUCCESS(200, "操作成功"),
    UNAUTHORIZED(401, "没有权限，请联系管理员授权"),
    FORBIDDEN(403, "访问受限，授权过期"),
    NOT_FOUND(404, "资源,服务未找到"),
    SYSTEM_ERROR(500, "系统内部错误"),
    BUSINESS_ERROR(501, "业务异常"),
    PARAMS_ERROR(502, "请求参数有误"),
    ;

    private final int code;
    private final String message;

    ErrorMessage(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}