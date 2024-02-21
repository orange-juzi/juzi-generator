package com.xyh.springbootinit.exception;

import com.xyh.springbootinit.common.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private Integer code;          // 错误状态码
    private String message;        // 错误消息

    private ResultCodeEnum resultCodeEnum;     // 封装错误状态码和错误消息

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = message;
    }

}
