package com.coma.app.view.admin;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserAdminController {

    @GetMapping("/userManagement.do")
    public String userManagement() {
        return "admin/userManagement";
    }

    @GetMapping("/userManagementDetail.do")
    public String userManagementDetail() {
        return "admin/userManagementDetail";
    }

    // 회원 관리 리스트
    public String userManagementList(Model model, MemberDTO memberDTO, GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {
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
    public String userManagementDetail(Model model, MemberDTO memberDTO, GymDTO gymDTO, BoardDTO boardDTO, BattleDTO battleDTO, ReservationDTO reservationDTO) {

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

}
