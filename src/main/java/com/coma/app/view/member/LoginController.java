package com.coma.app.view.member;

import com.coma.app.view.annotation.LoginCheckImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

    @LoginCheck
    @GetMapping("/login.do") // 로그인 페이지 이동
    public String login() {
        return "views/login";
    }

    @LoginCheck
    @PostMapping("/login.do") // 로그인 처리
    public String login(Model model, MemberDTO memberDTO, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        // 로그인 정보가 있는지 확인
        String member_id = (String) session.getAttribute("MEMBER_ID");

        if (member_id == null) {
            memberDTO = this.memberService.selectOneSearchIdPassword(memberDTO);
            if (memberDTO != null) {
                // 로그인 성공 시 세션에 회원 정보 저장
                session.setAttribute("MEMBER_ID", memberDTO.getMember_id());
                session.setAttribute("CREW_CHECK", memberDTO.getMember_crew_num());
                session.setAttribute("MEMBER_ROLE", memberDTO.getMember_role());
                setCookies(memberDTO, response, request); // 쿠키 설정 메서드 호출

                if (memberDTO.getMember_role().equals("T")) {
                    model.addAttribute("title", "관리자로 로그인 성공!");
                    model.addAttribute("msg", "관리자 메인 페이지로 이동합니다.");
                    model.addAttribute("path", "mainManagement.do");

                } else {
                    model.addAttribute("title", "로그인 성공!");
                    model.addAttribute("msg", "메인 페이지로 이동합니다.");
                    model.addAttribute("path", "main.do");

                }


            } else {
                model.addAttribute("title", "로그인 실패!");
                model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
                model.addAttribute("path", "login.do");
            }
        }


        return "views/info";
    }

    @LoginCheck
    @GetMapping("/logout.do")// 로그아웃 처리
    public String logout(Model model, HttpServletResponse response, HttpServletRequest request) {
        LoginCheckImpl.logout(request, response);

        model.addAttribute("title", "로그아웃 성공!");
        model.addAttribute("msg", "메인 페이지로 이동합니다.");
        model.addAttribute("path", "main.do");

        return "views/info";
    }

    // 쿠키 설정 메서드
    private void setCookies(MemberDTO memberDTO, HttpServletResponse response, HttpServletRequest request) {

        String auto = request.getParameter("VIEW_AUTO_LOGIN"); // 자동 로그인 체크

        if (auto != null) { // 자동 로그인 체크되어있다면
            Cookie member_id_cookie = new Cookie("MEMBER_ID", memberDTO.getMember_id());
            Cookie member_crew_cookie = new Cookie("CREW_CHECK", String.valueOf(memberDTO.getMember_crew_num()));
            Cookie member_role_cookie = new Cookie("MEMBER_ROLE", memberDTO.getMember_role());

            member_id_cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키 유효 시간 설정 (7일)
            member_crew_cookie.setMaxAge(60 * 60 * 24 * 7);

            response.addCookie(member_id_cookie);
            response.addCookie(member_crew_cookie);
        }
    }
}