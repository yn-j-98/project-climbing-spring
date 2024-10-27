package com.coma.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

// 로그인 api
@RestController
public class LoginAPIController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession session; // 세션을 자동으로 주입

    @PostMapping("/loginAPI.do")
    public @ResponseBody String loginAPI(@RequestBody MemberDTO memberDTO,  String api_id) {
      

        // 해당 사용자의 아이디가 있는지 확인
        memberDTO.setMember_id(api_id);
        memberDTO = this.memberService.selectOneSearchId(memberDTO);

        // 만약 있으면 세션 값에 저장해서 로그인 진행
        if (memberDTO != null) {
            System.out.println("LoginAPIController memberDTO.member_id 로그 회원 아이디 : " + memberDTO.getMember_id());
            session.setAttribute("MEMBER_ID", memberDTO.getMember_id());
            session.setAttribute("CREW_CHECK", memberDTO.getMember_crew_num());
            System.out.println(memberDTO.getMember_id());
            System.out.println(memberDTO.getMember_crew_num());
            return "true"; // 문자열로 "true" 반환
        }
        // 만약 없으면 회원가입 페이지로 넘겨서 회원가입할 수 있도록 한다.
        else {
            System.out.println("LoginAPIController memberDTO NULL 로그 VIEW 에서 넘겨준 회원 아이디 : " + api_id);
            return api_id; // 회원가입을 위한 아이디 반환
        }
    }
}
