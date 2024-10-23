package com.coma.app.view.mypage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDAO;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("mypageController")
public class MypageController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private MemberService memberService;

	@RequestMapping("/MYPAGEPAGEACTION.do")
	public String myPage(HttpSession session, ServletContext servletContext, Model model, MemberDAO memberDAO, MemberDTO memberDTO, BoardDAO boardDAO, BoardDTO boardDTO, ReservationDTO reservationDTO, ReservationDAO reservationDAO) {
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "myPage";

		//사용자 아이디
		String member_id = (String)session.getAttribute("member_id");

		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//로그인 페이지로 전달해줍니다.
			path = "LOGINPAGEACTION.do";
		}
		else {
			//mypage 
			System.out.println("MyPage 로그인 정보 로그 : "+member_id);
			//------------------------------------------------------------------
			//내 정보 불러오는 코드 시작
			//사용자 정보 이름, 전화번호, 아이디, 프로필 이미지, 권한 전달
			memberDTO.setMember_id(member_id);
			//memberDTO.setMember_condition("MEMBER_SEARCH_ID");
			memberDTO = memberService.selectOneSearchId(memberDTO);
			String filename = "";
			//회원가입할때 무언가 문제가 발생하여 이미지가 설정되지 못했다면
			//기본 default 이미지로 설정해야한다.
			if(memberDTO == null) {
				filename = "default.jpg";
			}
			else {
				filename = memberDTO.getMember_profile();
			}

			//현재 웹 프로젝트의 경로를 반환받고 프로젝트 경로를 반환 받아옵니다.
			memberDTO.setMember_profile(servletContext.getContextPath()+ "/profile_img/" + filename);

			//사용자 정보를 MEMBERDATA에 담아서 View로 전달
			model.addAttribute("MEMBERDATA", memberDTO);

			//내 정보 불러오는 코드 종료
			//------------------------------------------------------------------

			//------------------------------------------------------------------
			//관리자 권한이 있다면 신규 등록한 아이디 출력 시작
			if(memberDTO.getMember_role().equals("T")) {
				//사용자 정보 이름, 전화번호, 아이디, 프로필 이미지, 권한 전달
				//memberDTO.setMember_condition("MEMBER_ALL_NEW");
				List<MemberDTO> member_list = memberService.selectAllNew(memberDTO);
				model.addAttribute("MEMBER_LIST", member_list);
			}
			//관리자 권한이 있다면 신규 등록한 아이디 출력 종료
			//------------------------------------------------------------------

			//사용자가 입력한 글 목록 출력 후 전달 시작
			//boardDTO.setBoard_writer_id(login); 모델에 mypage에서 쓸 컨디션 추가 부탁해야함
			boardDTO.setBoard_searchKeyword(member_id);
			//이후 구현 예정
			System.err.println("MyPagePageAction 로그 페이지 네이션 하드코딩 해둔 상태");
			boardDTO.setBoard_min_num(0);//페이지 네이션 하드코딩했음
			boardDTO.setBoard_max_num(300);//페이지 네이션 하드코딩했음
			//사용자가 입력한 글 목록 출력 후 전달 종료
			//------------------------------------------------------------------
			// 내 게시글 불러오는 코드 시작
			boardDTO.setBoard_condition("BOARD_ALL_SEARCH_MATCH_ID");
			List<BoardDTO> boardList = boardService.selectAllSearchMatchId(boardDTO);

			//내 게시글 정보를 BOARD에 담아서 View로 전달
			model.addAttribute("BOARD", boardList);
			// 내 게시글 불러오는 코드 종료
			//------------------------------------------------------------------
			//내 예약정보 불러오는 코드 시작

			//model에 reservation 테이블 정보를 요청

			//로그인 정보를 전달하기 위해 DTO에 추가
			reservationDTO.setModel_reservation_member_id(member_id);

			//요청해서 받을 값 (예약 PK번호 / 예약 암벽장 PK 번호 / 예약 암벽장 이름 / 예약 가격 / 암벽장 예약 날짜)
			ArrayList<ReservationDTO> model_Reservation_Datas = reservationDAO.selectAll(reservationDTO);

			//내 예약 정보를 model_reservation_datas 에 담아서 View로 전달
			model.addAttribute("model_reservation_datas", model_Reservation_Datas);

			//내 예약정보 불러오는 코드 종료
			//------------------------------------------------------------------
		}
		return path;
	}

	@RequestMapping("/DeleteReservation.do")
	public String execute(ReservationDAO reservationDAO, ReservationDTO reservationDTO, Model model) {
		String msg = "예약 취소 완료";
		String path = "MYPAGEPAGEACTION.do";

		boolean falg =  reservationDAO.delete(reservationDTO);

		if (!falg) {
			msg = "예약 취소 실패";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("path", path);

		return "info";
	}

	@RequestMapping("/DELETEMEMBERACTION.do")
	public String deleteMember(HttpSession session, ServletContext servletContext, Model model, MemberDTO data, MemberDAO memberDAO) {
		String path = "info";
		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");

		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//main 페이지로 전달해줍니다.
			path = "LOGINPAGEACTION.do";
		}
		else {
			//사용자 아이디를 DTO에 등록
			data.setMember_id(member_id);
			System.out.println("DeletememberAction.java 로그 : "+member_id);

			//탈퇴전 사용자의 프로필이미지를 가져오기 위해 아이디 하나 검색하는 컨디션을 추가합니다.
			//data.setMember_condition("MEMBER_SEARCH_ID");
			//탈퇴전 사용자의 프로필이미지를 가져와 줍니다.
			data = memberService.selectOneSearchId(data);

			//delete 를 성공하지 못했다면 Mypage로 보냅니다.
			boolean flag = memberDAO.delete(data);
			if(flag) {//멤버 삭제에 성공했다면 logout 페이지로 넘어갑니다.

				//탈퇴전 사용자의 프로필이미지를 변수에 추가해줍니다.
				String member_profile = data.getMember_profile();
				//사용자의 프로필이미지가 default 이미지가 아니라면 실행합니다.
				if(!member_profile.substring(0,member_profile.lastIndexOf(".")).equals("default")) {
					//받아온 프로필이미지를 삭제하기 위해 서버 주소를 추가해줍니다.
					String user_img_path = servletContext.getRealPath("/profile_img/") + member_profile;
					//파일 위치 확인용 로그
					System.out.println(user_img_path);
					//내 PC에 파일을 확인해줍니다.
					File file = new File(user_img_path);
					//파일이 있다면
					if(file.isFile()) {
						//삭제해줍니다.
						boolean flag_profile = file.delete();
						//삭제 확인용 로그
						System.out.println("프로필 삭제 여부 : "+flag_profile);
					}
				}

				System.err.println("회원탈퇴 성공 로그");
				model.addAttribute("msg", "회원 탈퇴 성공!");
				model.addAttribute("path", "LOGOUTPAGEACTION.do");
			}
			else {
				System.err.println("회원탈퇴 실패 로그");
				model.addAttribute("msg", "회원 탈퇴 실패...");
				model.addAttribute("path", "MYPAGEPAGEACTION.do");
			}
		}
		return path;
	}

}
