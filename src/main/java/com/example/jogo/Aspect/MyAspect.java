package com.example.jogo.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MyAspect {
//    @Pointcut("execution(public * com.example.jogo.Controller.UserController.*(..))")
//    public void myPointcut(){}
//
//    @Before(value = "myPointcut()")
//    public void myBefore(JoinPoint joinPoint){
//        System.out.println("***** before *****");
//        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        assert attributes != null;
//        HttpServletRequest request = attributes.getRequest();
//
////        System.out.println("requestURL:"+request.getRequestURL());
////        System.out.println("method:"+request.getMethod());
////        System.out.println("target:"+joinPoint.getTarget());
////        System.out.println("args:"+Arrays.toString(joinPoint.getArgs()));
//    }
//
//    @Around(value = "myPointcut()")
//    public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("***** Around 执行方法前 *****");
//        Object o = joinPoint.proceed();
//        System.out.println("***** Around 执行方法后 *****");
//        return o;
//    }
}
