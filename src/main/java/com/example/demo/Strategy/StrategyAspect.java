package com.example.demo.Strategy;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class StrategyAspect {
    private final ConcurrentHashMap<String, RateLimiter> RATE_LIMITER  = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.example.demo.Strategy.Strategy)")
    public void serviceLimit() {}

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature method_sig = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(method_sig.getName(), method_sig.getParameterTypes());
        Strategy annotation = currentMethod.getAnnotation(Strategy.class);
        double limitNum = annotation.token_num();
        String functionName = method_sig.getName(); // 限流策略

        if (!RATE_LIMITER.containsKey(functionName))
            RATE_LIMITER.put(functionName, RateLimiter.create(limitNum));
        RateLimiter rateLimiter = RATE_LIMITER.get(functionName);

        if(rateLimiter.tryAcquire())  return point.proceed();
        else throw new StrategyException(); //返回异常，此处为429
    }
}