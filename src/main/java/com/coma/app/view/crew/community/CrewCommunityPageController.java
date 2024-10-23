package com.coma.app.view.crew.community;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.crew_board.Crew_boardService;
import com.coma.app.biz.crew_board.Crew_boardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.crew_board.Crew_boardDAO;
import com.coma.app.biz.crew_board.Crew_boardDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("crewCommunityPageController")
public class CrewCommunityPageController{

	@Autowired
	private Crew_boardService crewBoardService;
    @Autowired
    private Crew_boardServiceImpl crew_boardService;

	@RequestMapping("/CrewCommunityPage.do")
	public String crewCommunityPage(HttpSession session, ServletContext servletContext, Model model, Crew_boardDTO crew_boardDTO, Crew_boardDAO crew_boardDAO) {
		String path = "crewCommunity"; // 기본 페이지 경로

		// 로그인 체크
		String member_id = (String)session.getAttribute("MEMBER_ID"); // 사용자 아이디

		if (member_id == null) {
			// 로그인하지 않은 경우 로그인 페이지로 리다이렉트
			path = "LOGINACTION.do";
		} else {
			// 사용자 크루 정보
			int crew_num = Integer.parseInt((String)session.getAttribute("CREW_CHECK"));

			if (crew_num <= 0) {
				// 크루가 없는 경우 크루 목록 페이지로 리다이렉트
				path = "CrewListPage.do";
			} else {
				int pageNum = crew_boardDTO.getPage(); // 페이지 번호 초기화
				int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
		        int minBoard = 1; // 최소 게시글 수 초기화
		        int maxBoard = 1; // 최대 게시글 수 초기화
		        
		        // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
		        if(pageNum <= 1) {
		            // 페이지 번호가 1 이하일 경우
		            minBoard = 1; // 최소 게시글 번호를 1로 설정
		            maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
		        }
		        else {
		            // 페이지 번호가 2 이상일 경우
		            minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
		            maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
		        }


				crew_boardDTO.setCrew_board_min_num(minBoard);
				crew_boardDTO.setCrew_board_max_num(maxBoard);
				crew_boardDTO.setCrew_board_writer_id(member_id);
				crew_boardDTO.setCrew_board_condition("CREW_BOARD_ALL_CREW_BOARD");
				
				//줄바꿈 처리
				String br_content = "";
				// 게시글 목록 가져오기
				List<Crew_boardDTO> crew_board_datas = crew_boardService.selectAllCrewBoard(crew_boardDTO);
				for(int i=0;i<crew_board_datas.size();i++) {
					System.out.println(crew_board_datas.get(i));
					br_content = crew_board_datas.get(i).getCrew_board_content();
					br_content = br_content.replace("\n", "<br>");
					System.err.println("줄바꿈 적용 내용 = "+br_content);
					crew_board_datas.get(i).setCrew_board_content(br_content);
				}
				// 프로필 이미지 URL 설정
				for (Crew_boardDTO data : crew_board_datas) {
					String filename = data.getCrew_board_member_profile();
					data.setCrew_board_member_profile(servletContext.getContextPath() + "/profile_img/" + filename);
				}

				// 총 게시글 수 확인
				crew_boardDTO.setCrew_board_condition("CREW_BOARD_ONE_COUNT");
				crew_boardDTO = crew_boardDAO.selectOne(crew_boardDTO);
				int listNum = crew_boardDTO.getCrew_board_total();

				model.addAttribute("crew_board_datas", crew_board_datas);
				model.addAttribute("totalCount", listNum);
				model.addAttribute("currentPage", pageNum);
				System.out.println("totalCount = "+listNum);
				System.out.println("currentPage = "+pageNum);
			}
		}

		return path;
	}
}
