package com.coma.app.view.aspect;

import com.coma.app.view.annotation.CrewCheckImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@Order(2) // @커스텀 여러개 쓸 경우 진행 순서
public class CrewAspect {

    @Autowired
    private CrewCheckImpl crewCheckImpl;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @Around("@annotation(com.coma.app.view.annotation.CrewCheck)")
    public Object checkCrew(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@around Crew Advice 시작");

        // 사용자의 요청 정보를 검사 & 처리를 위해 필요해서 가져옴
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
        String result = crewCheckImpl.checkCrew(request,session);

        if (result != null) {
            log.info("result != null");
            log.info("result data : [{}]",result);
            return result;
        }
        log.info("@around crew Advice 종료");
        return pjp.proceed();
    }
}
