package com.coma.app.view.admin;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class GymManagementController {

    @GetMapping("/gymManagement.do")
    public String gymManagement() {
        return "admin/gymManagement";
    }


    // 암벽장 관리
    public String gymManagement(Model model, MemberDTO memberDTO, GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {


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


}
