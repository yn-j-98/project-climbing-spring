package com.coma.app.biz.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;


@Service
@Aspect
@Slf4j
public class LogAdvice {

    @Before("PointcutCommon.beforePointcut()") // 전 실행
    public void printLogBefore(JoinPoint jp) {
        String methodName = jp.getSignature().getName(); // 메서드명
        log.info("공통 관심 - 로그 : [{}] 비즈니스 메서드 수행 전 호출",methodName);
    }
    @AfterReturning("PointcutCommon.afterReturningPointcut()") // 성공 실행
    public void printLogAfterReturning(JoinPoint jp) {
        String methodName = jp.getSignature().getName(); // 메서드명
        log.info("공통 관심 - 로그 : [{}] 비즈니스 메서드 수행 성공",methodName);
    }
    @AfterThrowing("PointcutCommon.afterThrowingPointcut()") // 오류 시 실행
    public void printLogAfterThrowing(JoinPoint jp) {
        String methodName = jp.getSignature().getName(); // 메서드명
        log.info("공통 관심 - 로그 : [{}] 비즈니스 메서드 수행 실패",methodName);
    }
}
