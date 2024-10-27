package com.coma.app.view.member;

import com.coma.app.view.annotation.LoginCheckImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    @Autowired
    private LoginCheckImpl loginCheckImpl;

    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)") // @LoginCheck 어노테이션이 붙은 메서드에 적용
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        loginCheckImpl.check(); // 로그인 체크 로직 실행

        // 로그인된 경우 원래 메서드를 실행
        return joinPoint.proceed();
    }
}