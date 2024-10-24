package com.coma.app.view.gym;

import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDAO;
import com.coma.app.biz.reservation.ReservationDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("gymReservationController")
public class GymReservationController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/GymReservationInformationPage.do")
	public String gymReservationInformationPage(HttpSession session, Model model, ReservationDTO reservationDTO, ReservationDAO reservationDAO, 
			GymDTO gymDTO, GymDAO gymDAO, MemberDTO memberDTO, MemberDAO memberDAO) {
		String path = "reservation"; // view에서 알려줄 예정
		String error_path = "info";

		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");
		//가입 크루 번호
		//String crew_check = login[1];

		//------------------------------------------------------------
		//해당 기능에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호 / 예약일 / 사용한 포인트 / 암벽장 가격)변수
		int view_reservation_use_point=memberDTO.getVIEW_USE_POINT();
		int gym_num = gymDTO.getGym_num();
		//최대 사용 포인트
		int max_Point = 5000;
		
		//예약금액 변수
		int reservation_price = 0;

		//예약 가능 인원
		int reservation_cnt = 0;

		//사용자 아이디
		String member_name = null;

		//View에서 이동할 페이지 변수
		String view_path = "GymInformationPage.do?gym_num="+gym_num;
		
		//------------------------------------------------------------
		if(member_id != null) {
			System.out.println("member_id 있음");
			//------------------------------------------------------------
			//사용 포인트 변경하는 로직 시작
			//사용자가 최대 포인트 보다 많이 입력했다면 
			if(view_reservation_use_point > max_Point) {
				//사용자 포인트를 강제로 5000으로 고정합니다.
				view_reservation_use_point = max_Point;
			}
			//(사용자 아이디)을 MemberDTO에 추가합니다.
			memberDTO.setMember_id(member_id);
			//memberDTO.setMember_condition("MEMBER_SEARCH_ID");
			//TODO 사용자의 현재 포인트를 SelectOne으로 요청하고
			MemberDTO member_point = memberService.selectOneSearchId(memberDTO);
			//TODO 해당 사용자의 현재 포인트 - 사용 포인트를 use_Point 변수에 추가
			int use_Point = member_point.getMember_current_point() - view_reservation_use_point;
			//use_Point 값이
			//음수 == error_message : 현재 포인트가 부족하여 예약에 실패하였습니다. (현재 포인트 : XX)
			//path				   : 암벽장 페이지	
			if(use_Point < 0) {
				model.addAttribute("msg", "포인트가 부족합니다. 포인트를 확인해주세요. \n (현재 포인트 : "+member_point.getMember_current_point()+")");
				model.addAttribute("path", view_path);
				return error_path;
			}
			//양수 : 예약금액 = 암벽장금액 - 사용 포인트
			else {
				reservation_price = gymDTO.getGym_price() - view_reservation_use_point;
			}
			//사용 포인트 변경하는 로직 종료
			//------------------------------------------------------------
			//예약 가능 인원 수 구하는 로직 시작
			//암벽장 번호를 Gym DTO 에 입력하여 암벽장 정보를 요청합니다.
			gymDTO.setGym_condition("GYM_ONE");
			//해당 암벽장 정보의 예약 최대 인원을 요청합니다.
			int reservation_total_cnt = gymDAO.selectOne(gymDTO).getGym_reservation_cnt();

			//암벽장 번호와 예약 날짜를 Reservation DTO 에 추가해줍니다.
			reservationDTO.setReservation_gym_num(gym_num);
			reservationDTO.setReservation_condition("RESERVATION_ONE_COUNT");//TODO 컨디션 추가해야함
			//model 에 selectOne 을 요청하여 현재 예약한 인원을 요청합니다.
			int reservation_current_cnt = reservationDAO.selectOne(reservationDTO).getReservation_total();
			//예약 인원이 resrvation_cnt = resrvation_total_cnt - resrvation_current_cnt
			reservation_cnt = reservation_total_cnt - reservation_current_cnt;
			//만약 0보다 작다면
			if(reservation_cnt <= 0) {
				//error_message : 예약이 불가능한 날짜입니다.
				model.addAttribute("msg", "예약인원이 불가능한 날짜입니다.");
				model.addAttribute("path", view_path);
				return error_path;
			}

			//예약 가능 인원 수 구하는 로직 종료
			//------------------------------------------------------------
			//사용자 이름 구하는 로직 시작
			//사용자 아이디를 Member DTO에 추가합니다.
			//memberDTO.setMember_condition("MEMBER_SEARCH_ID");
			memberDTO.setMember_id(member_id);
			//model 에 selectOne으로 사용자 이름을 요청합니다.
			member_name = memberService.selectOneSearchId(memberDTO).getMember_name();

			//사용자 이름 구하는 로직 종료
			//------------------------------------------------------------
		}
		else {
			System.out.println("member_id 없음");
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			model.addAttribute("path", "LOGINPAGEACTION.do");
			return error_path;
		}
		model.addAttribute("model_gym_num", gym_num);
		model.addAttribute("MEMBER_NAME", member_name);
		model.addAttribute("reservation_date", reservationDTO.getReservation_date());
		model.addAttribute("reservation_cnt", reservation_cnt);
		model.addAttribute("reservation_price", reservation_price);
		model.addAttribute("use_point", view_reservation_use_point);

		return path;
	}
	
	
	@RequestMapping("/GymReservation.do")
	public String gymReservation(HttpSession session, Model model, ReservationDTO reservationDTO, ReservationDAO reservationDAO, 
			MemberDTO memberDTO, MemberDAO memberDAO, GymDTO gymDTO, GymDAO gymDAO) {
		String path = "info"; // view에서 알려줄 예정 alert 창 띄우기 위한 JavaScript 페이지
		
        //사용자 아이디
        String member_id = (String)session.getAttribute("MEMBER_ID");
        
		//------------------------------------------------------------
		//해당 기능에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호 / 예약일 / 사용한 포인트 / 암벽장 가격)변수
		int view_reservation_gym_num = reservationDTO.getReservation_gym_num();
		int view_reservation_use_point=memberDTO.getVIEW_USE_POINT();
		int view_reservation_price = reservationDTO.getReservation_price();
		//예약금액 변수
		int reservation_price = 0;
		
		//View에서 이동할 페이지 변수
		String view_path = "GymInformationPage.do?view_gym_num="+view_reservation_gym_num;
		
		//------------------------------------------------------------
		//사용자가 해당 암벽장에 예약한 정보가 있는지 확인하기 위한 로직 시작
		//(예약일 / 암벽장번호 / login) 정보를 ReservationDTO에 추가합니다.
		reservationDTO.setReservation_member_id(member_id);
		reservationDTO.setReservation_condition("RESERVATION_ONE_SEARCH");
		//Reservation selectOne 을 요청
		ReservationDTO reservation_Check = reservationDAO.selectOne(reservationDTO);
		//요청 값이 null 이 아니라면 해당 날짜에 이미 예약되어있는 사용자 이므로
		//not null == error_message : 해당 날짜에는 이미 예약되어있습니다. (예약 번호 : Reservation PK 값)
		//path			 			: 암벽장 페이지
		if(reservation_Check != null) {
			model.addAttribute("msg", "해당 날짜에는 이미 예약되어있습니다. (예약 번호 : "+reservation_Check.getReservation_num()+")");
			model.addAttribute("path", view_path);
			return path;
		}
		
		//사용자가 해당 암벽장에 예약한 정보가 있는지 확인하기 위한 로직 종료		
		//------------------------------------------------------------
		//예약 정보가 정상문제 없는지 확인하는 로직 시작
		//암벽장 번호를 Gym DTO 에 추가해줍니다.
		gymDTO.setGym_condition("GYM_ONE"); // TODO 컨디션 추가해야함
		gymDTO.setGym_num(view_reservation_gym_num);
		//model 에 selectOne으로 암벽장 가격을 가져옵니다.
		int gym_price = gymDAO.selectOne(gymDTO).getGym_price();
		
		//사용자가 최대 Point 보다 많이 입력했다면 최대 포인트로 고정합니다.
		int max_Point = 5000;
		if(view_reservation_use_point > max_Point) {
			//사용자 포인트를 강제로 5000으로 고정합니다.
			view_reservation_use_point = max_Point;
		}
		
		//(사용자 아이디)을 MemberDTO에 추가합니다.
		memberDTO.setMember_id(member_id);
		//memberDTO.setMember_condition("MEMBER_SEARCH_ID");
		
		//사용자의 현재 포인트를 SelectOne으로 요청하고
		MemberDTO member_point = memberService.selectOneSearchId(memberDTO);
		
		//해당 사용자의 현재 포인트 - 사용 포인트를 use_Point 변수에 추가
		int use_Point = member_point.getMember_current_point() - view_reservation_use_point;
		
		//사용자 남은 포인트 로그
		System.out.println("사용자 남은 포인트 : "+use_Point);
		
		//use_Point 값이
		//음수 == error_message : 현재 포인트가 부족하여 예약에 실패하였습니다. (현재 포인트 : XX)
		//path				   : 암벽장 페이지	
		if(use_Point < 0) {
			model.addAttribute("msg", "포인트가 부족합니다. 포인트를 확인해주세요. \n (현재 포인트 : "+member_point.getMember_current_point()+")");
			model.addAttribute("path", view_path);
			return path;
		}
		
		//사용자 포인트에 문제가 없다면
		//DTO 에 남은 사용자 포인트를 추가하고
		memberDTO.setMember_current_point(use_Point);
		//memberDTO.setMember_condition("MEMBER_UPDATE_CURRENT_POINT");
		//member update 로 사용자 포인트를 변경합니다.
		boolean flag_point_update = memberService.updateCurrentPoint(memberDTO);
		if(!flag_point_update) {
			//문제가 발생했다는 문구를 띄워 줍니다.
			model.addAttribute("msg", "예약 진행중 오류가 발생하였습니다. (사유 : 사용자 포인트 변경오류)");
			model.addAttribute("path", view_path);	
			return path;
		}
		
		//예약금액 = 암벽장금액 - 사용 포인트
		reservation_price = gym_price - view_reservation_use_point;			
		
		//만약 받아온 금액과 다시 계산된 금액이 다르다면 
		if(view_reservation_price != reservation_price) {
			System.out.println("(GymReservationAction.java) Controller에서 계산된 예약금 로그 : "+view_reservation_price);
			System.out.println("(GymReservationAction.java) View에서 보내준 예약금 로그 : "+reservation_price);
			//문제가 발생했다는 문구를 띄워 줍니다.
			model.addAttribute("msg", "예약 불가 (사유 : 금액이 변경됨)");
			model.addAttribute("path", view_path);	
			return path;
		}
		
		//예약 정보가 정상문제 없는지 확인하는 로직 종료
		//------------------------------------------------------------
		//예약 정보 저장 하기 위한 로직 시작
		//(암벽장 번호 / 예약일 / 예약금액)을 ReservationDTO에 추가합니다.
		System.out.println("(GymReservationAction.java) 사용자 예약 암벽장 사람 로그 : "+member_id);
		reservationDTO.setReservation_member_id(member_id);
		System.out.println("(GymReservationAction.java) 사용자 예약 암벽장 번호 로그 : "+view_reservation_gym_num);
		reservationDTO.setReservation_gym_num(view_reservation_gym_num);
		System.out.println("(GymReservationAction.java) 사용자 예약 암벽장 가격 로그 : "+reservation_price);
		reservationDTO.setReservation_price(reservation_price);
		
		//model 에 Reservation 테이블에 Insert 해줍니다.
		boolean flag = reservationDAO.insert(reservationDTO);
		//저장 여부에 따른 값 전달
		//True == error_message  : 예약에 성공하였습니다.
		if(flag) {
			model.addAttribute("msg", "예약에 성공하였습니다.");
		}
		//false == error_message : 예약에 실패하였습니다.
		//path					 : 암벽장 페이지
		else {
			model.addAttribute("msg", "예약에 실패하였습니다.");
		}
		model.addAttribute("path", view_path);
		//예약 정보 저장 하기 위한 로직 종료
		//------------------------------------------------------------
		return path;
	}

}
