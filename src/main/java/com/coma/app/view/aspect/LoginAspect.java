package com.coma.app.view.aspect;

import com.coma.app.view.annotation.LoginCheckImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Slf4j
@Aspect
@Component
@Order(1) // @커스텀 여러개 쓸 경우 진행 순서
public class LoginAspect {

    @Autowired
    private LoginCheckImpl loginCheckImpl;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    // @Around 어드바이스는 @LoginCheck 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around login Advice 시작");

        String result= loginCheckImpl.checkLogin(request,session);
            if (result != null) {
                log.info("result != null");
                return result;
            }
        // 로그인된 경우 원래 메서드를 실행
        log.info("@around login Advice 종료");
        return pjp.proceed();
    }
}