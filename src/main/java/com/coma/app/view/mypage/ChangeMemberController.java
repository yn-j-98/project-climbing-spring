package com.coma.app.view.mypage;

import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller("chargeMemberController")
public class ChangeMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/CHANGEMEMBERPAGEACTION.do")
	public String execute(HttpSession session, ServletContext servletContext, Model model, MemberDTO data ) {
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "editMyPage";

	      //사용자 아이디
	      String member_id = (String)session.getAttribute("MEMBER_ID");
		
		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//main 페이지로 전달해줍니다.
			path = "LOGINPAGEACTION.do";
		}
		else {
			//사용자 아이디를 model에 전달하고
			data.setMember_id(member_id);
			//data.setMember_condition("MEMBER_SEARCH_ID");
			//전달해준 사용자 정보를 받아와 줍니다.
			data = memberService.selectOneSearchId(data);
			String profile = "";
			if(data.getMember_profile() == null) {
				profile = "default.png";
			}
			else {
				profile = data.getMember_profile();
			}
			data.setMember_profile(servletContext.getContextPath()+ "/profile_img/" + profile);
			model.addAttribute("data", data);
		}
		return path;
	}

}
