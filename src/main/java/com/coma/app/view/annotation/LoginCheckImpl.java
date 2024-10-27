package com.coma.app.view.annotation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LoginCheckImpl {

    /**
     * 현재 요청과 응답에서 로그인 정보를 검사하는 메서드.
     * 로그인 정보가 없을 경우 RuntimeException을 발생시킴.
     */
    public void check() {
        // 현재 요청과 응답 객체를 가져옴
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        // 세션 객체를 가져옴
        HttpSession session = request.getSession();

        // 로그인 정보를 체크하고 결과를 받아옴
        String[] loginInfo = checkLoginInformation(request, response, session);

        // 로그인 정보가 없으면 예외를 발생시킴
        if (loginInfo[0] == null) {
            throw new RuntimeException("User is not logged in");
        }
    }


     //사용자가 로그아웃할 때 세션과 쿠키를 무효화하는 메서드.
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션이 있을 경우 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 쿠키를 제거
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("MEMBER_ID") || cookie.getName().equals("CREW_CHECK")) {
                    cookie.setMaxAge(0); // 쿠키를 무효화
                    cookie.setPath("/"); // 전체 경로에 적용
                    response.addCookie(cookie); // 응답에 추가
                }
            }
        }
    }

    /**
     * 현재 요청, 응답, 세션 객체에서 로그인 정보를 체크하고 가져오는 메서드.

     *  로그인 정보 배열 [MEMBER_ID, CREW_CHECK]
     */
    private String[] checkLoginInformation(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String[] loginInformation = new String[2]; // [MEMBER_ID, CREW_CHECK]

        // 쿠키에서 로그인 정보를 가져옴
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("MEMBER_ID")) {
                    cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 유효
                    response.addCookie(cookie); // 응답에 추가
                    loginInformation[0] = cookie.getValue(); // MEMBER_ID 저장
                } else if (cookie.getName().equals("CREW_CHECK")) {
                    cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 유효
                    response.addCookie(cookie); // 응답에 추가
                    loginInformation[1] = cookie.getValue(); // CREW_CHECK 저장
                }
            }
        }

        // 세션에서 로그인 정보를 가져옴
        String sessionMemberID = (String) session.getAttribute("MEMBER_ID");
        String sessionMemberCrew = (String) session.getAttribute("CREW_CHECK");

        // 세션 또는 쿠키에서 로그인 정보가 있을 경우, 두 정보를 동기화
        if (sessionMemberID == null && loginInformation[0] != null) {
            session.setAttribute("MEMBER_ID", loginInformation[0]); // 세션에 MEMBER_ID 저장
            if (sessionMemberCrew == null || sessionMemberCrew.equals("0")) {
                session.setAttribute("CREW_CHECK", loginInformation[1]); // 세션에 CREW_CHECK 저장
            }
        } else if (sessionMemberID != null && loginInformation[0] == null) {
            loginInformation[0] = sessionMemberID; // 세션에서 MEMBER_ID 가져옴
            if (sessionMemberCrew != null && sessionMemberCrew.equals("0")) {
                loginInformation[1] = "0"; // 세션에서 CREW_CHECK 가져옴
            }
        }

        return loginInformation; // 로그인 정보 반환
    }
}