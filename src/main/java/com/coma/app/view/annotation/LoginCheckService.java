
package com.coma.app.view.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCheckService {


    private static final String MEMBER_ID = "MEMBER_ID"; // 회원 ID를 나타내는 상수
    private static final String CREW_CHECK = "CREW_CHECK"; // 크루 체크를 나타내는 상수
    private static final String MEMBER_ROLE = "MEMBER_ROLE"; // 회원인지 관리자인지를 나타내는 상수

    // 현재 요청과 응답, 세션 객체를 이용하여 로그인 정보를 검사하는 메서드
    public String checkLogin(HttpServletRequest request, HttpSession session) {

        // 세션에서 로그인 정보를 가져옴
        String[] loginInfo = getLoginInformation(session);
        // 세션의 로그인 정보 동기화
        synchronizeLoginInfo(loginInfo, session);
        log.info("로그인 정보: ID={}, 가입된 크루 번호 ={}, ROLE={}", loginInfo[0], loginInfo[1], loginInfo[2]);

        // 로그인 정보가 없으면
        if (loginInfo[0] == null) {
            log.error("로그인 정보 없음");
            request.setAttribute("title", "로그인 후 이용할 수 있는 페이지입니다.");
            request.setAttribute("msg", "로그인 페이지로 이동합니다.");
            request.setAttribute("path", "login.do");
            return "views/info";
        }
        return null; // 로그인 정보가 있으면 null 반환
    }

    // 세션 객체에서 로그인 정보를 가져오는 메서드
    private String[] getLoginInformation(HttpSession session) {
        // 로그인 정보 배열 [MEMBER_ID, CREW_CHECK, MEMBER_ROLE]
        // 로그인 정보를 저장할 배열 생성
        String[] loginInfo = new String[3];
        // 세션에서 정보를 가져와 배열에 저장
        infoFromSession(session, loginInfo);
        return loginInfo; // 로그인 정보 배열 반환
    }

    // 세션 배열 값 저장 [0] - MEMBER_ID, [1] - CREW_CHECK, [2] - ROLE_CHECK
    private void infoFromSession(HttpSession session, String[] loginInfo) {
        // 로그인한 ID 정보
        if (loginInfo[0] == null) {
            // 세션에서 가져오기
            loginInfo[0] = (String) session.getAttribute(MEMBER_ID);
        }
        // 가입한 크루 정보 (없으면 0)
        if (loginInfo[1] == null) {
            Integer crewCheck = (Integer) session.getAttribute(CREW_CHECK);
            if (crewCheck != null) {
                loginInfo[1] = crewCheck.toString(); // Integer 값을 String으로 변환하여 저장
            }
        }
        // 관리자(T)인지 회원(F)인지 구분
        if (loginInfo[2] == null) {
            loginInfo[2] = (String) session.getAttribute(MEMBER_ROLE);
        }
    }

    // 세션과 로그인 정보를 동기화하는 메서드
    private void synchronizeLoginInfo(String[] loginInfo, HttpSession session) {
        log.info("세션과 로그인 정보 동기화");
        if (session.getAttribute(MEMBER_ID) == null && loginInfo[0] != null) {
            log.info("MEMBER_ID를 세션에 저장");
            session.setAttribute(MEMBER_ID, loginInfo[0]);
        }
        if (session.getAttribute(CREW_CHECK) == null && loginInfo[1] != null) {
            log.info("CREW_CHECK를 세션에 저장");
            session.setAttribute(CREW_CHECK, Integer.parseInt(loginInfo[1]));
        }
        if (session.getAttribute(MEMBER_ROLE) == null && loginInfo[2] != null) {
            log.info("MEMBER_ROLE을 세션에 저장");
            session.setAttribute(MEMBER_ROLE, loginInfo[2]);
        }
    }

    // 로그아웃 시 세션을 무효화하는 메서드
    public static void logout(HttpServletRequest request) {
        invalidateSession(request); // 세션 무효화
    }

    // 세션을 무효화하는 메서드
    private static void invalidateSession(HttpServletRequest request) {
        // 세션이 존재하면 반환하고, 그렇지 않으면 null 반환
        HttpSession session = request.getSession(false);
        if (session != null) { // 세션이 null이 아니면
            log.info("세션 무효화 완료");
            session.invalidate(); // 세션 무효화
        }
    }
}