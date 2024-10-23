package com.coma.app.view.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;

import jakarta.servlet.http.HttpSession;

@Controller("boardDeleteController")
public class BoardDeleteController {

	@RequestMapping("/BOARDDELETEACTION.do")
	public String boardDelete(HttpSession session, BoardDAO boardDAO, BoardDTO boardDTO) {
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "MYPAGEPAGEACTION.do";

	      //사용자 아이디
	      String member_id = (String)session.getAttribute("MEMBER_ID");
		
		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//LoginPageAction 페이지로 전달해줍니다.
			path = "LOGINPAGEACTION.do";
		}
		else {
			//사용자가 선택한 글번호를 받아서
			boardDTO.setBoard_writer_id(member_id);
			//model 에 전달해 글을 삭제하고
			boolean flag = boardDAO.delete(boardDTO);
		}
		
		return path;
	}

}
