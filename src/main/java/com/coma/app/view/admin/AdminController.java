package com.coma.app.view.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle.BattleService;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private GymService gymService;
	@Autowired
	private BattleService battleService;
	
	@GetMapping("/crewManagement.do")
	public String crewManagement() {
		return "admin/crewManagement";
	}
	@GetMapping("/crewManagementDetail.do")
	public String crewManagementDetail() {
		return "admin/crewManagementDetail";
	}
	
	
	

	// 관리자 메인
	@LoginCheck
	@PostMapping("/adminMain.do")
	public String mainManagement(Model model,HttpSession session, MemberDTO memberDTO, 
			GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {
		// 세션에서 관리자 ID 가져오기
		String member_id = (String) session.getAttribute("MEMBER_ID");

		// 차트js
		// 통계 데이터 가져오기

		// !★!★!★!★!★!★!★!★ FIXME Impl (컨디션값) 참고하기!★!★!★!★!★!★!★!★!★!★!★!★
		//		사용자- 관리자  count*
//		int total_member = memberService.selectOneTotal(memberDTO);
		//		암벽장 count*
//		int total_gym = gymService.selectOneTotal(gymDTO);
		//		예약 수 count*
//		int total_reservation = reservationService.selectOneTotal(reservationDTO);
		//		게시판 수 count(*)
//		int total_board = boardService.selectOneTotal(boardDTO);
		//		크루전 수 c*
//		int total_battle = battleService.selectOneTotal(battleDTO);
		//		월별 가입자 수 count(꺾은선그래프)
//		int total_monthly_join_member = memberService.selectOneMonthlyJoin(memberDTO);
		//		월별 예약 수 count (막대그래프)
//		int total_monthly_reservation = reservationService.selectOneMonthlyReservation(reservationDTO);
		//		지역별 암벽장 수 count(동그라미)
//		int total_region_gym = gymService.selectOneTotalRegionGym(gymDTO);
		
		
		//		최신글 5개  제목 + 내용만 대시보드
//		List<BoardDTO> board_datas = boardService.selectAllNew5(boardDTO);
		//		최신 크루전 5개 (개최일 빠른순 == 내림차순) 개최일, 암벽장 이름, 참여 크루
		//	
		
		// 사용자 수 count 쿼리문
		// 월별예약 수 count 쿼리문
		// 지역별 암벽장 개수 count 쿼리문 ( 동그라미 )
		// 최신글 5개 보여주기 쿼리문
		// 최신 크루전 5개 보여주기 쿼리문

		return null;
	}

	// 회원 관리 리스트
	public String memberManagementList() {
		/*
		회원이름, 회원 아이디, 가입날짜

		회원 탈퇴 MEMBER DELETE 쿼리문

		selectbox 번호로검색, 아이디로 검색, 가입 날짜로 검색 쿼리문(concat)


		필터검색 ACTION

		페이지네이션

		 */


		return null;
	}

	// 회원 관리 -개인 상세 ( 모달 )
	public String memberManagementDetail() {
		/*
		아이디
		비밀번호
		사용자 이름
		사용자 점수
		사용자 주소
		사용자 크루
		사용자 전화번호 V에서 입력받음
		role = 사용자 or 관리자
		↑ INSERT문 추가



		role - 사용자 / 관리자 selectbox

		등록(insert)


		 */
		return null;
	}

	// 암벽장 관리
	public String gymManagement() {

		
		/*
		암벽장 관리 리스트

		암벽장 사진 
		암벽장 이름
		암벽장 주소
		암벽장 가격
		암벽장 소개
		↑ 
		SELECTALL (아마도)

		권한
		Authority t/f

		페이지네이션 
		 */


		/*
		암벽장 추가하기 모달

		암벽장 이름
		암벽장 주소
		암벽장 가격
		암벽장 소개
		암벽장 사진 (파일 업로드) 
		권한 default f 

		↑ INSERT 문 추가
		저장 / 취소 (스위트 알랏)

		동기

		 */
		return null;
	}

	// 게시판 관리
	public String boardManagement() {


		//		selectbox 지역 

		/*
		DELETE  BOARD ID ..

		 */


		//TODO 일괄삭제기능도 포함 
		//		이건 트랜잭션

		return null;
	}

	// 예약 관리
	public String reservationManagement() {

		/*
		차트js 빼기로 합의봤음 ㅎㅎ



		예약자명
		예약한 암벽장 이름
		결제한 금액
		예약한 날짜
		↑ SELECTALL

		페이지네이션


		 */

		return null;
	}
	// 크루전 관리
	public String crewBattleManagement() {
		/*
		selectbox 
			크루전 번호, 암벽장 이름, 크루전 진행 날짜


		크루전 번호
		암벽장 이름
		크루전 진행 날짜
		크루전 생성일
		상태 ( t/f )
		↑ SELECTALL


		 */

		// 크루 정보 등록 필요 nullcheck (t/f)


		return null;
	}

	public String crewBattleManagementModal() {

		//		크루전 진행한 전체 크루 사람 selectall
		//		
		//		크루전 진행한 1개 크루명 자체를 불러오기 selectall
		//		
		//		selectall* selectall 재활용(C)
		/*
		!!!!!!!!!!!!!!!!!!!!!!!비동기
		암벽장 이름
		크루전 진행 날짜
		승리 크루 크루명 (크루전 진행한 크루의 크루명 selectall 검색해야하기때문에)
		크루전 MVP
		↑ INSERT


		 */

		return null;
	}


}
