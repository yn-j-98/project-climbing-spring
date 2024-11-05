
package com.coma.app.view.annotation;

import com.coma.app.biz.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCheckImpl {

    @Autowired
    private MemberService memberService;

    private static final String MEMBER_ID = "MEMBER_ID"; // 회원 ID를 나타내는 상수
    private static final String CREW_CHECK = "CREW_CHECK"; // 크루 체크를 나타내는 상수
    private static final String MEMBER_ROLE = "MEMBER_ROLE"; // 회원인지 관리자인지를 나타내는 상수

    // 현재 요청과 응답, 세션 객체를 이용하여 로그인 정보를 검사하는 메서드
    public String checkLogin(HttpServletRequest request, HttpSession session) {

        String[] loginInfo = getLoginInformation(session); // 로그인 정보를 가져옴
        synchronizeLoginInformation(loginInfo, session); // 세션의 로그인 정보를 동기화
        log.info("loginInfo[0] {}, [1] {}, [2] {}", loginInfo[0], loginInfo[1], loginInfo[2]);

        if (loginInfo[0] == null) { // 로그인 정보가 없으면
            log.error("로그인 정보 없음");
            request.setAttribute("title", "페이지 접근 실패: 권한이 없습니다.");
            request.setAttribute("msg", "로그인 페이지로 이동합니다.");
            request.setAttribute("path", "login.do");
            return "views/info";
        }
        return null; // 로그인 정보가 있으면 null 반환
    }

    // 요청과 세션 객체에서 로그인 정보를 가져오는 메서드
    private String[] getLoginInformation(HttpSession session) {
        // 로그인 정보 배열 [MEMBER_ID, CREW_CHECK, ROLE_CHECK]
        String[] loginInfo = new String[3]; // 로그인 정보 배열 생성
        fillLoginInfoFromSession(session, loginInfo); // 세션에서 로그인 정보를 가져와 배열에 저장
        return loginInfo; // 로그인 정보 배열 반환
    }

    // 세션 배열 값 저장 [0] - MEMBER_ID, [1] - CREW_CHECK, [2] - ROLE_CHECK
    private void fillLoginInfoFromSession(HttpSession session, String[] loginInfo) {
        if (loginInfo[0] == null) { // 배열의 첫 번째 요소가 null인 경우
            loginInfo[0] = (String) session.getAttribute(MEMBER_ID); // 세션에서 MEMBER_ID를 가져와 저장
        }
        if (loginInfo[1] == null) { // 배열의 두 번째 요소가 null인 경우
            Integer crewCheck = (Integer) session.getAttribute(CREW_CHECK); // 세션에서 CREW_CHECK를 가져와 저장
            if (crewCheck != null) {
                loginInfo[1] = crewCheck.toString(); // Integer 값을 String으로 변환하여 저장
            }
        }
        if (loginInfo[2] == null) { // 배열의 세 번째 요소가 null인 경우
            loginInfo[2] = (String) session.getAttribute(MEMBER_ROLE); // 세션에서 ROLE_CHECK를 가져와 저장
        }
    }

    // 세션과 로그인 정보를 동기화하는 메서드
    private void synchronizeLoginInformation(String[] loginInfo, HttpSession session) {
        log.info("세션과 로그인 정보 동기화");
        if (session.getAttribute(MEMBER_ID) == null && loginInfo[0] != null) {
            log.info("session.getAttribute(MEMBER_ID) == null && loginInfo[0] != null");
            session.setAttribute(MEMBER_ID, loginInfo[0]);
        }
        if (session.getAttribute(CREW_CHECK) == null && loginInfo[1] != null) {
            log.info("session.getAttribute(CREW_CHECK) == null && loginInfo[1] != null");
            session.setAttribute(CREW_CHECK, Integer.parseInt(loginInfo[1]));
        }
        if (session.getAttribute(MEMBER_ROLE) == null && loginInfo[2] != null) {
            log.info("session.getAttribute(MEMBER_ROLE) == null && loginInfo[2] != null");
            session.setAttribute(MEMBER_ROLE, loginInfo[2]);
        }
    }

    // 로그아웃 시 세션을 무효화하는 메서드
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        invalidateSession(request); // 세션 무효화
    }

    // 세션을 무효화하는 메서드
    private static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 존재하면 반환하고, 그렇지 않으면 null 반환
        if (session != null) { // 세션이 null이 아니면
            log.info("세션 무효화 완료");
            session.invalidate(); // 세션 무효화
        }
    }
}