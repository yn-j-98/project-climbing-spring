package com.coma.app.view.annotation;

import com.coma.app.biz.crew.CrewDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrewCheckService {

    private static final String CREW_CHECK = "CREW_CHECK";

    // 가입한 크루가 있는지 확인하는 메서드
    // 없으면 크루 가입페이지로 이동됨
    public String checkCrew(HttpServletRequest request, HttpSession session){
        // 세션에서 로그인 정보를 가져옴
        String[] loginInfo = getLoginInfo(session);

        CrewDTO crewDTO = new CrewDTO();
        // 크루 정보가 없으면 "0"으로 설정
        loginInfo[1] = loginInfo[1] == null ? "0" : loginInfo[1];
        crewDTO.setCrew_num(Integer.parseInt(loginInfo[1]));

        // 가입한 크루가 없으면
        if(crewDTO.getCrew_num() <= 0){
            log.error("가입한 크루 없음");
            log.error("현재 로그인한 회원의 아이디 :  {}, 가입한 크루 번호 {}", loginInfo[1], loginInfo[2]);
            request.setAttribute("title", "가입한 크루가 있는 회원만 접근 가능한 페이지입니다!");
            request.setAttribute("msg", "크루 가입 페이지로 이동합니다.");
            request.setAttribute("path", "crewList.do");
            return "views/info";
        }

        return null;
    }

    // 세션 객체에서 로그인 정보를 가져오는 메서드
    private String[] getLoginInfo(HttpSession session) {
        // 로그인 정보를 저장할 배열 생성
        String[] loginInfo = new String[3];
        loginInfoFromSession(session, loginInfo);
        return loginInfo;
    }

    // 세션 객체에서 로그인 정보를 배열에 채우는 메서드
    private void loginInfoFromSession(HttpSession session, String[] loginInfo) {
        // 세션에서 CREW_CHECK 정보 가져와 배열에 채움
        loginInfo[1] = session.getAttribute(CREW_CHECK)+"";
    }
}

