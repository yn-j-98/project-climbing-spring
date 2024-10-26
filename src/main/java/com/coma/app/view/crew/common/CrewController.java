package com.coma.app.view.crew.join;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CrewController{

	@Autowired
	private CrewService crewService;

	@Autowired
	private Battle_recordService battle_recordService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private Crew_boardService crew_boardService;

	@RequestMapping(value="/crewList.do", method=RequestMethod.POST)
	public String crewList(HttpServletRequest request, HttpServletResponse response, Model model, CrewDTO crewDTO) {
		//		ActionForward forward = new ActionForward();
		//		String path = "crewList.jsp"; // 마이페이지로
		//		boolean flagRedirect = false; // 포워드 방식

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
		String login[] = LoginCheck.Success(request, response);
		String member_id = login[0];
		//사용자 아이디
		if (member_id == null) {
			// 로그인 페이지로 전달

			return "redirect:login.do";
		}
		else {
			int pageNum = crewDTO.getPage();//요거 필요
			int boardSize = 5; // 한 페이지에 표시할 게시글 수 설정
			int minBoard = 1; // 최소 게시글 수 초기화

			minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
			int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화


			crewDTO.setCrew_min_num(minBoard);
			//			crewDTO.setModel_crew_max_num(maxBoard);

//            crewDTO.setCrew_condition("CREW_ALL");//크루 전체 목록 컨디션
			List<CrewDTO> crew_datas = crewService.selectAll(crewDTO);
			if(!crew_datas.isEmpty()) {
				System.out.println("CrewController.crewList.crew_datas ["+crew_datas.get(0).getCrew_num()+"]");
			}

			//			CrewDTO crewCount = new CrewDTO();
			//			crewCount.setModel_crew_condition("CREW_ONE_COUNT");//크루 총개수 컨디션
			//			crewCount = crewDAO.selectOne(crewCount);

//            crewDTO.setCrew_condition("CREW_ONE_COUNT");
			CrewDTO crewCount = crewService.selectOneCount(crewDTO);
			listNum = crewCount.getCrew_total();
			System.out.println("CrewController.crewList.listNum ["+listNum+"]");

			//			request.setAttribute("model_crew_datas", model_crew_datas);
			//			System.out.println("CrewListPageAction 83 "+model_crew_datas.get(0));
			//			System.out.println(model_crew_datas.get(1));
			//			System.out.println(model_crew_datas.get(2));
			//			System.out.println(model_crew_datas.get(3));
			//			request.setAttribute("model_crew_page_total", listNum);
			//			request.setAttribute("currentPage", pageNum); // 현재 페이지 번호

			model.addAttribute("crew_datas", crew_datas);
			model.addAttribute("crew_page_total", listNum);
			model.addAttribute("currentPage", pageNum);


		}

		//		forward.setPath(path); // 이동할 페이지 설정
		//		forward.setRedirect(flagRedirect); // 페이지 이동 방식 설정 (포워드)

		return "views/crewList";


	}
	@RequestMapping(value="/crewInfo.do", method=RequestMethod.POST)
	public String crewInfo(HttpServletRequest request, HttpServletResponse response, Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO) {
		//		ActionForward forward = new ActionForward();
		//		String path = "crewInformation.jsp"; //  페이지로 이동
		//		boolean flagRedirect = false; // 포워드 방식 사용 여부 설정 (false = forward 방식)
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
		String login[] = LoginCheck.Success(request, response);
		//사용자 아이디
		String member_id = login[0];
		//사용자 아이디
		if (member_id == null) {
			// 로그인 페이지로 전달
			//			path = "LOGINACTION.do";
			//			flagRedirect = true;
			return "redirect:login.do";
		}
		else {
			//사용자 크루 정보
			int crew_num = Integer.parseInt(login[1]);
			//			int view_crew_num = 0;

			//			if(request.getParameter("view_crew_num") !=null) {//null체크
			//				System.out.println("49 CrewInformationPageAction view_crew_num = "+request.getParameter("view_crew_num"));
			//			}//FIXME
			//			view_crew_num =	Integer.parseInt(request.getParameter("view_crew_num"));

			//			CrewDTO crewDTO = new CrewDTO();
			//			CrewDAO crewDAO = new CrewDAO();
			//			crewDTO.setModel_crew_num(view_crew_num);

//            crewDTO.setCrew_condition("CREW_ONE_COUNT_CURRENT_MEMBER_SIZE");//선택 크루 인원 컨디션

			crewDTO = crewService.selectOneCountCurretMemberSize(crewDTO);
			System.out.println("CrewInformationPageAction crewDTO = "+crewDTO);
			String filename = "";

			if (crewDTO == null) {//혹시모를 에러잡기 위해
				filename = "default.jpg"; // 디폴트(기본) 이미지

			} else {
				filename = crewDTO.getCrew_profile(); // 사용자의 프로필을 받아옴

			}

			//			request.setAttribute("crew_profile", request.getServletContext().getContextPath() + "/crew_img_folder/" + filename);

			model.addAttribute("crew_profile", request.getServletContext().getContextPath() + "/crew_img_folder/" + filename);
			//			Battle_recordDTO battle_recordDTO = new Battle_recordDTO();
			//			Battle_recordDAO battle_recordDAO = new Battle_recordDAO();
			//			battle_recordDTO.setModel_battle_record_crew_num(view_crew_num);

//            battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");//승리목록 컨디션
			List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);

			//			request.setAttribute("CREW", crewDTO);
			model.addAttribute("CREW", crewDTO);
			System.out.println("CrewController.crewInfo.crewDTO ["+ crewDTO+"]");

			//			request.setAttribute("model_battle_record_datas", model_battle_record_datas);
			model.addAttribute("battle_record_datas", battle_record_datas);
		}

		//		forward.setPath(path);
		//		forward.setRedirect(flagRedirect);
		return "views/crewInformation";
	}
	@RequestMapping(value="/crewJoin.do", method=RequestMethod.POST)
	public String crewJoin(HttpServletRequest request, HttpServletResponse response, Model model, MemberDTO memberDTO) {
		//	    ActionForward forward = new ActionForward();
		//	    String path = "info.jsp"; // 크루 가입 안내 페이지
		//	    boolean flagRedirect = false; // 포워드 방식 (false = forward)

		// 로그인 상태 체크
		String login[] = LoginCheck.Success(request, response);
		String member_id = login[0];

		// 로그인 되어 있지 않은 경우
		if (member_id == null) {
			//	        path = "LOGINACTION.do"; // 로그인 페이지로 이동
			//	        flagRedirect = true;
			return "redirect:login.do";
		}
		else {
			// 크루 번호 확인 및 유효성 검사
			try {
				int crew_num = Integer.parseInt(login[1]);

				// 이미 크루에 가입된 경우
				if (crew_num > 0) {
					//	                request.setAttribute("msg", "이미 소속된 크루가 있습니다.");
					//	                request.setAttribute("path", "CrewListPage.do");
					model.addAttribute("title", "불가능..");
					model.addAttribute("msg", "이미 소속된 크루가 있습니다.");
					model.addAttribute("path", "CrewListPage.do");
				} else {
					// 크루 번호 파라미터 확인
					int view_crew_num = memberDTO.getMember_crew_num();

					//					if (request.getParameter("view_crew_num") != null) {
					//						view_crew_num = Integer.parseInt(request.getParameter("view_crew_num"));
					//					} else {
					//						//	                    request.setAttribute("msg", "잘못된 요청입니다.");
					//						//	                    request.setAttribute("path", "CrewListPage.do");
					//						model.addAttribute("title", "불가능..");
					//						model.addAttribute("msg", "잘못된 요청입니다.");
					//						model.addAttribute("path", "CrewListPage.do");
					//
					//						//	                    forward.setPath(path);
					//						//	                    forward.setRedirect(flagRedirect);
					//						return "info"; // 유효하지 않은 요청 시 바로 종료
					//					}

					// 크루 가입 처리

					memberDTO.setMember_id(member_id);
					memberDTO.setMember_crew_num(view_crew_num);
//                    memberDTO.setMember_condition("MEMBER_UPDATE_CREW");

					boolean flag = memberService.updateCrew(memberDTO);

					if (flag) {
						// 업데이트 성공 시 세션 갱신
						HttpSession session = request.getSession();
						session.setAttribute("CREW_CHECK", view_crew_num);

						//	                    request.setAttribute("msg", "해당 크루에 가입을 완료했습니다.");
						//	                    request.setAttribute("path", "CrewPage.do");
						model.addAttribute("title", "성공");

						model.addAttribute("msg", "해당 크루에 가입을 완료했습니다.");
						model.addAttribute("path", "CrewPage.do");

					} else {
						// 업데이트 실패 시
						//	                    request.setAttribute("msg", "크루 가입에 실패했습니다.");
						//	                    request.setAttribute("path", "CrewListPage.do");
						model.addAttribute("title", "실패");

						model.addAttribute("msg", "크루 가입에 실패했습니다.");
						model.addAttribute("path", "CrewList.do");

					}
				}

			} catch (NumberFormatException e) {
				// 숫자 변환 실패 (유효하지 않은 파라미터)
				//	            request.setAttribute("msg", "잘못된 요청입니다.");
				//	            request.setAttribute("path", "CrewListPage.do");
				model.addAttribute("title", "실패");

				model.addAttribute("msg", "잘못된 요청입니다.");
				model.addAttribute("path", "CrewList.do");

			}
		}

		//	    forward.setPath(path);
		//	    forward.setRedirect(flagRedirect);
		return "views/info";
	}

	@RequestMapping(value="/crew.do", method=RequestMethod.POST)
	public String crewPage(HttpServletRequest request, HttpServletResponse response, Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO, MemberDTO memberDTO) {
		String login[] = LoginCheck.Success(request, response);
		String member_id = login[0];

		if (member_id == null) {
			return "redirect:login.do";
		} else {
			int crew_num = Integer.parseInt(login[1]);

			if (crew_num <= 0) {
				return "redirect:crewList.do";
			} else {
				// 크루 정보 가져오기
				crewDTO.setCrew_condition("CREW_ONE");
				crewDTO = crewService.selectOne(crewDTO);

				if (crewDTO != null) {
					// 이미 crewDTO에 프로필 이미지 경로가 있으므로 바로 추가
					model.addAttribute("CREW", crewDTO);
					model.addAttribute("crewProfilePath", crewDTO.getCrew_profile());
				} else {
					// 크루 정보가 없을 경우 기본 프로필 이미지 경로 설정
					model.addAttribute("crewProfilePath", "default.jpg");
				}

				//	            crewDTO.setModel_crew_profile(request.getServletContext().getContextPath() + "/crew_img_folder/" + filename);

				// 크루원의 정보를 가져와 model에 추가
//                memberDTO.setMember_condition("MEMBER_ALL_SEARCH_CREW_MEMBER_NAME");
				List<MemberDTO> member_crew_datas = memberService.selectAllSearchCrewMemberName(memberDTO);
				model.addAttribute("member_crew_datas", member_crew_datas);

				// 크루 승리 목록 정보를 가져와 model에 추가
//                battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");
				List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);
				model.addAttribute("battle_record_datas", battle_record_datas);
			}
		}

		return "views/MyCrewPage";
	}

	@RequestMapping(value="/crewCommunity.do", method=RequestMethod.POST)
	public String crewCommunity(HttpServletRequest request, HttpServletResponse response, Model model, Crew_boardDTO crew_boardDTO) {
		//		ActionForward forward = new ActionForward();
		//		String path = "crewCommunity.jsp"; // 기본 페이지 경로
		//		boolean flagRedirect = false; // 포워드 방식 초기화

		// 로그인 체크
		String login[] = LoginCheck.Success(request, response);
		String member_id = login[0]; // 사용자 아이디

		if (member_id == null) {
			// 로그인하지 않은 경우 로그인 페이지로 리다이렉트
			//			path = "LOGINACTION.do";
			//			flagRedirect = true;
			return "redirect:login.do";
		} else {
			// 사용자 크루 정보
			int crew_num = Integer.parseInt(login[1]);

			if (crew_num <= 0) {
				// 크루가 없는 경우 크루 목록 페이지로 리다이렉트
				//				path = "CrewListPage.do";
				//				flagRedirect = true;
				return "redirect:crewList.do";
			} else {
				int pageNum = 1; // 페이지 번호 초기화
				if (request.getParameter("page") != null) {
					// 페이지 번호가 주어지면 변환하여 저장
					//					pageNum = Integer.parseInt(request.getParameter("page"));
					System.out.println(" (CrewCommunityPageAction) pageNum = "+pageNum);
				}
				//				int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
				//				int minBoard = 1; // 최소 게시글 수 초기화
				//				int maxBoard = 1; // 최대 게시글 수 초기화
				//
				//				// 페이지 번호에 따라 최소 및 최대 게시글 수 설정
				//				if(pageNum <= 1) {
				//					// 페이지 번호가 1 이하일 경우
				//					minBoard = 1; // 최소 게시글 번호를 1로 설정
				//					maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
				//				}
				//				else {
				//					// 페이지 번호가 2 이상일 경우
				//					minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
				//					maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
				//				}

				pageNum = crew_boardDTO.getPage();//요거 필요
				int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
				int minBoard = 1; // 최소 게시글 수 초기화

				minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
				int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

				//				Crew_boardDTO crew_boardDTO = new Crew_boardDTO();
				//				Crew_boardDAO crew_boardDAO = new Crew_boardDAO();

				crew_boardDTO.setCrew_board_min_num(minBoard);
				//				crew_boardDTO.setModel_crew_board_max_num(maxBoard);
				//				crew_boardDTO.setModel_crew_board_writer_id(member_id);

				//줄바꿈 처리
				String br_content = "";
				// 게시글 목록 가져오기
//                crew_boardDTO.setCrew_board_condition("CREW_BOARD_ALL_CREW_BOARD");

				List<Crew_boardDTO> crew_board_datas = crew_boardService.selectAllCrewBoard(crew_boardDTO);
				for(int i=0;i<crew_board_datas.size();i++) {
					System.out.println(crew_board_datas.get(i));
					br_content = crew_board_datas.get(i).getCrew_board_content();
					br_content = br_content.replace("\n", "<br>");
					System.err.println("줄바꿈 적용 내용 = "+br_content);
					crew_board_datas.get(i).setCrew_board_content(br_content);
				}
//				 프로필 이미지 URL 설정


				// 총 게시글 수 확인
				//				Crew_boardDTO crew_board_count = new Crew_boardDTO();
				//				crew_board_count.setModel_crew_board_writer_id(member_id);

//                crew_boardDTO.setCrew_board_condition("CREW_BOARD_ONE_COUNT");
				Crew_boardDTO crew_board_count = crew_boardService.selectOneCount(crew_boardDTO);
				listNum = crew_board_count.getCrew_board_total();

				//				request.setAttribute("model_crew_board_datas", model_crew_board_datas);
				//				request.setAttribute("totalCount", listNum);
				//				request.setAttribute("currentPage", pageNum);

				model.addAttribute("crew_board_datas", crew_board_datas);
				model.addAttribute("totalCount", listNum);
				model.addAttribute("currentPage", pageNum);

				//				System.out.println("totalCount = "+listNum);
				//				System.out.println("currentPage = "+pageNum);
			}
		}

		//		forward.setPath(path); // 페이지 설정
		//		forward.setRedirect(flagRedirect); // 페이지 이동 방식 설정
		return "views/crewCommunity";
	}


}
