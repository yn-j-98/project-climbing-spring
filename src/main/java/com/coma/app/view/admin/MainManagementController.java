package com.coma.app.view.admin;

import com.coma.app.biz.admin.MainManagementService;
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
import com.coma.app.view.annotation.AdminCheck;
import com.coma.app.view.annotation.LoginCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
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
    @Autowired
    private MainManagementService mainManagementService;

    @Autowired
    private ObjectMapper objectMapper;

    // 관리자 메인
    @AdminCheck
    @LoginCheck
    @GetMapping("/mainManagement.do")
    public String mainManagement(Model model, MemberDTO memberDTO,
                                 GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) throws JsonProcessingException {

        // 차트js
        // 통계 데이터 가져오기

        // !★!★!★!★!★!★!★!★ TODO Impl (컨디션값) 참고하기!★!★!★!★!★!★!★!★!★!★!★!★
        //		사용자- 관리자  count*,암벽장 count*,예약 수 count*,게시판 수 count(*),크루전 수 c*
        String managementTotal = mainManagementService.getManagementTotalDate(memberDTO,gymDTO,reservationDTO,boardDTO,battleDTO);
        log.info("managementTotal_json [{}]",managementTotal);
        //		월별 가입자 수 count(꺾은선그래프)
		List<MemberDTO> monthly_join_datas = this.memberService.selectAllMonthCountAdmin(memberDTO);
        log.info("monthly_join_datas [{}]",monthly_join_datas);
        String monthly_join = objectMapper.writeValueAsString(monthly_join_datas);
        log.info("monthly_join [{}]",monthly_join);
        //		월별 예약 수 count (막대그래프)
		List<ReservationDTO> monthly_reservation_datas = this.reservationService.selectAllCountMonthAdmin(reservationDTO);
        log.info("monthly_reservation_datas [{}]",monthly_reservation_datas);
        String monthly_reservation = objectMapper.writeValueAsString(monthly_reservation_datas);
        log.info("monthly_reservation [{}]",monthly_reservation);
        //		지역별 암벽장 수 count(동그라미)
		List<GymDTO> region_gym_datas = this.gymService.selectAllLocationCountAdmin(gymDTO);
        log.info("region_gym_datas [{}]",region_gym_datas);
        String region_gym = objectMapper.writeValueAsString(region_gym_datas);
        log.info("region_gym [{}]",region_gym);


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!TODO MODEL이 끝나면 주석풀기
        //		최신글 5개  제목 + 내용만 대시보드
		List<BoardDTO> board_datas = this.boardService.selectAllRecentBoard5(boardDTO);
        log.info("board_datas [{}]",board_datas);
        //		최신 크루전 5개 (개최일 빠른순 == 내림차순) 개최일, 암벽장 이름, 참여 크루
		List<BattleDTO> battle_datas = this.battleService.selectAdminAll5Active(battleDTO);
        log.info("battle_datas [{}]",battle_datas);

        // 모델에 데이터를 추가
        // V와 값 통일시키기
        model.addAttribute("total_data", managementTotal);
        model.addAttribute("monthly_join_datas", monthly_join);
        model.addAttribute("monthly_reservation_datas", monthly_reservation);
        model.addAttribute("region_gym_datas", region_gym);
        model.addAttribute("board_datas", board_datas);
        model.addAttribute("battle_datas", battle_datas);

        //

        return "admin/main";
    }


}
