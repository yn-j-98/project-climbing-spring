package com.coma.app.view.crew.community;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.CrewCheck;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class CrewCommunityPageController {

    @Autowired
    private MemberService memberService;

    @Autowired
    HttpSession session;

    @LoginCheck
    @CrewCheck
    @GetMapping("/crewCommunity.do")
    public String showCrewCommunityPage(Model model,
                                        @SessionAttribute(value = "MEMBER_ID", required = false) String member_id,
                                        MemberDTO memberDTO) {
        memberDTO.setMember_id(member_id);
        MemberDTO data = memberService.selectOneSearchId(memberDTO);
        model.addAttribute("data", data);
        return "views/crewCommunity"; // View 이름 반환
    }
}