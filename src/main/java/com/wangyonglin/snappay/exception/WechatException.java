package com.wangyonglin.snappay.exception;

import lombok.Getter;

@Getter
public class WechatException extends RuntimeException {
    private final String message;
    public WechatException(String message) {
        super(message);
        this.message = message;
    }

}
