package com.xyh.springbootinit.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 数据库切面类，日志
 *
 */
@Aspect //声明为切面类
@Component
@Order(1) //标记切点的优先级,i越小,优先级越高
public class SQLAspectLog {

    //定义切点表达式：*，第一个返回值，第二个类名，第三个方法名
    @Pointcut("execution(public * com.xyh.springbootinit.mapper.*.*(..))")
    //使用一个返回值为void，方法体为空的方法来命名切入点
    public void myPointCut() {
    }

    private final Logger logger = LoggerFactory.getLogger(SQLAspectLog.class);


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint 是JoinPoint的子接口，表示可以执行目标方法
     * @return Object
     * 必须接收一个参数，类型为ProceedingJoinPoint
     * @必须 throws Throwable
     */
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        //执行当前目标方法
        Object obj = proceedingJoinPoint.proceed();
        logger.info("数据库执行方法：{}", proceedingJoinPoint.getSignature());
        time = System.currentTimeMillis() - time;
        logger.info("数据库用时：{}", time + "（毫秒）");
        return obj;
    }

}