package com.coma.app.view.crew.common;


import java.util.List;

import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import com.coma.app.biz.crew_board.Crew_boardDTO;
import com.coma.app.biz.crew_board.Crew_boardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class CrewController{
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private CrewService crewService;

	@Autowired
	private Battle_recordService battle_recordService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private Crew_boardService crew_boardService;


	@LoginCheck
	@GetMapping("/crewList.do")


	public String crewList(HttpServletRequest request, HttpServletResponse response, Model model, CrewDTO crewDTO) {


		/*
		 * 페이지네이션을 하기 위해 page_num을 view에게 받아옵니다.
		 * 페이지네이션 계산 후
		 * 크루DAO와 DTO를 생성해서
		 * 크루 전체목록을 selectAll 컨디션 주고
		 * 크루 총 개수 selectOne 컨디션 (페이지네이션)
		 * model_crew_datas라는 ArrayList를 생성해서
		 * 뷰에게 전달
		 * model_crew_page_total과 page_num 또한 뷰에게 전달
		 *
		 */

		int pageNum = crewDTO.getPage();//요거 필요
		int boardSize = 5; // 한 페이지에 표시할 게시글 수 설정
		int minBoard = 1; // 최소 게시글 수 초기화

		if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
			pageNum = 1;
		}

		minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
		int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화


		crewDTO.setCrew_min_num(minBoard);


//            crewDTO.setCrew_condition("CREW_ALL");//크루 전체 목록 컨디션
		List<CrewDTO> crew_datas = this.crewService.selectAll(crewDTO);
		if(!crew_datas.isEmpty()) {
			System.out.println("CrewController.crewList.crew_datas ["+crew_datas.get(0).getCrew_num()+"]");
		}


		//			crewCount.setModel_crew_condition("CREW_ONE_COUNT");//크루 총개수 컨디션
		CrewDTO crewCount = this.crewService.selectOneCount(crewDTO);
		listNum = crewCount.getTotal();
		System.out.println("CrewController.crewList.listNum ["+listNum+"]");


		System.out.println("CrewController.crew_datas ["+crew_datas+"]");



		model.addAttribute("crew_datas", crew_datas);
		model.addAttribute("total", listNum);
		model.addAttribute("page", pageNum);




		return "views/crewList";


	}
	@LoginCheck
	@GetMapping("/crewInfo.do")
	public String crewInfo(HttpSession session, Model model, ServletContext servletContext, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO) {

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
		//로그인 정보가 있는지 확인해주고

		//사용자 크루 정보
		int crew_num = (Integer) session.getAttribute("CREW_NUM");

//            crewDTO.setCrew_condition("CREW_ONE_COUNT_CURRENT_MEMBER_SIZE");//선택 크루 인원 컨디션

		crewDTO = this.crewService.selectOneCountCurretMemberSize(crewDTO);
		System.out.println("CrewController.crewInfo.crewDTO = ["+crewDTO+"]");
		String filename = "";

		if (crewDTO == null) {//혹시모를 에러잡기 위해
			filename = "default.jpg"; // 디폴트(기본) 이미지

		} else {
			filename = crewDTO.getCrew_profile(); // 사용자의 프로필을 받아옴

		}


		model.addAttribute("crew_profile", servletContext.getContextPath() + "/crew_img_folder/" + filename);



//            battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");//승리목록 컨디션
		List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);


		model.addAttribute("CREW", crewDTO);
		System.out.println("CrewController.crewInfo.crewDTO ["+ crewDTO+"]");


		model.addAttribute("battle_record_datas", battle_record_datas);



		return "views/crewInformation";
	}


	@LoginCheck
	@PostMapping("/crewJoin.do")
	public String crewJoin(HttpSession session, Model model, MemberDTO memberDTO) {


		// 로그인 상태 체크
		String member_id = (String) session.getAttribute("MEMBER_ID");


		// 크루 번호 확인 및 유효성 검사
		try {
			int crew_num = (Integer) session.getAttribute("CREW_NUM");

			// 이미 크루에 가입된 경우
			if (crew_num > 0) {
				model.addAttribute("title", "불가능..");
				model.addAttribute("msg", "이미 소속된 크루가 있습니다.");
				model.addAttribute("path", "crewList.do");
			} else {
				// 크루 번호 파라미터 확인
				crew_num = memberDTO.getMember_crew_num();


				// 크루 가입 처리

				memberDTO.setMember_id(member_id);
				memberDTO.setMember_crew_num(crew_num);
//                    memberDTO.setMember_condition("MEMBER_UPDATE_CREW");

				boolean flag = memberService.updateCrew(memberDTO);

				if (flag) {
					// 업데이트 성공 시 세션 갱신
//
					session.setAttribute("CREW_CHECK", crew_num);


					model.addAttribute("title", "성공");

					model.addAttribute("msg", "해당 크루에 가입을 완료했습니다.");
					model.addAttribute("path", "crewPage.do");

				} else {
					// 업데이트 실패 시

					model.addAttribute("title", "실패");

					model.addAttribute("msg", "크루 가입에 실패했습니다.");
					model.addAttribute("path", "crewList.do");

				}
			}

		} catch (NumberFormatException e) {
			// 숫자 변환 실패 (유효하지 않은 파라미터)

			model.addAttribute("title", "실패");

			model.addAttribute("msg", "잘못된 요청입니다.");
			model.addAttribute("path", "crewList.do");

		}

		return "views/info";
	}

	@LoginCheck
	@GetMapping("/crew.do")
	public String crewPage(HttpSession session, Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO, MemberDTO memberDTO) {
//
		String member_id = (String) session.getAttribute("MEMBER_ID");

		if (member_id == null) {
			return "redirect:login.do";
		} else {
			int crew_num = (Integer) session.getAttribute("CREW_NUM");

			if (crew_num <= 0) {
				return "redirect:crewList.do";
			} else {
				// 크루 정보 가져오기
				crewDTO = this.crewService.selectOne(crewDTO);

				if (crewDTO != null) {
					// 이미 crewDTO에 프로필 이미지 경로가 있으므로 바로 추가
					model.addAttribute("CREW", crewDTO);
					model.addAttribute("crewProfilePath", crewDTO.getCrew_profile());
				} else {
					// 크루 정보가 없을 경우 기본 프로필 이미지 경로 설정
					model.addAttribute("crewProfilePath", "default.jpg");
				}
				String filename = crewDTO.getCrew_profile(); // 사용자의 프로필을 받아옴

				crewDTO.setCrew_profile(servletContext.getContextPath() + "/crew_img_folder/" + filename);

				// 크루원의 정보를 가져와 model에 추가
//                memberDTO.setMember_condition("MEMBER_ALL_SEARCH_CREW_MEMBER_NAME");
				List<MemberDTO> member_crew_datas = this.memberService.selectAllSearchCrewMemberName(memberDTO);
				model.addAttribute("member_crew_datas", member_crew_datas);

				// 크루 승리 목록 정보를 가져와 model에 추가
//                battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");
				List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinner(battle_recordDTO);
				model.addAttribute("battle_record_datas", battle_record_datas);
			}
		}

		return "views/myCrewPage";
	}

	@LoginCheck
	@RequestMapping(value="/crewCommunity.do", method=RequestMethod.POST)
	public String crewCommunity(HttpSession session, Model model, Crew_boardDTO crew_boardDTO) {


		// 로그인 체크
		String member_id = (String) session.getAttribute("MEMBER_ID") ; // 사용자 아이디

		if (member_id == null) {
			// 로그인하지 않은 경우 로그인 페이지로 리다이렉트

			return "redirect:login.do";
		} else {
			// 사용자 크루 정보
			int crew_num = (Integer) session.getAttribute("CREW_NUM");

			if (crew_num <= 0) {
				// 크루가 없는 경우 크루 목록 페이지로 리다이렉트

				return "redirect:crewList.do";
			} else {
				int pageNum = 1; // 페이지 번호 초기화
//

				pageNum = crew_boardDTO.getPage();//요거 필요
				int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
				int minBoard = 1; // 최소 게시글 수 초기화

				minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
				int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화
				if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
					pageNum = 1;
				}


				crew_boardDTO.setCrew_board_min_num(minBoard);

				//줄바꿈 처리
				String br_content = "";
				// 게시글 목록 가져오기
//                crew_boardDTO.setCrew_board_condition("CREW_BOARD_ALL_CREW_BOARD");

				List<Crew_boardDTO> crew_board_datas = this.crew_boardService.selectAllCrewBoard(crew_boardDTO);
				for(int i=0;i<crew_board_datas.size();i++) {
					System.out.println(crew_board_datas.get(i));
					br_content = crew_board_datas.get(i).getCrew_board_content();
					br_content = br_content.replace("\n", "<br>");
					System.err.println("줄바꿈 적용 내용 = "+br_content);
					crew_board_datas.get(i).setCrew_board_content(br_content);
				}
//				 프로필 이미지 URL 설정


				// 총 게시글 수 확인

				crew_boardDTO.setCrew_board_writer_id(member_id);

//                crew_boardDTO.setCrew_board_condition("CREW_BOARD_ONE_COUNT");
				Crew_boardDTO crew_board_count = this.crew_boardService.selectOneCount(crew_boardDTO);
				listNum = crew_board_count.getTotal();



				model.addAttribute("crew_board_datas", crew_board_datas);
				model.addAttribute("total", listNum);
				model.addAttribute("page", pageNum);

				System.out.println("crewController.crewCommunity.total = "+listNum);
				System.out.println("crewController.crewCommunity.page = "+pageNum);
			}
		}


		return "views/crewCommunity";
	}


}
