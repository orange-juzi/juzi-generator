package com.xyh.springbootinit.common;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return Result.build(ResultCodeEnum.SUCCESS, data);
    }

    /**
     * 失败
     *
     * @param resultCodeEnum
     * @return
     */
    public static Result<?> error(ResultCodeEnum resultCodeEnum) {
        return Result.build(resultCodeEnum, null);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static Result<?> error(int code, String message) {
        return Result.build(code, message, null);
    }

    /**
     * 失败
     *
     * @param resultCodeEnum
     * @return
     */
    public static Result<?> error(ResultCodeEnum resultCodeEnum, String message) {
        return Result.build(resultCodeEnum.getCode(), message, null);
    }
}
