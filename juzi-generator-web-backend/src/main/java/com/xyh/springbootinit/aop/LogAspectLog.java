package com.xyh.springbootinit.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
/**
 * 切面类，日志
 */
@Aspect //声明为切面类
@Component
@Order(1) //标记切点的优先级,i越小,优先级越高
public class LogAspectLog {

    //定义切点表达式：*，第一个返回值，第二个类名，第三个方法名
    @Pointcut("execution(public * com.xyh.springbootinit.controller.*.*(..)) && !execution(public * com.xyh.crmitem.controller.*.*List(..))")
    //使用一个返回值为void，方法体为空的方法来命名切入点
    public void myPointCut(){};

    private final Logger logger = LoggerFactory.getLogger(LogAspectLog.class);


    //前置通知
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        logger.info("-----------@Before 前置通知-----------");
        logger.info("前置通知：模拟执行权限检查...");
        logger.info("目标类是={}", joinPoint.getTarget());
        logger.info("被植入增强目标为={}", joinPoint.getSignature());

        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        //url
        logger.info("请求路径URL={}",request.getRequestURL());
        //method
        logger.info("请求方式Method={}",request.getMethod());
        //ip
        logger.info("请求IP={}",request.getRemoteAddr());
        //类方法
        logger.info("请求类方法ClassMethod={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //参数
        logger.info("请求参数Args={}",joinPoint.getArgs());
    }

    //后置通知
/*	@AfterReturning("myPointCut()")
	public void myAfterReturning(JoinPoint joinPoint) {
		logger.info("-----------@AfterReturning 后置通知-----------");
		logger.info("后置通知：模拟记录日志...");
		logger.info("返回值 response={}", joinPoint.toString());
	}*/

    //后置通知
    @AfterReturning(returning = "objects", pointcut = "myPointCut()")
    public void myAfterReturning(Object objects) {
        logger.info("-----------@AfterReturning 后置通知-----------");
        logger.info("后置通知：模拟记录日志...");
        logger.info("返回值 response={}", objects);
    }
    /**
     * 环绕通知
     * @param proceedingJoinPoint 是JoinPoint的子接口，表示可以执行目标方法
     * @return Object
     * 必须接收一个参数，类型为ProceedingJoinPoint
     * @必须 throws Throwable
     */
    @Around("myPointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //开始
        logger.info("-----------@Around 环绕通知start-----------");
        logger.info("环绕开始：开启之前，模拟开启事务...");
        long time = System.currentTimeMillis();
        //执行当前目标方法
        Object obj=proceedingJoinPoint.proceed();
        //结束
        logger.info("环绕结束：模拟关闭事务...");
        time = System.currentTimeMillis() - time;
        logger.info("方法用时Time={}", time +"（毫秒）");
        logger.info("-----------@Around 环绕通知end-----------");
        return obj;
    }

    //异常通知
    @AfterThrowing(value="myPointCut()", throwing="e")
    public void myAfterThrowing(JoinPoint joinPoint,Throwable e) {
        logger.info("-----------@AfterThrowing 异常通知-----------");
        logger.info("异常通知：出错了"+ e);
        logger.info("异常通知：出错了getStackTrace={}", e.getStackTrace());
        logger.info("异常通知：出错了Throwable...具体如下", e);
    }

    //最终通知
    @After("myPointCut()")
    public void myAfter() {
        logger.info("-----------@After 最终通知-----------");
        logger.info("最终通知：模拟方法结束后的释放资源...");
    }


}