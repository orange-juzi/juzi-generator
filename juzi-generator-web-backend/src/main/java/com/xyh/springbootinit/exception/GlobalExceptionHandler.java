package com.xyh.springbootinit.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.xyh.springbootinit.common.Result;
import com.xyh.springbootinit.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        //处理后端验证失败产生的异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            log.error("后端验证执行异常", exception);
            return Result.build(500, exception.getBindingResult().getFieldError().getDefaultMessage(), null);
            //处理自定义异常
        } else if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            log.error("自定义执行异常", exception);
            return Result.build(exception.getCode(), exception.getMessage(), null);
        } else {
            log.error("系统内部异常", e);
            return Result.build(ResultCodeEnum.FAIL, null);
        }
    }

    /**
     * 登录验证处理异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<?> unLoginHandler(NotLoginException e) {
        log.error("登录失败验证异常", e);
        return Result.build(e.getCode(), e.getMessage(), null);
    }

    /**
     * 权限校验处理异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<?> unRoleHandler(NotRoleException e) {
        log.error("无此权限", e);
        return Result.build(e.getCode(), e.getMessage(), null);
    }
}
