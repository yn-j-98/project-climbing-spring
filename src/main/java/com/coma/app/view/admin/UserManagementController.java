package com.coma.app.view.admin;


import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.LoginCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class UserManagementController {


    @Autowired
    private MemberService memberService;

    @LoginCheck
    @GetMapping("/userManagement.do")
    public String userManagement(Model model, MemberDTO memberDTO) {

        // 세션에서 관리자 ID 가져오기
//        String member_id = (String) session.getAttribute("MEMBER_ID");

        //-----------------------------------------------------------------------------
        // !★!★!★!★!★!★!★!★ TODO Impl (컨디션값) 참고하기!★!★!★!★!★!★!★!★!★!★!★!★
        //		페이지네이션

        int page = memberDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;

        log.info("min_num = {}", min_num);

        memberDTO.setMember_min_num(min_num);
//		필터검색 ACTION
//		회원이름, 회원 아이디, 가입날짜
        List<MemberDTO> datas = null;


        String search_keyword = memberDTO.getSearch_keyword();

        if (search_keyword.equals("member_id")) {
            datas = this.memberService.selectAllSearchIdAdmin(memberDTO);

//            // TODO 삭제해야되는가?
//        } else if (search_keyword.equals("member_name")) {
//            search_datas = this.memberService.selectAllSearchNameAdmin(memberDTO);

        } else if (search_keyword.equals("member_join_date")) {
            datas = this.memberService.selectAllSearchDateAdmin(memberDTO);

        } else {
            model.addAttribute("title", "Server Error");
            model.addAttribute("msg", "search_keyword error");
            model.addAttribute("path", "userManagement.do");
            return "views/info";
        }
        // datas 로그
        log.info("UserManagementController datas {} " , datas);

        model.addAttribute("datas", datas);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "admin/userManagement";
    }


    // 회원 관리 리스트
    @PostMapping("/userManagement.do")
    public String userManagementList(Model model, MemberDTO memberDTO) {
        //		회원 탈퇴 MEMBER DELETE
        boolean flag = this.memberService.delete(memberDTO);
        model.addAttribute("title","회원 탈퇴");
        if(flag){
            model.addAttribute("msg", "회원 탈퇴 처리 성공!");
        }
        else {
            model.addAttribute("msg", "회원 탈퇴 처리 실패 ..");
        }
        model.addAttribute("path", "userManagement.do");

        return "views/info";
    }

    @GetMapping("/userManagementDetail.do")
    public String userManagementDetail() {
        return "admin/userManagementDetail";
    }

    // 회원 관리 -개인 상세 ( 모달 )
    @PostMapping("/userManagementDetail.do")
    public String userManagementDetail(Model model, MemberDTO memberDTO) {

        // 회원관리 insert
        boolean flag = this.memberService.insert(memberDTO);
        if (!flag) {
            model.addAttribute("title", "Server Error");
            model.addAttribute("msg", "member insert 실패");
            model.addAttribute("path", "userManagement.do");
            return "views/info";
        }
        // role 로그
        System.out.println("userManagementDetail - role =" + (memberDTO.getMember_role()));


        return "admin/userManagement";
    }

}
