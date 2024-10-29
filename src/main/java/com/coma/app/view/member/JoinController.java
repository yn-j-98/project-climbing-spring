package com.coma.app.view.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class JoinController {


	@Autowired
	private MemberService memberService;
	@Autowired
	private HttpSession session;

	//---------페이지 이동----------
	@GetMapping("/join.do")
	public String join(MemberDTO memberDTO, Model model) {
		if (memberDTO.getMember_id() != null) {
			log.info("memberDTO [{}]",memberDTO);
			model.addAttribute("member_id",memberDTO.getMember_id());
		}

		return "views/join";
	}
	//---------------------------
	
	// 회원가입 페이지
	@LoginCheck
	@PostMapping("/join.do")
	public String joinPage(Model model, MemberDTO memberDTO) {
		
		String path="views/info";
		
		String member_id = (String) session.getAttribute("MEMBER_ID");
		// 만약 로그인 정보가 있다면
		if (member_id != null) {
			// main 페이지로 전달해줍니다.
			return "redirect:main.do"; // 리다이렉트 경로를 반환
		}
		else {
			boolean flag = this.memberService.insert(memberDTO);
			model.addAttribute("title", "회원 가입");
			if(flag) {
				System.err.println("회원가입 성공 로그");
				model.addAttribute("msg", "회원가입 성공!");
				model.addAttribute("path", "main.do");
			}
			//실패했다면 실패 내용을 전달해준다.
			else {
				System.err.println("회원가입 실패 로그");
				model.addAttribute("msg", "회원가입 실패..");
				model.addAttribute("path", "join.do");
			}
			return path;
		}
	}
}
