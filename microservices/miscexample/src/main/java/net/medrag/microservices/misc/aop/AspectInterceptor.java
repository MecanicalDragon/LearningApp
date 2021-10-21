package net.medrag.microservices.misc.aop;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@Aspect
@Service
public class AspectInterceptor {

    @SneakyThrows
    @Around("@annotation(aspectIntercepted)")
    public String intercept(ProceedingJoinPoint point, AspectIntercepted aspectIntercepted) {
        System.out.println(aspectIntercepted.printBefore());
        System.out.println(point.getSignature().getDeclaringTypeName());
        System.out.println(point.getSignature().getName());
        final String proceed = (String) point.proceed();
        System.out.println(aspectIntercepted.printAfter());
        return proceed;
    }
}
