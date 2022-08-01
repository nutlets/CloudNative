/*
 * @Author: zhao-yz
 * @Date: 2022-07-30 12:56:01
 * @LastEditors: zhao-yz
 * @LastEditTime: 2022-07-31 12:26:26
 */
package com.example.demo.Strategy;

import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class StrategyAspect {
    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER  = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;

    @Pointcut("@annotation(com.example.demo.Strategy.Strategy)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {


        //获取拦截的方法名
        MethodSignature method_sig = (MethodSignature) point.getSignature();
        //返回被织入增加处理目标对象
        Object target = point.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(method_sig.getName(), method_sig.getParameterTypes());
        //获取注解信息
        Strategy annotation = currentMethod.getAnnotation(Strategy.class);
        double limitNum = annotation.token_num(); //获取注解每秒加入桶中的token
        String functionName = method_sig.getName(); // 注解所在方法名区分不同的限流策略

        if (!RATE_LIMITER.containsKey(functionName))
            RATE_LIMITER.put(functionName, RateLimiter.create(limitNum));
        rateLimiter=RATE_LIMITER.get(functionName);
        
        if(rateLimiter.tryAcquire())  return point.proceed();
        else throw new StrategyException();
    }
}