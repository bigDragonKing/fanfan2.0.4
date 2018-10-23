package com.fanfan.alon.exception;

public class SellException extends RuntimeException {
    private Integer code;
    private String message;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
