package com.coma.app.view.crew.join;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.battle_record.Battle_recordServiceImpl;
import com.coma.app.biz.crew.CrewService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.crew.CrewDAO;
import com.coma.app.biz.crew.CrewDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller("crewJoinPageController")
public class CrewJoinPageController{

	@Autowired
	private Battle_recordDAO battle_recordDAO;
    @Autowired
    private Battle_recordServiceImpl battle_recordService;
	@Autowired
	private CrewService crewService;

	@RequestMapping("/CrewListPage.do")
	public String crewListPage(HttpSession session, Model model, CrewDTO crewDTO) {
		String path = "crewList"; // 마이페이지로

		/*
		 * 페이지네이션을 하기 위해 page_num을 view에게 받아옵니다.
		 * 페이지네이션 계산 후 
		 * 크루DAO와 DTO를 생성해서
		 * 크루 전체목록을 selectAll 컨디션 주고
		 * 크루 총 개수 selectOne 컨디션 (페이지네이션)
		 * Crew_datas라는 ArrayList를 생성해서
		 * 뷰에게 전달
		 * Crew_page_total과 page_num 또한 뷰에게 전달
		 * 
		 */
		String member_id = (String)session.getAttribute("MEMBER_ID");
		//사용자 아이디
		if (member_id == null) {
			// 로그인 페이지로 전달
			path = "LOGINACTION.do";
		} 
		else {
			int pageNum = crewDTO.getPage(); // 페이지 번호 초기화
			// 페이지네이션 부분
			int boardSize = 5; // 한 페이지에 표시할 게시글 수 설정
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


			int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

			crewDTO.setCrew_min_num(minBoard);
			crewDTO.setCrew_max_num(maxBoard);

			crewDTO.setCrew_condition("CREW_ALL");//크루 전체 목록 컨디션
			List<CrewDTO> crew_datas = crewService.selectAll(crewDTO);
			if(!crew_datas.isEmpty()) {
				System.out.println("CrewListPageAction 72"+crew_datas.get(0).getCrew_num());
			}
			
			//crewDTO.setCrew_condition("CREW_ONE_COUNT");//크루 총개수 컨디션
			CrewDTO crew_Count = crewService.selectOneCount(crewDTO);
			listNum = crew_Count.getCrew_total();
			System.out.println("CrewListPageAction 78 "+listNum);
			
			model.addAttribute("crew_datas", crew_datas);
			model.addAttribute("crew_page_total", listNum);
			model.addAttribute("currentPage", pageNum);
		}

		return path;
	}

	@RequestMapping("/CrewInformationPage.do")
	public String crewInformationPage(HttpSession session, ServletContext servletContext, Model model, CrewDAO crewDAO, CrewDTO crewDTO,
			Battle_recordDTO battle_recordDTO, Battle_recordDAO battle_recordDAO) {
		String path = "crewInformation"; //  페이지로 이동
		/*
		 * 크루 상세보기 페이지
		 * 뷰에게 크루 번호를 받아옵니다
		 * crewDTO와 DAO를 생성해서 받아온 크루번호를 DTO에 set해서 
		 * crew selectOne 크루의 정보을 불러옵니다.
		 * memverDTO와 DAO를 생성해서 선택한 크루의 인원을
		 * selectOne해줍니다.
		 * model_battle_record_datas라는
		 * ArrayList를 생성해서 승리목록을 뷰에게 전달합니다.
		 * crewDTO(크루의 정보)또한 뷰에게 전달합니다.
		 */
		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");
		//사용자 아이디
		if (member_id == null) {
			// 로그인 페이지로 전달
			path = "LOGINACTION.do";
		} 
		else {
			//사용자 크루 정보
			int crew_num = Integer.parseInt((String)session.getAttribute("CREW_NUM"));
			
			crewDTO.setCrew_condition("CREW_ONE_COUNT_CURRENT_MEMBER_SIZE");//선택 크루 인원 컨디션
			crewDTO = crewDAO.selectOne(crewDTO);
			System.out.println("57 CrewInformationPageAction crewDTO = "+crewDTO);
			String filename = "";

			if (crewDTO == null) {//혹시모를 에러잡기 위해
				filename = "default.jpg"; // 디폴트(기본) 이미지
			} else {
				filename = crewDTO.getCrew_profile(); // 사용자의 프로필을 받아옴
			}

			model.addAttribute("Crew_profile", servletContext.getContextPath() + "/crew_img_folder/" + filename);

			battle_recordDTO.setBattle_record_crew_num(crew_num);
			battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");//승리목록 컨디션
			List<Battle_recordDTO> model_battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);

			model.addAttribute("CREW", crewDTO);
			System.out.println("CREW로그 = "+ crewDTO);
			
			model.addAttribute("model_battle_record_datas", model_battle_record_datas);
		}
		return path;
	}
}
