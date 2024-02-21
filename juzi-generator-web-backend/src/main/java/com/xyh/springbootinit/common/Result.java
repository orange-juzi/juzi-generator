package com.xyh.springbootinit.common;


import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public static <T> Result<T> build(ResultCodeEnum resultCodeEnum, T data) {
        return build(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), data);
    }

    public static <T> Result<T> build(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
