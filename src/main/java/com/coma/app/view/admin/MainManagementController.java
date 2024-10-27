package com.coma.app.view.admin;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainManagementController{

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

    // 관리자 메인
    @LoginCheck
    @GetMapping("/mainManagement.do")
    public String mainManagement(Model model, HttpSession session, MemberDTO memberDTO,
                                 GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {
        // 세션에서 관리자 ID 가져오기
        String member_id = (String) session.getAttribute("MEMBER_ID");

        // 차트js
        // 통계 데이터 가져오기

        // !★!★!★!★!★!★!★!★ TODO Impl (컨디션값) 참고하기!★!★!★!★!★!★!★!★!★!★!★!★
        //		사용자- 관리자  count*
		MemberDTO total_member = this.memberService.selectOneCountAdmin(memberDTO);
        //		암벽장 count*
		GymDTO total_gym = this.gymService.selectOneCount(gymDTO);
        //		예약 수 count*
		ReservationDTO total_reservation = this.reservationService.selectOneCountYearAdmin(reservationDTO);
        //		게시판 수 count(*)
		BoardDTO total_board = this.boardService.selectOneCount(boardDTO);
        //		크루전 수 c*
		BattleDTO total_battle = this.battleService.selectOneCountActive(battleDTO);

        //		월별 가입자 수 count(꺾은선그래프)
		List <MemberDTO> monthly_join_datas = this.memberService.selectAllMonthCountAdmin(memberDTO);
        //		월별 예약 수 count (막대그래프)
		List <ReservationDTO> monthly_reservation_datas = this.reservationService.selectAllCountMonthAdmin(reservationDTO);
        //		지역별 암벽장 수 count(동그라미)
		List <GymDTO> region_gym_datas = this.gymService.selectAllLocationCountAdmin(gymDTO);


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!TODO MODEL이 끝나면 주석풀기
        //		최신글 5개  제목 + 내용만 대시보드
//		List<BoardDTO> board_datas = this.boardService.selectAllBoardAllTop5(boardDTO);
        //		최신 크루전 5개 (개최일 빠른순 == 내림차순) 개최일, 암벽장 이름, 참여 크루
//		List<BattleDTO> battle_datas = this.battleService.selectAllBattleAllTop5(battleDTO);


        // 모델에 데이터를 추가
        // TODO V와 값 통일시키기
        model.addAttribute("total_member", total_member);
        model.addAttribute("total_gym", total_gym);
        model.addAttribute("total_reservation", total_reservation);
        model.addAttribute("total_board", total_board);
        model.addAttribute("total_battle", total_battle);
        model.addAttribute("monthly_join_datas", monthly_join_datas);
        model.addAttribute("monthly_reservation_datas", monthly_reservation_datas);
        model.addAttribute("region_gym_datas", region_gym_datas);
        //TODO MODEL이 끝나면 주석풀기
//        model.addAttribute("board_datas", board_datas);
//        model.addAttribute("battle_datas", battle_datas);

        //

        return "admin/main";
    }


}
