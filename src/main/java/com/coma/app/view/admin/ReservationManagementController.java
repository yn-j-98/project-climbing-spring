package com.coma.app.view.admin;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ReservationAdminController {

    @GetMapping("/reservationManagement.do")
    public String reservationManagement() {
        return "admin/reservationManagement";
    }

    // 예약 관리
    public String reservationManagement(Model model, MemberDTO memberDTO, GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {

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


}
