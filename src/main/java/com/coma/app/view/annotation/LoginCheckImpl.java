
package com.coma.app.view.annotation;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCheckImpl {



    private static final String MEMBER_ID = "MEMBER_ID"; // 회원 ID를 나타내는 상수
    private static final String CREW_CHECK = "CREW_CHECK"; // 크루 체크를 나타내는 상수
    private static final String ROLE_CHECK = "ROLE_CHECK"; // 회원인지 관리자인지를 나타내는 상수

    // 현재 요청과 응답, 세션 객체를 이용하여 로그인 정보를 검사하는 메서드
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        MemberDTO memberDTO = new MemberDTO();
        String[] loginInfo = getLoginInformation(request, session); // 로그인 정보를 가져옴
        synchronizeLoginInformation(loginInfo, session); // 세션과 쿠키 간의 로그인 정보를 동기화

        if (loginInfo[0] == null) { // 로그인 정보가 없으면
            request.setAttribute("title", "페이지 접근 실패: 권한이 없습니다.");
            request.setAttribute("msg", "로그인 페이지로 이동합니다.");
            request.setAttribute("path", "login.do");
            return "views/info";
        }
//        else if(loginInfo[1] == null){ // 가입한 크루가 없으면
        else if(loginInfo[1] == null || memberDTO.getMember_crew_join_date()==null && !loginInfo[2].equals("T")){ // 가입한 크루가 없으면
            request.setAttribute("title", "페이지 접근 실패: 가입한 크루가 없습니다.");
            request.setAttribute("msg", "크루 가입 페이지로 이동합니다.");
            request.setAttribute("path", "crewList.do");
            return "views/info";
        }
        if(!loginInfo[2].equals("T")){
            request.setAttribute("title", "페이지 접근 실패: 권한이 없습니다.");
            request.setAttribute("msg", "메인 페이지로 이동합니다.");
            request.setAttribute("path", "main.do");
            return "views/info";

        }
//        else if(loginInfo[2] == null || !loginInfo[2].equals("T")){
//            request.setAttribute("title", "페이지 접근 실패: 권한이 없습니다.");
//            request.setAttribute("msg", "메인 페이지로 이동합니다.");
//            request.setAttribute("path", "main.do");
//            return "views/info";
//
//        }


        return null; // 로그인 정보가 있으면 null 반환
    }


    // 요청과 세션 객체에서 로그인 정보를 가져오는 메서드
    private String[] getLoginInformation(HttpServletRequest request, HttpSession session) {
        // 로그인 정보 배열 [MEMBER_ID, CREW_CHECK, ROLE_CHECK]
        String[] loginInfo = new String[3]; // 로그인 정보 배열 생성
        fillLoginInfoFromCookies(request, loginInfo); // 쿠키에서 로그인 정보를 가져와 배열에 저장
        fillLoginInfoFromSession(session, loginInfo); // 세션에서 로그인 정보를 가져와 배열에 저장
        return loginInfo; // 로그인 정보 배열 반환
    }

    // 쿠키에서 로그인 정보를 가져와 배열에 저장하는 메서드
    private void fillLoginInfoFromCookies(HttpServletRequest request, String[] loginInfo) {
        Cookie[] cookies = request.getCookies(); // 요청에서 쿠키 배열을 가져옴
        if (cookies != null) { // 쿠키가 null이 아니면
            for (Cookie cookie : cookies) { // 모든 쿠키를 순회
                if (MEMBER_ID.equals(cookie.getName())) { // 쿠키 이름이 MEMBER_ID인 경우
                    loginInfo[0] = cookie.getValue(); // 배열의 첫 번째 요소에 쿠키 값을 저장
                } else if (CREW_CHECK.equals(cookie.getName())) { // 쿠키 이름이 CREW_CHECK인 경우
                    loginInfo[1] = cookie.getValue(); // 배열의 두 번째 요소에 쿠키 값을 저장
                } else if (ROLE_CHECK.equals(cookie.getName())) { // 쿠키 이름이 ROLE_CHECK인 경우
                    loginInfo[2] = cookie.getValue(); // 배열의 세 번째 요소에 쿠키 값을 저장
                }
            }
        }
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
            loginInfo[2] = (String) session.getAttribute(ROLE_CHECK); // 세션에서 ROLE_CHECK를 가져와 저장
        }
    }

//    // 세션과 로그인 정보를 동기화하는 메서드
//    private void synchronizeLoginInformation(String[] loginInfo, HttpSession session) {
//        // 세션의 MEMBER_ID가 null이고 배열의 첫 번째 요소가 null이 아닌 경우
//        if (session.getAttribute(MEMBER_ID) == null && loginInfo[0] != null) {
//            session.setAttribute(MEMBER_ID, loginInfo[0]);
//        }
//
//        // 세션의 CREW_CHECK가 null이고 배열의 두 번째 요소가 null이 아닌 경우
//        if (session.getAttribute(CREW_CHECK) == null && loginInfo[1] != null) {
//            session.setAttribute(CREW_CHECK, Integer.parseInt(loginInfo[1]));
//        }
//
//        // 세션의 ROLE_CHECK가 null이고 배열의 세 번째 요소가 null이 아닌 경우
//        if (session.getAttribute(ROLE_CHECK) == null && loginInfo[2] != null) {
//            session.setAttribute(ROLE_CHECK, loginInfo[2]);
//        }
//
//    }
// 세션과 로그인 정보를 동기화하는 메서드
private void synchronizeLoginInformation(String[] loginInfo, HttpSession session) {
    if (session.getAttribute(MEMBER_ID) == null && loginInfo[0] != null) {
        session.setAttribute(MEMBER_ID, loginInfo[0]);
    }
    if (session.getAttribute(CREW_CHECK) == null && loginInfo[1] != null) {
        session.setAttribute(CREW_CHECK, Integer.parseInt(loginInfo[1]));
    }
    if (session.getAttribute(ROLE_CHECK) == null && loginInfo[2] != null) {
        session.setAttribute(ROLE_CHECK, loginInfo[2]);
    }
}



    // 로그아웃 시 세션과 쿠키를 무효화하는 메서드
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        invalidateSession(request); // 세션 무효화
        clearCookies(request, response); // 쿠키 무효화
    }

    // 세션을 무효화하는 메서드
    private static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 존재하면 반환하고, 그렇지 않으면 null 반환
        if (session != null) { // 세션이 null이 아니면
            session.invalidate(); // 세션 무효화
        }
    }

    // 쿠키를 무효화하는 메서드
    private static void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies(); // 요청에서 쿠키 배열을 가져옴
        if (cookies != null) { // 쿠키가 null이 아니면
            for (Cookie cookie : cookies) { // 모든 쿠키를 순회
                // 쿠키 이름이 MEMBER_ID 또는 CREW_CHECK 또는 ROLE_CHECK 인 경우
                if (MEMBER_ID.equals(cookie.getName()) || CREW_CHECK.equals(cookie.getName()) || ROLE_CHECK.equals(cookie.getName())) {
                    cookie.setMaxAge(0); // 쿠키를 무효화 (만료 시간 0 설정)
                    cookie.setPath("/"); // 전체 경로에 적용
                    response.addCookie(cookie); // 응답에 추가
                }
            }
        }
    }
}