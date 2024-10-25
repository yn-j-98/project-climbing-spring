package com.coma.app.view.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCheck {
   public static String[] Success(HttpServletRequest request, HttpServletResponse response) {
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
      
      //cookie 에 저장된 로그인 정보를 불러오기
      Cookie[] cookies = request.getCookies();
      //로그인 정보 저장용
      String[] login_information = new String[2];
//      String cookieMemberID = null;
//      int cookieMemberCrew = 0;
      //cookies 가 null이 아니라면
      if(cookies != null) {
         //cookies 만큼 배열을 돌리고
         for (Cookie cookie : cookies) {
            //cookie 에 저장된 회원 아이디 정보가 있다면
            if(cookie.getName().equals("MEMBER_ID")) {//쿠키에 저장된 사용자 ID를 불러옵니다.
               //쿠키가 있으면 자동로그인 기간을 추가
               cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 유지
               //쿠키를 추가헤줍니다.
               response.addCookie(cookie);
               //회원 ID를 불러와 login_information 배열에 저장해줍니다.
               login_information[0] = cookie.getValue();
            }
            else if(cookie.getName().equals("CREW_CHECK")) {//쿠키에 저장된 사용자 CREW를 불러옵니다.
               cookie.setMaxAge(60 * 60 * 24 * 7);
               //쿠키를 추가헤줍니다.
               response.addCookie(cookie);
               //회원 CREW를 불러와 login_information 배열에 저장해줍니다.
               login_information[1] = cookie.getValue();
            }
         }
      }

      //session 에 저장된 로그인 정보를 불러오기
      HttpSession session = request.getSession();
      String sessionMemberID = (String)session.getAttribute("MEMBER_ID");   
      String sessionMemberCrew=session.getAttribute("CREW_CHECK")+"";
      
      

      //sessionID 가 null 이고 cookieID 가 null 아니면
      if(sessionMemberID == null && login_information[0] != null) {
         //session 을 갱신하고
         session.setAttribute("MEMBER_ID", login_information[0]);
         //cookieID를 반환해 줍니다.
         System.err.println("(LoginMemberID.java) Session ID 로그 : " + login_information[0]);
         System.err.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + login_information[1]);
         System.out.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + sessionMemberCrew );
         //sessionMemberCrew 0이 이면
         if(sessionMemberCrew.equals("0") || sessionMemberCrew.equals("null")) {
            //session 을 갱신하고
            session.setAttribute("CREW_CHECK", login_information[1]);
            //login_information[1]를 반환해 줍니다.
            System.err.println("(LoginMemberID.java) if 내부 Session Crew 로그 : " + login_information[1]);
         }
         if(Integer.parseInt(sessionMemberCrew) >= 0) {
        	 login_information[1] = sessionMemberCrew;
         }
      }
      //만약 sessionID 가 null 아니고 cookieID 가 null 이면
      else if(sessionMemberID != null && login_information[0] == null){
         login_information[0] = sessionMemberID;
         //cookieID를 반환해 줍니다.
         System.err.println("(LoginMemberID.java) Session ID 로그 : " + login_information[0]);
         System.err.println("(LoginMemberID.java) if 외부 Session Crew 로그 : " + login_information[1]);

         //sessionMemberCrew 0 이 아니거나 sessionMemberCrew null이 아니면
         if(sessionMemberCrew.equals("0")) {
            login_information[1] = "0";
            //login_information[1]를 반환해 줍니다.
            System.err.println("(LoginMemberID.java) if 내부 Session Crew 로그 : " + login_information[1]);
         }
         
         if(Integer.parseInt(sessionMemberCrew) >= 0) {
        	 login_information[1] = sessionMemberCrew;
         }
      }

      System.err.println("(LoginMemberID.java) 최종 로그인 정보 : " + login_information[0]);
      System.err.println("(LoginMemberID.java) 최종 로그인 정보 : " + login_information[1]);
      return login_information;
   }

}
