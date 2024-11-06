package com.coma.app.view.aspect;

import com.coma.app.view.annotation.AdminCheckService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
public class AdminAspect {

    @Autowired
    private AdminCheckService adminCheckService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;


    // @Around 어드바이스는 @Admin 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.AdminCheck)")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around Admin Advice 시작");

        // 관리자 권한 체크 로직 실행
        String result = adminCheckService.checkAdmin(request, session);

        if (result != null) {
            log.info("result != null");
            return result;
        }

        // 권한이 있는 경우 원래 메서드를 실행
        log.info("@around Admin Advice 종료");
        return pjp.proceed();
    }
}