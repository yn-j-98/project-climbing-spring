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

    // @Around 어드바이스는 @LoginCheck 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        // 현재 요청과 응답 객체를 가져옴
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 세션에 저장된 사용자 아이디를 가져오기 위해 가져옴
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        // 로그인하지 않은 사용자를 로그인 페이지로 보내기 위해 가져옴
        HttpSession session = request.getSession();



        // 로그인 체크 로직 실행
        loginCheckImpl.checkLogin(request, response, session);

        // 로그인된 경우 원래 메서드를 실행
        return pjp.proceed();
    }
}