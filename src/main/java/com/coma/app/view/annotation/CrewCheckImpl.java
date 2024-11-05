package com.coma.app.view.annotation;

import com.coma.app.biz.crew.CrewDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrewCheckImpl {

    private static final String CREW_CHECK = "CREW_CHECK";

    public String checkCrew(HttpServletRequest request, HttpSession session){
        String[] loginInfo = getLoginInformation(session);

        CrewDTO crewDTO = new CrewDTO();
        crewDTO.setCrew_num(Integer.parseInt(loginInfo[1]));
        if(loginInfo[1] == null || crewDTO.getCrew_num()<=0){ // 가입한 크루가 없으면
            log.error("가입한 크루 없음");
            log.error("loginInfo[1] {}, loginInfo[2] {}", loginInfo[1], loginInfo[2]);
            request.setAttribute("title", "페이지 접근 실패: 가입한 크루가 없습니다.");
            request.setAttribute("msg", "크루 가입 페이지로 이동합니다.");
            request.setAttribute("path", "crewList.do");
            return "views/info";
        }

        return "views/info";
    }
    private String[] getLoginInformation(HttpSession session) {
        String[] loginInfo = new String[3];
        fillLoginInfoFromSession(session, loginInfo);
        return loginInfo;
    }

    private void fillLoginInfoFromSession(HttpSession session, String[] loginInfo) {
        loginInfo[2] = (String) session.getAttribute(CREW_CHECK);
    }
}

