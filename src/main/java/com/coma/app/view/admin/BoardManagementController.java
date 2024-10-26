package com.coma.app.view.admin;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class BoardAdminController {

    @GetMapping("/boardManagement.do")
    public String boardManagement() {
        return "admin/boardManagement";
    }

    @GetMapping("/boardManagementDetail.do")
    public String boardManagementDetail() {
        return "admin/boardManagementDetail";
    }

    // 게시판 관리
    public String boardManagement(Model model, MemberDTO memberDTO, GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {


        //		selectbox 지역

		/*
		DELETE  BOARD ID ..

		 */


        //TODO 일괄삭제기능도 포함
        //		이건 트랜잭션

        return null;
    }

}
