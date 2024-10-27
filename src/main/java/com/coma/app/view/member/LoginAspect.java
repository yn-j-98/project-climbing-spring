package com.coma.app.view.member;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class LoginAspect {



    @Around("@annotation(com.coma.app.view.annotation.LoginCheck)") // @LoginCheck 어노테이션이 붙은 메서드에 적용
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
    	// 현재 실행 중인 메서드가 어떤 HTTP 요청을 받고 있는지 알기 위해 사용
    	// 요청 정보에는 클라이언트가 보낸 데이터, 요청 URL, 쿠키 등이 포함됨
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 현재 요청에 대한 응답을 클라이언트에게 어떻게 돌려줄지 설정할 때 사용
        // 예를 들어, 쿠키 추가, 리다이렉트 처리 등이 가능
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        HttpSession session = request.getSession();

        // 로그인 정보 확인
        String[] loginInfo = checkLoginInformation(request, response, session);

        if (loginInfo[0] == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:login.do"; // login.do로 리다이렉트
        }

        // 로그인된 경우 원래 메서드를 실행
        return joinPoint.proceed();
    }

    private String[] checkLoginInformation(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    	/*
        getCookies()   HTTP 요청 메시지의 헤더에 포함된 쿠키를 javax.servlet.http.Cookie 배열(Cookie[])로 리턴.
        getServerName()   서버의 도메인명을 문자열로 리턴한다.
        getReqeustIRL()   요청 URL을 StringBuffer로 리턴한다.
        getName()   쿠키의 이름을 가져온다. (String으로 리턴)
        getPath()    쿠키의 유효한 디렉토리 정보를 가져온다. (String으로 리턴)
        getValue()    쿠키에 설정된 값을 가져온다. (String으로 리턴)
        setMaxAge(int)   쿠키의 유효한 기간을 설정한다. 
        setValue(String value)   쿠키 값을 설정한다.
        getMaxAge()   쿠키 만료 기간을 얻어온다.
         */
    	
    	//로그인 정보 저장용
    	String[] loginInformation = new String[2]; // [MEMBER_ID, CREW_CHECK]

        // 쿠키에서 로그인 정보 가져오기
        Cookie[] cookies = request.getCookies();
      //cookies 가 null이 아니라면
        if (cookies != null) {
        	//cookies 만큼 배열을 돌리고
            for (Cookie cookie : cookies) {
            	//cookie 에 저장된 회원 아이디 정보가 있다면
                if (cookie.getName().equals("MEMBER_ID")) {//쿠키에 저장된 사용자 ID를 불러옵니다.
                	//쿠키가 있으면 자동로그인 기간을 추가
                    cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 유지
                    //쿠키를 추가해줍니다.
                    response.addCookie(cookie);
                    //회원 ID를 불러와 login_information 배열에 저장해줍니다.
                    loginInformation[0] = cookie.getValue(); // MEMBER_ID
                } else if (cookie.getName().equals("CREW_CHECK")) {//쿠키에 저장된 사용자 CREW를 불러옵니다.
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                    //쿠키를 추가해줍니다.
                    response.addCookie(cookie);
                    //회원 CREW를 불러와 login_information 배열에 저장해줍니다.
                    loginInformation[1] = cookie.getValue(); // CREW_CHECK
                }
            }
        }

        // 세션에서 로그인 정보 가져오기
        String sessionMemberID = (String) session.getAttribute("MEMBER_ID");
        String sessionMemberCrew = (String) session.getAttribute("CREW_CHECK");

        //sessionID 가 null 이고 cookieID 가 null 아니면
        if (sessionMemberID == null && loginInformation[0] != null) {
        	//session 을 갱신하고
            session.setAttribute("MEMBER_ID", loginInformation[0]);
            //cookieID를 반환해 줍니다.
            System.err.println("(LoginMemberID.java) Session ID 로그 : " + loginInformation[0]);
            System.err.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + loginInformation[1]);
            System.out.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + sessionMemberCrew );
            //sessionMemberCrew 0이 이면
            if (sessionMemberCrew == null || sessionMemberCrew.equals("0")) {
            	//session 을 갱신하고
                session.setAttribute("CREW_CHECK", loginInformation[1]);
                //login_information[1]를 반환해 줍니다.
                System.err.println("(LoginMemberID.java) if 내부 Session Crew 로그 : " + loginInformation[1]);
            }
            if(Integer.parseInt(sessionMemberCrew) >= 0) {
           	 loginInformation[1] = sessionMemberCrew;
            }
        } 
        //만약 sessionID 가 null 아니고 cookieID 가 null 이면
        else if (sessionMemberID != null && loginInformation[0] == null) {
            loginInformation[0] = sessionMemberID;
            //cookieID를 반환해 줍니다.
            System.err.println("(LoginMemberID.java) Session ID 로그 : " + loginInformation[0]);
            System.err.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + loginInformation[1]);
            //sessionMemberCrew 0 이 아니거나 sessionMemberCrew null이 아니면
            if(sessionMemberCrew.equals("0")) {
               loginInformation[1] = "0";
               //login_information[1]를 반환해 줍니다.
               System.err.println("(LoginMemberID.java) if 내부 Session Crew 로그 : " + loginInformation[1]);
            }
            
            if(Integer.parseInt(sessionMemberCrew) >= 0) {
           	 loginInformation[1] = sessionMemberCrew;
            }
        }
        System.err.println("(LoginMemberID.java) 최종 로그인 정보 : " + loginInformation[0]);
        System.err.println("(LoginMemberID.java) 최종 로그인 정보 : " + loginInformation[1]);
        return loginInformation;
    }
 // 로그아웃 처리 메서드
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("MEMBER_ID") || cookie.getName().equals("CREW_CHECK")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie); // 쿠키 삭제
                }
            }
        }
        // 세션에서 정보 삭제
        request.getSession().removeAttribute("MEMBER_ID");;
        request.getSession().removeAttribute("CREW_CHECK");
    }

}
