package de.atruvia.webapp.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {



    //@Before(value ="Pointcuts.restControllerMethods()")

    @Before(value ="Pointcuts.dozentMethods()")
    public void logAdvice(JoinPoint joinPoint){

        log.warn("####### Log advice #######");
        log.warn(joinPoint.getSignature().getName() + " wurde gerufen");
    }


    @AfterReturning(value ="execution(public * de.atruvia.webapp.presentation.controller.PersonenQueryController.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println(result);
    }

    @AfterThrowing(value ="execution(public * de.atruvia.webapp.presentation.controller.PersonenQueryController.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("-----------------------");
       log.error("{}",ex);
    }


    @Around(value ="Pointcuts.personenControllerMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        return joinPoint.proceed();

    }
}
