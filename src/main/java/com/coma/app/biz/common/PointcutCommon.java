package com.coma.app.biz.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutCommon {

    @Pointcut("execution(* com.coma.app.biz..*DAO.*(..))")
    public void beforePointcut() {} // 참조 메서드, 실질적인 기능이 있지않다

    @Pointcut("execution(* com.coma.app.biz..*DAO.*(..))")
    public void afterReturningPointcut() {}

    @Pointcut("execution(* com.coma.app.biz..*DAO.*(..))")
    public void afterThrowingPointcut() {}


}
