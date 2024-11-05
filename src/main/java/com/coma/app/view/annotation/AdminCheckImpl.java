package com.coma.app.view.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminCheckImpl {

    private static final String MEMBER_ROLE = "MEMBER_ROLE"; // 회원인지 관리자인지를 나타내는 상수

    // 현재 요청과 응답, 세션 객체를 이용하여 관리자 권한을 검사하는 메서드
    public String checkAdmin(HttpServletRequest request, HttpSession session) {
        String[] loginInfo = getLoginInformation(session); // 로그인 정보를 가져옴

        if (!"T".equals(loginInfo[2])) { // 관리자 권한이 아닌 경우
            log.error("관리자 아님");
            request.setAttribute("title", "페이지 접근 실패: 권한이 없습니다.");
            request.setAttribute("msg", "메인 페이지로 이동합니다.");
            request.setAttribute("path", "main.do");
            return "views/info";
        }

        return null; // 권한이 있는 경우 null 반환
    }

    // 요청과 세션 객체에서 로그인 정보를 가져오는 메서드
    private String[] getLoginInformation(HttpSession session) {
        String[] loginInfo = new String[3]; // 로그인 정보 배열 생성
        fillLoginInfoFromSession(session, loginInfo); // 세션에서 로그인 정보를 가져와 배열에 저장
        return loginInfo; // 로그인 정보 배열 반환
    }

    // 세션에서 로그인 정보를 가져와 배열에 저장하는 메서드
    private void fillLoginInfoFromSession(HttpSession session, String[] loginInfo) {
        loginInfo[2] = (String) session.getAttribute(MEMBER_ROLE); // 세션에서 MEMBER_ROLE을 가져옴
    }
}
