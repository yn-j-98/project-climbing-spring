package com.coma.app.view.mypage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.coma.app.view.asycnServlet.FTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.annotation.LoginCheck;
import com.coma.app.view.function.ProfileUpload;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class MyPageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private FTPService ftpService;
	@Autowired
	private HttpSession session;

	@LoginCheck
	@GetMapping("/myPage.do")
	public String myPage(MemberDTO memberDTO, BoardDTO boardDTO,ReservationDTO reservationDTO, Model model) throws IOException {

		// 로그인된 사용자의 마이 페이지 처리

		// 예시로 로그인한 사용자 정보를 가져와서 모델에 추가
		String member_id = (String) session.getAttribute("MEMBER_ID");
		MultipartFile file = memberDTO.getPhotoUpload();

		log.info("MyPage 로그인 정보 [{}]",member_id);
		//------------------------------------------------------------------
		//내 정보 불러오는 코드 시작
		//사용자 정보 이름, 전화번호, 아이디, 프로필 이미지, 권한 전달
		memberDTO.setMember_id(member_id);

		memberDTO = this.memberService.selectOneSearchId(memberDTO);
		//내 정보 불러오는 코드 종료
		//------------------------------------------------------------------

		//------------------------------------------------------------------
		//관리자 권한이 있다면 신규 등록한 아이디 출력 시작
		if(memberDTO.getMember_role().equals("T")) {
			//사용자 정보 이름, 전화번호, 아이디, 프로필 이미지, 권한 전달
			List<MemberDTO> member_list = this.memberService.selectAllNew(memberDTO);
			model.addAttribute("member_list", member_list);
		}
		//관리자 권한이 있다면 신규 등록한 아이디 출력 종료
		//------------------------------------------------------------------
		int page = memberDTO.getPage();
		int size = 10; // 한 페이지에 표시할 게시글 수
		if (page <= 0) { // 페이지가 0일 때 (npe방지)
			page = 1;
		}
		int min_num = (page - 1) * size;

		System.out.println("min = " + min_num);

		memberDTO.setMember_min_num(min_num);
		//사용자가 입력한 글 목록 출력 후 전달 시작
		boardDTO.setBoard_writer_id(member_id);
		//boardDTO.setSearch_keyword(member_id);
		//이후 구현 예정
		//사용자가 입력한 글 목록 출력 후 전달 종료
		//------------------------------------------------------------------
		// 내 게시글 불러오는 코드 시작
		List<BoardDTO> boardList = this.boardService.selectAllSearchMatchId(boardDTO);

		// 내 게시글 불러오는 코드 종료
		//------------------------------------------------------------------
		//내 예약정보 불러오는 코드 시작
		//model에 reservation 테이블 정보를 요청
		//로그인 정보를 전달하기 위해 DTO에 추가
		reservationDTO.setReservation_member_id(member_id);
		//요청해서 받을 값 (예약 PK번호 / 예약 암벽장 PK 번호 / 예약 암벽장 이름 / 예약 가격 / 암벽장 예약 날짜)
		List<ReservationDTO> reservation_datas = this.reservationService.selectAll(reservationDTO);
		//내 예약정보 불러오는 코드 종료
		//------------------------------------------------------------------
		//사용자 정보를 MEMBERDATA에 담아서 View로 전달
		//내 게시글 정보를 BOARD에 담아서 View로 전달
		//내 예약 정보를 model_reservation_datas 에 담아서 View로 전달


		model.addAttribute("MEMBERDATA", memberDTO);
		model.addAttribute("BOARD", boardList);
		model.addAttribute("reservation_datas", reservation_datas);

		return "views/myPage";
	}
	//-------------------------------


	// DeleteMemberAction
	@LoginCheck
	@PostMapping("/deleteMember.do")
	public String deleteMember(MemberDTO memberDTO, Model model) throws IOException {
		String member_id = (String)this.session.getAttribute("MEMBER_ID");
		memberDTO.setMember_id(member_id);
		//탈퇴전 사용자의 프로필이미지를 가져오기 위해 아이디 하나 검색하는 컨디션을 추가합니다.
		System.out.println("*******************************************"+memberDTO.getMember_id());
		//탈퇴전 사용자의 프로필이미지를 가져와 줍니다.
		memberDTO = this.memberService.selectOneSearchId(memberDTO);
		//delete 를 성공하지 못했다면 Mypage로 보냅니다.
		boolean flag = this.memberService.delete(memberDTO);
		model.addAttribute("title", "회원 탈퇴");

		String proFile = memberDTO.getMember_profile();

		if(!proFile.contains("default.png") || !proFile.contains("default.jpg")) {
			flag = ftpService.ftpFileDelete(memberDTO.getMember_profile());
		}

		//멤버 삭제에 성공했다면 logout 페이지로 넘어갑니다.
		if (flag) { // 멤버 삭제에 성공했다면 로그아웃 후 메인 페이지로 이동합니다.
			session.invalidate();
			model.addAttribute("msg", "회원 탈퇴 성공!");
			model.addAttribute("path", "main.do");
		} else {
			model.addAttribute("msg", "회원 탈퇴 실패...");
			model.addAttribute("path", "myPage.do");
		}
		return "views/info";
	}



	//DeleteReservationAction

	@GetMapping("/deleteRerservation.do")
	public String deleteReservation(ReservationDTO reservationDTO, Model model) {
		boolean flag =  this.reservationService.delete(reservationDTO);

		model.addAttribute("title", "예약 취소");
		model.addAttribute("msg", "예약 취소 완료");
		if (!flag) {
			model.addAttribute("msg","예약 취소 실패");
		}
		model.addAttribute("path", "myPage.do");
		return "views/info";
	}

	@LoginCheck
	@PostMapping("/changeMember.do")
	public String changeMember(MemberDTO memberDTO) throws IOException {

		String member_id = (String) session.getAttribute("MEMBER_ID");
		// 바꿀 사용자 아이디를 입력해줍니다.
		memberDTO.setMember_id(member_id);
		boolean flag = false;
		MultipartFile file = memberDTO.getPhotoUpload();
		// 프로필 이미지 업로드 처리
		String filename = file.getOriginalFilename();
			log.info("uploadFile : [{}]", filename);
		if ("".equals(filename)) {
			System.out.println("uploadfile null 로그");
			flag = this.memberService.updateWithoutProfile(memberDTO);
		}
		else {
			log.info("uploadFile not null Log : [{}]", filename);
			//사용자 프로필 파일 생성
			ftpService.ftpCreateFolder("profile_img",member_id,0);

			filename = ftpService.ftpProfileFileUpload(memberDTO.getPhotoUpload(), "profile_img", member_id, 0); // ftpFileUpload 주입된 인스턴스 사용
			// uploadfile이 null이 아니라면 DB의 프로필 이미지를 변경합니다.
			log.info("Update File Name log : [{}]", filename);
			memberDTO.setMember_profile(filename); // 저장한 프로필 이미지로 변경합니다.
			flag = this.memberService.updateAll(memberDTO);
		}

		log.info("프로필 이미지 저장 로그: [{}]",memberDTO);
		log.info("filename 로그 : [{}]",filename);

		// 사용자 정보를 DB에 업데이트 요청

		if (!flag) {
			session.setAttribute("CHANGE_CHECK", flag);
		}

		return "redirect:myPage.do";
	}


	//ChangeMember.do
	@LoginCheck
	@GetMapping("/changeMember.do")
	public String changeMember(MemberDTO memberDTO, Model model) throws IOException {
		String member_id = (String) session.getAttribute("MEMBER_ID");
		//사용자 아이디를 model에 전달하고
		memberDTO.setMember_id(member_id);
		//전달해준 사용자 정보를 받아와 줍니다.
		memberDTO = this.memberService.selectOneSearchId(memberDTO);
		model.addAttribute("data", memberDTO);
		return "views/editMyPage";
	}


}
