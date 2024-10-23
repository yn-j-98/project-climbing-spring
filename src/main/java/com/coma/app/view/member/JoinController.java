package com.coma.app.view.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("joinController")
public class JoinController {

	@RequestMapping("/JOINPAGEACTION.do")
	public String joinPage(HttpServletRequest request, HttpServletResponse response) {
		String path = "redirect:join.jsp";
		return path;
	}

	@RequestMapping("/JOINACTION.do")
	public String join(HttpSession session, Model model, MemberDAO dao, MemberDTO data) {
		String path = "info";
		
		String member_id = (String)session.getAttribute("member_id");
		
		// 만약 로그인 정보가 있다면
		if(member_id != null) {
			// main 페이지로 전달해줍니다.
			path = "MAINPAGEACTION.do";
		}
		else { // 로그인 정보가 없다면
			//model 에 등록을 요청하고
			boolean flag = dao.insert(data);
			
			//만약 성공했다면 회원가입 성공했다는 내용을 전달해주고
			if(flag) {
				System.err.println("회원가입 성공 로그");
				model.addAttribute("msg", "회원가입 성공!");
				model.addAttribute("path", "MAINPAGEACTION.do");
			}
			//실패했다면 실패 내용을 전달해준다.
			else {
				System.err.println("회원가입 실패 로그");
				model.addAttribute("msg", "회원가입 실패..");
				model.addAttribute("path", "JOINPAGEACTION.do");
			}
		}
		
		return path;
	}
	
}
