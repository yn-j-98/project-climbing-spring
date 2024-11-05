package com.coma.app.view.aspect;

import com.coma.app.view.annotation.AdminCheckImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.checkerframework.checker.units.qual.A;
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
public class AdminAspect {

    @Autowired
    private AdminCheckImpl adminCheckImpl;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;


    // @Around 어드바이스는 @Admin 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.AdminCheck)")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around Admin Advice 시작");

        // 사용자의 요청 정보를 검사 & 처리를 위해 필요해서 가져옴
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();

        // 관리자 권한 체크 로직 실행
        String result = adminCheckImpl.checkAdmin(request, session);

        if (result != null) {
            log.info("result != null");
            return result;
        }

        // 권한이 있는 경우 원래 메서드를 실행
        log.info("@around Admin Advice 종료");
        return pjp.proceed();
    }
}