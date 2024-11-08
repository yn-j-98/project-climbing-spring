package com.coma.app.view.aspect;

import com.coma.app.view.annotation.LoginCheckService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
@Order(1) // @커스텀 여러개 쓸 경우 진행 순서
public class LoginAspect {

    @Autowired
    private LoginCheckService loginCheckService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    // @Around 어드바이스는 @LoginCheck 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around login Advice 시작");

        // 로그인 체크 서비스 호출
        String result = loginCheckService.checkLogin(request, session);
        // 로그인 체크 실패 시 반환할 View 경로가 있는 경우
        if (result != null) {
            log.info("로그인 체크 실패: result != null, result: {}", result);
            return result;
        }
        // 로그인된 경우 기존(@LoginCheck 붙은) 메서드 실행
        log.info("@around login Advice 종료");
        return pjp.proceed();
    }
}