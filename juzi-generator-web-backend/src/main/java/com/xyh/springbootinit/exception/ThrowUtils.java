package com.xyh.springbootinit.exception;


import com.xyh.springbootinit.common.ResultCodeEnum;

/**
 * 抛异常工具类
 *
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param resultCodeEnum
     */
    public static void throwIf(boolean condition, ResultCodeEnum resultCodeEnum) {
        throwIf(condition, new BusinessException(resultCodeEnum));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param resultCodeEnum
     * @param message
     */
    public static void throwIf(boolean condition, ResultCodeEnum resultCodeEnum, String message) {
        throwIf(condition, new BusinessException(resultCodeEnum, message));
    }
}
