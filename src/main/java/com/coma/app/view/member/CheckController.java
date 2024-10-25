package com.coma.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;

@RestController	
public class CheckController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/checkId.do")
	public @ResponseBody String checkId(MemberDTO memberDTO) {
		System.out.println("CheckController checkId 비동기 처리 로그");
		
		boolean flag = false; 
		
//		data.setModel_member_id(request.getParameter("member_id"));
		memberDTO.setMember_condition("MEMBER_SEARCH_ID");
		//model 에 사용자 ID를 넘겨 값이 있는 지 확인 후	
		memberDTO = this.memberService.selectOneSearchId(memberDTO);
		
		//값이 없으면 true 를 반환 합니다.		
		if(memberDTO == null) {
			flag = true;
		}
		
		// view 로 값을 전달 합니다.
		return String.valueOf(flag);
	}
	
	@PostMapping("/checkPassword.do")
	public @ResponseBody String checkPassword(MemberDTO memberDTO) {
		System.out.println("CheckController checkPassword 비동기 처리 로그");
		
		memberDTO.setMember_condition("MEMBER_SEARCH_ID_PASSWORD");
		//model 에 사용자 ID를 넘겨 값이 있는 지 확인 후
		memberDTO = this.memberService.selectOneSearchIdPassword(memberDTO);
		
		String result = "false";
		//값이 없으면 true 를 반환 합니다.
		if(memberDTO != null) {
			result = "true";
		}
		// view 로 값을 전달 합니다.
		return result;
	}

}
