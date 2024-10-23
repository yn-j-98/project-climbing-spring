package com.coma.app.view.asycnServlet;

import java.io.IOException;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import com.coma.app.biz.member.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet implementation class IDCheckAction
 */
@WebServlet("/checkId")
public class IDCheckAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MemberService memberService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IDCheckAction() {
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
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO data = new MemberDTO();
		//PrintWriter out = response.getWriter();
		
		boolean flag = false;
		
		//view 에서 사용자 ID를 보내주면 MemberDTO 에 값을 추가
		data.setMember_id(request.getParameter("member_id"));
		//data.setMember_condition("MEMBER_SEARCH_ID");
		//model 에 사용자 ID를 넘겨 값이 있는 지 확인 후
		data = memberService.selectOneSearchId(data);
		
		//값이 없으면 true 를 반환 합니다.
		if(data == null) {
			flag = true;
		}
		
		// view 로 값을 전달 합니다.
		response.getWriter().print(flag);
		
	}

}
