package com.coma.app.view.admin;


import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.AdminCheck;
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

    @AdminCheck
    @GetMapping("/userManagement.do")
    public String userManagement(Model model, MemberDTO memberDTO) {

        //-----------------------------------------------------------------------------
        //		페이지네이션
        int page = memberDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;
        int total = 0;
        log.info("min_num = {}", min_num);

        memberDTO.setMember_min_num(min_num);
        // 필터검색 ACTION
        // 회원이름, 회원 아이디, 가입날짜
        List<MemberDTO> datas = null;

        String search_keyword = memberDTO.getSearch_keyword();

        if(search_keyword != null){
            //아이디로 검색
            if (search_keyword.equals("MEMBERID")) {
                log.info("memberDTO [{}]",memberDTO);
                datas = this.memberService.selectAllSearchIdAdmin(memberDTO);
                total = this.memberService.selectOneSearchIdCountAdmin(memberDTO).getTotal();
            }
            //가입 날짜로 검색
            else if (search_keyword.equals("DATE")) {
                datas = this.memberService.selectAllSearchDateAdmin(memberDTO);
                total = this.memberService.selectOneSearchDateCountAdmin(memberDTO).getTotal();
            }
        }
        //전체 출력
        else {
            datas = this.memberService.selectAllSearchAdmin(memberDTO);
            total = this.memberService.selectOneSearchCountAdmin(memberDTO).getTotal();
        }
        // datas 로그
        log.info("UserManagementController datas {} " , datas);

        model.addAttribute("data", datas);
        model.addAttribute("search_keyword", search_keyword);
        model.addAttribute("search_content", memberDTO.getSearch_content());
        model.addAttribute("page", page);
        model.addAttribute("total", total);

        return "admin/userManagement";
    }

    @AdminCheck
    @GetMapping("/userManagementDetail.do")
    public String userManagementDetailGet(Model model, MemberDTO memberDTO) {
        String path = "admin/userManagementDetail";
        String infoPath = "userManagement.do";

        //회원 아이디를 받아오면 회원에 대한 정보를 불러오고
        MemberDTO memberOne = memberService.selectOneSearchId(memberDTO);

        if(memberOne == null){
            model.addAttribute("title", "잘못된요청");
            model.addAttribute("msg","현재 없는 회원입니다.");
            model.addAttribute("path", infoPath);
            path = "views/info";
        }

        //해당 정보를 View에 data 로 전달합니다.
        model.addAttribute("data", memberOne);


        return path;
    }

    // 회원 관리 -개인 상세
    @AdminCheck
    @PostMapping("/userManagementDetail.do")
    public String userManagementDetailPost(Model model, MemberDTO memberDTO) {

        String infoPath = "userManagement.do";
        String title = "회원정보 수정 성공";
        String msg = "회원정보를 수정하였습니다.";
        log.info("memberDTO [{}]", memberDTO);
        // 회원관리 update
        if (!this.memberService.updateAdmin(memberDTO)) {
            infoPath = "userManagementDetail.do?member_id="+memberDTO.getMember_id();
            title = "Server Error";
            msg = "회원정보 수정 실패";
        }

        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", infoPath);

        return "views/info";
    }

    @AdminCheck
    @PostMapping("adminDeleteMember.do")
    public String adminDeleteMember(Model model, MemberDTO memberDTO) {
        String path = "userManagement.do";
        String title = "회원 삭제";
        String msg = "["+ memberDTO.getMember_id()+"] 회원이 삭제 되었습니다.";

        if(!memberService.delete(memberDTO)){
            title = "서버 오류";
            msg = "회원삭제 실패";
        };
        log.info("path [{}]",path);
        log.info("title [{}]",title);
        log.info("msg [{}]",msg);

        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", path);

        return "views/info";
    }
}
