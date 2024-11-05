package com.coma.app.view.gym;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.favorite.FavoriteDTO;
import com.coma.app.biz.favorite.FavoriteService;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class GymController {
	@Autowired
	private GymService gymService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private Battle_recordService battle_recordService;
	@Autowired
	private HttpSession session;
	@Autowired
	private ServletContext servletContext;

	@LoginCheck
	@GetMapping("/gymReservation.do")
	public String gymReservation() {
		return "views/reservation";
	}


	@GetMapping("/gymMain.do")
	public String gymMain(GymDTO gymDTO, Model model) {
		log.info("gymMain.do 도착");
		//boolean flag_Redirect = false; // 값을 전달해야하게 때문에 forward 방식으로 전달해야한다.
		//---------------------------------------------------------------------------
		//해당 페이지에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (페이지 번호)변수
		//---------------------------------------------------------------------------
		//페이지 네이션을 위해 암벽장 전체 개수를 요청 selectOne
		//페이지 네이션을 위한 페이지 개수를 구하는 로직을 구현

		int page = gymDTO.getPage();
		log.info("page = [{}]", page);

		int size = 10; // 한 페이지에 표시할 게시글 수
		if (page <= 0) { // 페이지가 0일 때 (npe방지)
			page = 1;
		}
		int min_num = (page - 1) * size;
		log.info("min_num = [{}]", min_num);

		gymDTO.setGym_min_num(min_num);

		//암벽장 총 개수를 요청 selectOne
		GymDTO gym_total = this.gymService.selectOneCount(gymDTO);
		log.info("gym_total = [{}]", gym_total);

		//암벽장 리스트를 model에 요청 selectAll
		//암벽장 테이블에서 받을 값(암벽장 번호 / 암벽장 이름 / 암벽장 주소)
		List<GymDTO> gym_datas = this.gymService.selectAll(gymDTO);
		log.info("gym_datas =[{}]", gym_datas);
		//------------------------------------------------------------
		//지도 API를 사용하기 위해 json 형식으로 보내는 로직
		String json = "[";
		//Gym_datas 비어 있지 않다면
		if(!gym_datas.isEmpty()) {
			for (GymDTO data_gym : gym_datas) {
				// datas(크롤링한 암벽장 데이터)만큼 암벽장 이름과
				json += "{\"title\":\"" + data_gym.getGym_name() + "\",";
				// 암벽장 장소를 json 형식으로 쓰여져있는 String 값으로 추가해서
				json += "\"address\":\"" + data_gym.getGym_location() + "\"},";
			}
			// 끝에있는 쉼표 제거한 뒤 저장
			json = json.substring(0, json.lastIndexOf(",")); 
		}
		json+="]";
		log.info("json = [{}]", json);
		//------------------------------------------------------------
		//암벽장 리스트를 View로 전달
		model.addAttribute("gym_datas", gym_datas);
		//암벽장 전체 개수를 View로 전달
		//FIXME V에서 앞에 model 빼야지 작동함
		model.addAttribute("total", gym_total.getTotal());
		log.info("total = [{}]", gym_total.getTotal());
		//암벽장 페이지 페이지 번호를 전달.
		model.addAttribute("page", page);
		//json 형식 데이터 전송
		model.addAttribute("datas", json);
		return "views/gymMain";
	}


	@PostMapping("/gymReservation.do")
	public String gymReservation(GymDTO gymDTO, ReservationDTO reservationDTO, MemberDTO memberDTO, Model model) {
		log.info("gymReservation.do 도착");
		String path = "views/info"; // view에서 알려줄 예정 alert 창 띄우기 위한 JavaScript 페이지
		//로그인 정보가 있는지 확인해주고
//		String member_id = (String) session.getAttribute("MEMBER_ID");

		//------------------------------------------------------------
		//해당 기능에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호 / 예약일 / 사용한 포인트 / 암벽장 가격)변수
		//FIXME usepoint 확인
		int reservation_use_point = reservationDTO.getReservation_use_point();
		//예약금액 변수
		int reservation_price = 0;
		//View에서 이동할 페이지 변수
		//TODO 확인 V에서 GYMNUM 확인
		String view_path = "gymInfo.do?gym_num="+reservationDTO.getReservation_gym_num();
		//------------------------------------------------------------
		//사용자가 해당 암벽장에 예약한 정보가 있는지 확인하기 위한 로직 시작
		//(예약일 / 암벽장번호 / login) 정보를 ReservationDTO에 추가합니다.
		ReservationDTO reservation_Check = this.reservationService.selectOne(reservationDTO);
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
		//model 에 selectOne으로 암벽장 가격을 가져옵니다.
		int gym_price = this.gymService.selectOne(gymDTO).getGym_price();
		//사용자가 최대 Point 보다 많이 입력했다면 최대 포인트로 고정합니다.
		int max_Point = 5000;
		if(reservation_use_point > max_Point) {
			//사용자 포인트를 강제로 5000으로 고정합니다.
			reservation_use_point = max_Point;
		}
		//(사용자 아이디)을 MemberDTO에 추가합니다.
		//사용자의 현재 포인트를 SelectOne으로 요청하고
		MemberDTO member_point = this.memberService.selectOneSearchId(memberDTO);
		//해당 사용자의 현재 포인트 - 사용 포인트를 use_Point 변수에 추가
		int use_Point = member_point.getMember_current_point() - reservation_use_point;
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
		//member update 로 사용자 포인트를 변경합니다.
		boolean flag_point_update = this.memberService.updateCurrentPoint(memberDTO);
		if(!flag_point_update) {
			//문제가 발생했다는 문구를 띄워 줍니다.
			model.addAttribute("msg", "예약 진행중 오류가 발생하였습니다. (사유 : 사용자 포인트 변경오류)");
			model.addAttribute("path", view_path);
			return path;
		}

		//예약금액 = 암벽장금액 - 사용 포인트
		reservation_price = gym_price - reservation_use_point;

		//만약 받아온 금액과 다시 계산된 금액이 다르다면
		if(reservationDTO.getReservation_price() != reservation_price) {
			System.out.println("(GymController.java) Controller에서 계산된 예약금 로그 : "+reservationDTO.getReservation_price());
			System.out.println("(GymController.java) View에서 보내준 예약금 로그 : "+reservation_price);
			//문제가 발생했다는 문구를 띄워 줍니다.
			model.addAttribute("msg", "예약 불가 (사유 : 금액이 변경됨)");
			model.addAttribute("path", view_path);
			return path;
		}
		//예약 정보가 정상문제 없는지 확인하는 로직 종료
		//------------------------------------------------------------
		//예약 정보 저장 하기 위한 로직 시작
		//(암벽장 번호 / 예약일 / 예약금액)을 ReservationDTO에 추가합니다.
		//model 에 Reservation 테이블에 Insert 해줍니다.
		boolean flag = this.reservationService.insert(reservationDTO);
		//저장 여부에 따른 값 전달
		//True == error_message  : 예약에 성공했습니다.
		if(flag) {
			model.addAttribute("msg","예약에 성공했습니다.");
		}
		else {
			model.addAttribute("msg","예약에 실패했습니다.");
		}
		model.addAttribute("path", view_path);
		//예약 정보 저장 하기 위한 로직 종료
		//------------------------------------------------------------
		return path;
	}


	@LoginCheck
	@PostMapping("/gymReservationInfo.do")
	public String gymReservationInfo(Model model,GymDTO gymDTO, MemberDTO memberDTO, ReservationDTO reservationDTO) {
		log.info("gymReservationInfo.do 도착");
		String error_path = "views/info";

		String member_id = (String) session.getAttribute("MEMBER_ID");

		//FIXME reservation_date M에서
		String gym_reservation_date = reservationDTO.getReservation_date();
		log.info("gym_reservation_date = [{}]", gym_reservation_date);
		//------------------------------------------------------------
		//해당 기능에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호 / 예약일 / 사용한 포인트 / 암벽장 가격)변수
		int gym_num = gymDTO.getGym_num();
		log.info("gym_num = [{}]", gym_num);

//		System.err.println("사용포인트 넘어왔니"+memberDTO.getMember_use_point);
//		int reservation_use_point=0;
//		if(memberDTO.getMember_use_point!=null) {
//			reservation_use_point = memberDTO.getMember_use_point;
//		}
		log.info("reservation_use_point = [{}]", reservationDTO.getReservation_use_point());
		int reservation_use_point=reservationDTO.getReservation_use_point();
//		if(memberDTO.getMember_use_point!=null) {
//			reservation_use_point = memberDTO.getMember_use_point;
//		}
		//TODO gym_price Int 변환 확인하기
		int gym_price = gymDTO.getGym_price();
		//최대 사용 포인트
		int max_Point = 5000;
		//예약금액 변수
		int reservation_price = 0;
		//예약 가능 인원
		int reservation_cnt = 0;
		//사용자 아이디
		String member_name = null;
		//View에서 이동할 페이지 변수
		String view_path = "gymInfo.do?gym_num="+gymDTO.getGym_num();
		model.addAttribute("msg", "예약 되었습니다!");
		//------------------------------------------------------------

		if(member_id != null) {
			System.out.println("member_id 있음");
			//------------------------------------------------------------
			//사용 포인트 변경하는 로직 시작
			//사용자가 최대 포인트 보다 많이 입력했다면
			if(reservation_use_point > max_Point) {
				//사용자 포인트를 강제로 5000으로 고정합니다.
				reservation_use_point = max_Point;
			}
			//(사용자 아이디)을 MemberDTO에 추가합니다.
			memberDTO.setMember_id(member_id);
			//TODO 사용자의 현재 포인트를 SelectOne으로 요청하고
			MemberDTO member_point = this.memberService.selectOneSearchId(memberDTO);
			//TODO 해당 사용자의 현재 포인트 - 사용 포인트를 use_Point 변수에 추가

			int use_Point = member_point.getMember_current_point() - reservation_use_point;
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
				reservation_price = gym_price - reservation_use_point;
			}
			//사용 포인트 변경하는 로직 종료
			//------------------------------------------------------------
			//예약 가능 인원 수 구하는 로직 시작
			//암벽장 번호를 Gym DTO 에 입력하여 암벽장 정보를 요청합니다.
			gymDTO.setGym_num(gym_num);
			//해당 암벽장 정보의 예약 최대 인원을 요청합니다.
			int reservation_total_cnt = this.gymService.selectOne(gymDTO).getGym_reservation_cnt();
			//암벽장 번호와 예약 날짜를 Reservation DTO 에 추가해줍니다.
			reservationDTO.setReservation_gym_num(gym_num);
			reservationDTO.setReservation_date(gym_reservation_date);
			//model 에 selectOne 을 요청하여 현재 예약한 인원을 요청합니다.
			int reservation_current_cnt = this.reservationService.selectOneCount(reservationDTO).getTotal();
			//예약 인원이 resrvation_cnt = resrvation_total_cnt - resrvation_current_cnt
			reservation_cnt = reservation_total_cnt - reservation_current_cnt;
			//만약 0보다 작다면
			if(reservation_cnt <= 0) {
				//error_message : 예약이 불가능한 날짜입니다.
				model.addAttribute("msg", "예약인원이 불가능한 날짜입니다.");
				//path : 암벽장 페이지
				model.addAttribute("path", view_path); //TODO 암벽장 페이지 작성해야함
				return error_path;
			}
			//예약 가능 인원 수 구하는 로직 종료
			//------------------------------------------------------------
			//사용자 이름 구하는 로직 시작
			//사용자 아이디를 Member DTO에 추가합니다.
			//model 에 selectOne으로 사용자 이름을 요청합니다.
			member_name = this.memberService.selectOneSearchId(memberDTO).getMember_name();

			//사용자 이름 구하는 로직 종료
			//------------------------------------------------------------
		}
		else {
			System.out.println("member_id 없음");
			//error_message : 예약이 불가능한 날짜입니다.
			model.addAttribute("msg", "로그인 후 사용 가능합니다.");
			//path : 암벽장 페이지
			model.addAttribute("path", "login.do"); //TODO 암벽장 페이지 작성해야함
			return error_path;
		}
		//FIXME V에서 값 맞는지 확인하기
		model.addAttribute("gym_num", gym_num);
		model.addAttribute("member_name", member_name);
		model.addAttribute("reservation_date", gym_reservation_date);
		model.addAttribute("reservation_cnt", reservation_cnt);
		model.addAttribute("reservation_price", reservation_price);
		model.addAttribute("reservation_use_point", reservation_use_point);
		return "views/reservation";
	}

	@GetMapping("/gymInfo.do")
	public String gymInfo(Model model, GymDTO gymDTO, Battle_recordDTO battle_recordDTO, MemberDTO memberDTO, FavoriteDTO favoriteDTO) {
		String member_id = (String) session.getAttribute("MEMBER_ID");
		//---------------------------------------------------------------------------
		//해당 페이지에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호)변수
		String favorite = "delete"; //찜목록 데이터 초기화 없다면 delete 있다면 insert		
		//암벽장 정보 변수 및 객체
		int gym_num = 0;
		String gym_name="";
		String gym_profile="";
		String gym_description="";
		String gym_location="";
		int gym_price=0;
		//해당 암벽장의 승리 크루전 객체
		//크루전 정보 변수 및 객체
		int Gym_battle_num;
		String Gym_battle_game_date;
		//---------------------------------------------------------------------------
		//암벽장 정보 로직 시작
		//View에서 전달해준 암벽장 번호를 gym DTO에 저장하고
		System.out.println("암벽장 PK : "+ gym_num);
		//gym selectOne으로 Model에 암벽장정보를 요청합니다.
		//데이터 : 암벽장 번호 / 암벽장 이름 / 암벽장 사진 / 암벽장 설명 / 암벽장 주소 / 암벽장 가격
		GymDTO data = this.gymService.selectOne(gymDTO);
		gym_num = data.getGym_num();
		gym_name = data.getGym_name();
		gym_profile = data.getGym_profile();
		if(gym_profile != null){
			if(!data.getGym_profile().contains("http")){
				gym_profile = "https://" + gym_profile;
			}
		}
		gym_description = data.getGym_description();
		gym_location = data.getGym_location();
		gym_price = data.getGym_price();

		//암벽장 정보 로직 종료
		//---------------------------------------------------------------------------
		//해당 암벽장에서 승리한 크루 목록 로직 시작
		//View에서 전달해준 암벽장 번호를 battle_record DTO에 저장하고
		battle_recordDTO.setBattle_record_gym_num(gym_num);
		//battle_record selectAll으로 Model에 해당 암벽장에서 승리한 크루 목록을 요청하고
		//데이터 : 승리크루 이름 / 승리크루 사진 / 승리크루 경기날짜 / MVP 이름
		List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinnerParticipantGym(battle_recordDTO);

		//해당 암벽장에서 승리한 크루 목록 로직 종료
		//---------------------------------------------------------------------------
		//해당 암벽장에 등록되어 있는 크루전 정보 로직 시작
		// View에서 전달해준 암벽장 번호를 DTO에 저장하고
		gymDTO.setGym_num(gym_num);
		// Battle selectOne으로 Model에 해당 암벽장에서 크루전 정보 요청
		//데이터 : 크루전 번호 / 크루전 날짜
		GymDTO gym_data = this.gymService.selectOne(gymDTO);
		Gym_battle_num = gym_data.getGym_battle_num();
		Gym_battle_game_date = gym_data.getGym_battle_game_date();
		//해당 암벽장에 등록되어 있는 크루전 정보 로직 종료
		//---------------------------------------------------------------------------
		//로그인한 사용자라면
		if(member_id != null) {
			//사용자 포인트 요청 로직 시작
			//사용자 아이디를 Member DTO에 저장하고
			memberDTO.setMember_id(member_id);
			//Member selectOne으로 Model에 해당 사용자의 사용가능 포인트요청
			MemberDTO member_data = this.memberService.selectOneSearchId(memberDTO);
			int member_current_point = 0 ;
			//데이터 : 사용가능 포인트
			if(member_data != null) {
				member_current_point = member_data.getMember_current_point();
				//View로 사용 가능 포인트 전달
				//FIXME V에서 앞에 Gym 빼야지 작동함
				model.addAttribute("gym_member_current_point", member_current_point);
			}

			//사용자 포인트 요청 로직 종료
			//---------------------------------------------------------------------------
			//좋아요 여부 로직 시작
			//View에서 전달해준 암벽장 번호와 사용자 아이디를 favorite DTO에 저장하고

			favoriteDTO.setFavorite_gym_num(gym_num);
			favoriteDTO.setFavorite_member_id(member_id);
			//Favorite selectOne으로 Model에 좋아요 여부를 요청합니다.
			FavoriteDTO favorite_null_check = this.favoriteService.selectOne(favoriteDTO);
			//만약 없으면 좋아요 안한 사용자.	
			if(favorite_null_check != null) {
				//만약 있으면 좋아요 한 사용자.
				favorite = "insert";
				System.out.println("찜목록 if 안 : "+favorite);
			}
			System.out.println("찜목록 if 밖 : " +favorite);
			//좋아요 여부 로직 종료
		}//if(member_id != null) { 종료
		//---------------------------------------------------------------------------	
		//View로 암벽장 승리 크루 전달 model_battle_record_datas
		//FIXME V에서 앞에 model 빼야지 작동함
		model.addAttribute("battle_record_datas", battle_record_datas);
		//View로 암벽장 정보 전달 
		model.addAttribute("gym_num", gym_num);
		model.addAttribute("gym_name", gym_name);
		model.addAttribute("gym_profile", gym_profile);
		model.addAttribute("gym_description", gym_description);
		model.addAttribute("gym_location", gym_location);
		model.addAttribute("gym_price", gym_price);
		//View로 암벽장 크루전 정보 전달
		// FIXME V 확인하기
		model.addAttribute("gym_battle_num", Gym_battle_num);
		model.addAttribute("gym_battle_game_date", Gym_battle_game_date);
		//View로 좋아요 여부 전달 model_favorite
		System.out.println("GIP 167 favorite = "+favorite);
		model.addAttribute("favorite", favorite);
		return "views/gymInformation";
	}
}
