package com.coma.app.view.member;

import com.coma.app.view.annotation.LoginCheckImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Slf4j
@Aspect
@Component
public class LoginAspect {

    @Autowired
    private LoginCheckImpl loginCheckImpl;


    // @Around 어드바이스는 @LoginCheck 어노테이션이 붙은 메서드를 가로채서 로그인 체크를 수행
    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around Advice Start");
        // 사용자의 요청 정보를 검사 & 처리를 위해 필요해서 가져옴
        //현재 세션을 가져온 후, 세션에서 사용자 아이디 또는 인증 토큰을 확인
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 로그인하지 않은 사용자 == 인증 정보가 없는 사용자
        // 를 로그인페이지로 보내기 위해 가져옴
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        // 로그인 여부를 확인하기 위해 가져옴(로그인 O == 세션 조회 O)
        HttpSession session = request.getSession();

            // 로그인 체크 로직 실행
            String result = loginCheckImpl.checkLogin(request, response, session);

            if (result != null) {
                log.info("result != null");
                return result;
            }


        // 로그인된 경우 원래 메서드를 실행
        log.info("@around Advice End");
        return pjp.proceed();
    }
}