package com.coma.app.view.asycnServlet;

import com.coma.app.biz.member.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet implementation class LoginAPI
 */
@WebServlet("/loginAPI")
public class LoginAPIAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	MemberService memberService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAPIAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String api_id = request.getParameter("email");
		
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO = new MemberDTO();
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		//해당 사용자의 아이디가 있는지 확인해서
		memberDTO.setMember_condition("MEMBER_SEARCH_ID");
		memberDTO.setMember_id(api_id);
		memberDTO = memberService.selectOneSearchId(memberDTO);
		
		//만약 있으면 session 값에 저장해서 로그인 진행
		if(memberDTO != null) {
			System.out.println("LoginAPIAtion memberDTO.member_id 로그 회원 아이디 : "+memberDTO.getMember_id());
			session.setAttribute("MEMBER_ID", memberDTO.getMember_id());
			session.setAttribute("CREW_CHECK", memberDTO.getMember_crew_num());
			System.out.println(memberDTO.getMember_id());
			System.out.println(memberDTO.getMember_crew_num());
			out.print(true);
		}
		//만약 없으면 회원가입 페이지로 넘겨서 회원가입할 수 있도록 한다.
		else {
			System.out.println("LoginAPIATION memberDTO NULL 로그 VIEW 에서 넘겨준 회원 아이디 : "+api_id);
			out.print(api_id);
		}
	}

}
