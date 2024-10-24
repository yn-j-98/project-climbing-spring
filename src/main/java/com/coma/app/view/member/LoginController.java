package com.coma.app.view.member;

import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;


@Controller
public class LoginController{

	@Autowired
	private MemberService memberService;


	@GetMapping("/LOGINPAGEACTION.do")
	public String login() {
		return "login";
	}
	
	@PostMapping("/LOGINACTION.do")
	public String login(Model model, HttpSession session, MemberDTO memberDTO, HttpServletResponse response) {

		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "redirect:MAINPAGEACTION.do";

//		//로그인 정보가 있는지 확인해주고
//		String login[] = LoginCheck.Success(request, response);
//		//사용자 아이디
//		String member_id = login[0];

			//사용자의 아이디와 비밀번호를 model 로 전달하여 확인하고
		//memberDTO.setModel_member_condition("MEMBER_SEARCH_ID_PASSWORD");
		System.out.println("	[로그] com.coma.app.view.member.LoginController.login() selectOne(): " + memberService.selectOneSearchIdPassword(memberDTO));
		memberDTO = memberService.selectOneSearchIdPassword(memberDTO);
		System.out.println("	[memberDTO] : " + memberDTO);
			//만약 data 가 null 을 반환하면
			if(memberDTO == null) {
				System.err.println("(LoginAction.java) data null 로그");
				//로그인을 실패한 것 이기 때문에 login.jsp 페이지로 반환합니다.
				path = "LOGINPAGEACTION.do";
				//로그인 여부를 전달하여 사용자가 로그인 여부를 확인할 수 있도록 만들어 줍니다.
			}
			//null 이 아니라면
			else {
				System.err.println("(LoginAction.java) data.getMember_id 로그 : " + memberDTO.getMember_id());
				System.err.println("(LoginAction.java) data.getModel_member_crew_num 로그 : " + memberDTO.getMember_crew_num());
				//사용자 ID Session에 저장
				session.setAttribute("MEMBER_ID", memberDTO.getMember_id());
				//크루가 없다면 0
				session.setAttribute("CREW_CHECK", memberDTO.getMember_crew_num());

				//자동로그인을 등록했다면 cookie 에 로그인 정보를 저장해줍니다.
				if(memberDTO.getVIEW_AUTO_LOGIN() != null){
					Cookie member_id_cookie = new Cookie("MEMBER_ID", memberDTO.getMember_id());
					//Cookie 값은 haxCode로 저장되기 때문에 문자열로 변환하여 저장해야한다.
					//크루가 없으면 0을 반환한다.
					Cookie member_crew_cookie = new Cookie("CREW_CHECK", memberDTO.getMember_crew_num()+"");

					System.out.println("(LoginAction.java) 사용자 크루 쿠키 값 저장 로그 : " + member_crew_cookie.getValue());
					System.out.println("(LoginAction.java) 사용자 크루 쿠키 명 저장 로그 : " + member_crew_cookie.getName());

					// 쿠키 유효 시간 설정 (7일)
					member_id_cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 동안 유지
					member_crew_cookie.setMaxAge(60 * 60 * 24 * 7);
					
					//쿠키를 추가헤줍니다.
					response.addCookie(member_id_cookie);
					response.addCookie(member_crew_cookie);
					System.out.println("(LoginAction.java) 사용자 아이디 쿠키 값 저장 로그 : " + member_id_cookie.getValue());
					System.out.println("(LoginAction.java) 사용자 아이디 쿠키 명 저장 로그 : " + member_id_cookie.getName());
				}
				//로그인 여부를 전달하여 사용자가 로그인 여부를 확인할 수 있도록 만들어 줍니다.
				//request.setAttribute("LOGIN_CHECK", true);
			}
		return  path;
	}
	
	public String logout(Cookie[] cookies, HttpSession session, HttpServletResponse response) {
		//쿠키가 없다면 for문을 실행하지 않습니다.
		if(cookies != null) {
			//cookie 배열 만큼 for문을 돌리고
			for (int i = 0; i < cookies.length; i++) {
				//쿠키 배열을 하나씩 확인하고
				Cookie cookie = cookies[i];
				//만약 MEMBER_ID라는 cookie가 있으면 해당 쿠키의
				if(cookie.getName().equals("MEMBER_ID")) {
					//기간을 0으로 하여
					cookie.setMaxAge(0);
					//쿠키를 저장해 삭제해줍니다.
					response.addCookie(cookie);
				}
				if(cookie.getName().equals("CREW_CHECK")) {
					//기간을 0으로 하여
					cookie.setMaxAge(0);
					//쿠키를 저장해 삭제해줍니다.
					response.addCookie(cookie);
				}
			}
		}
		
		//로그아웃이므로 session에 MEMBER_ID 라는 이름을 삭제합니다.
		session.removeAttribute("MEMBER_ID");
		session.removeAttribute("CREW_CHECK");
		return "MAINPAGEACTION.do";
	}
}
