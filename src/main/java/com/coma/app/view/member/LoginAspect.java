package com.coma.app.view.member;

import com.coma.app.view.annotation.LoginCheckImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class LoginAspect {

    private LoginCheckImpl loginCheckImpl;

    @Autowired
    public LoginAspect(LoginCheckImpl loginCheckImpl) {
        this.loginCheckImpl = loginCheckImpl;
    }


    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        // 현재 요청과 응답 객체를 가져옴
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        // 세션 객체를 가져옴
        HttpSession session = request.getSession();

        // 로그인 체크 로직 실행
        loginCheckImpl.checkLogin(request, response, session);

        // 로그인된 경우 원래 메서드를 실행
        return joinPoint.proceed();
    }
}