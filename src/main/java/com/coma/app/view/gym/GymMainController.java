package com.coma.app.view.gym;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.favorite.FavoriteDAO;
import com.coma.app.biz.favorite.FavoriteDTO;
import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller("gymMainController")
public class GymMainController {

	@Autowired
	private Battle_recordDAO battle_recordDAO;

	@Autowired
	private MemberService memberService;

	@RequestMapping("/GymMainPage.do")
	public String gymMain(Model model, GymDAO gymDAO, GymDTO gymDTO) {
		String path = "gymMain"; // view에서 알려줄 예정

		//---------------------------------------------------------------------------
		//페이지 네이션을 위해 암벽장 전체 개수를 요청 selectOne
		//페이지 네이션을 위한 페이지 개수를 구하는 로직을 구현
		int page_num = 1; // page_num 초기 변수 지정
		if(gymDTO != null) {
			page_num = gymDTO.getPage();
		}
		int gym_size = 5; // 한 페이지에 표시할 게시글 수 설정
		int min_gym = 1; // 최소 게시글 수 초기화
		int max_gym = 1; // 최대 게시글 수 초기화

		// 페이지 번호에 따라 최소 및 최대 게시글 수 설정
		if(page_num <= 1) {
			// 페이지 번호가 1 이하일 경우
			min_gym = 1; // 최소 게시글 번호를 1로 설정
			max_gym = min_gym * gym_size; // 최대 게시글 번호 계산
		}
		else {
			// 페이지 번호가 2 이상일 경우
			min_gym = ((page_num - 1) * gym_size) + 1; // 최소 게시글 번호 계산
			max_gym = page_num * gym_size; // 최대 게시글 번호 계산
		}
		//페이지네이션 값과 condition 값을 DTO에 추가하여 (6개출력)
		gymDTO.setModel_gym_max_num(max_gym);
		gymDTO.setModel_gym_min_num(min_gym);
		gymDTO.setModel_gym_condition("GYM_ONE_COUNT"); //컨디션 추가해야함

		//암벽장 총 개수를 요청 selectOne
		GymDTO model_gym_total = gymDAO.selectOne(gymDTO);

		System.out.println("min_gym"+min_gym);
		System.out.println("max_gym"+max_gym);
		//암벽장 리스트를 model에 요청 selectAll
		//암벽장 테이블에서 받을 값(암벽장 번호 / 암벽장 이름 / 암벽장 주소)
		ArrayList<GymDTO> model_gym_datas = gymDAO.selectAll(gymDTO);
		System.out.println(model_gym_datas);
		//------------------------------------------------------------
		//지도 API를 사용하기 위해 json 형식으로 보내는 로직
		String json = "[";
		//model_gym_datas 비어 있지 않다면
		if(!model_gym_datas.isEmpty()) {
			for (GymDTO data_gym : model_gym_datas) {
				// datas(크롤링한 암벽장 데이터)만큼 암벽장 이름과
				json += "{\"title\":\"" + data_gym.getModel_gym_name() + "\",";
				// 암벽장 장소를 json 형식으로 쓰여져있는 String 값으로 추가해서
				json += "\"address\":\"" + data_gym.getModel_gym_location() + "\"},";
			}
			// 끝에있는 쉼표 제거한 뒤 저장
			json = json.substring(0, json.lastIndexOf(",")); 
		}
		json+="]";
		System.out.println(json);
		//------------------------------------------------------------

		//암벽장 리스트를 View로 전달
		model.addAttribute("model_gym_datas", model_gym_datas);

		//암벽장 전체 개수를 View로 전달
		model.addAttribute("model_gym_total", model_gym_total.getModel_gym_total());

		//암벽장 페이지 페이지 번호를 전달.
		model.addAttribute("page_num", page_num);

		//json 형식 데이터 전송
		model.addAttribute("datas", json);

		return path;
	}


	@RequestMapping("/GymInformationPage.do")
	public String gymInformation(HttpSession session,ServletContext srServletContext, Model model, GymDTO gymDTO, GymDAO gymDAO, Battle_recordDTO battle_recordDTO, Battle_recordDAO battle_recordDAO
			, MemberDTO memberDTO, MemberDAO memberDAO, FavoriteDTO favoriteDTO, FavoriteDAO favoriteDAO) {
		String path = "gymInformation"; // view에서 알려줄 예정

		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");
		//가입 크루 번호
		//String crew_check = login[1];

		//---------------------------------------------------------------------------
		//해당 페이지에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (암벽장 번호)변수

		//암벽장 정보 변수 및 객체
		int model_gym_num = 0;
		String model_gym_name="";
		String model_gym_profile="";
		String model_gym_description="";
		String model_gym_location="";
		int model_gym_price=0;

		//크루전 정보 변수 및 객체
		int model_gym_battle_num;
		String model_gym_battle_game_date;
		//---------------------------------------------------------------------------
		//암벽장 정보 로직 시작
		//View에서 전달해준 암벽장 번호를 gym DTO에 저장하고
		System.out.println("암벽장 PK : "+ gymDTO);
		
		int gym_num = gymDTO.getModel_gym_num();
		
		gymDTO.setModel_gym_condition("GYM_ONE");//TODO 컨디션값 입력해야함

		//gym selectOne으로 Model에 암벽장정보를 요청합니다.
		//데이터 : 암벽장 번호 / 암벽장 이름 / 암벽장 사진 / 암벽장 설명 / 암벽장 주소 / 암벽장 가격
		GymDTO data = gymDAO.selectOne(gymDTO);
		model_gym_num = data.getModel_gym_num();
		model_gym_name = data.getModel_gym_name();
		model_gym_profile = "https://"+data.getModel_gym_profile();
		model_gym_description = data.getModel_gym_description();
		model_gym_location = data.getModel_gym_location();
		model_gym_price = data.getModel_gym_price();
		//암벽장 정보 로직 종료
		//---------------------------------------------------------------------------
		//해당 암벽장에서 승리한 크루 목록 로직 시작
		//View에서 전달해준 암벽장 번호를 battle_record DTO에 저장하고
		battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER_PARTICIPANT_GYM");//TODO 컨디션 추가해야함 selectAll 필요함
		battle_recordDTO.setBattle_record_gym_num(gym_num);
		//battle_record selectAll으로 Model에 해당 암벽장에서 승리한 크루 목록을 요청하고
		//데이터 : 승리크루 이름 / 승리크루 사진 / 승리크루 경기날짜 / MVP 이름
		List<Battle_recordDTO> Battle_record_datas = battle_recordDAO.selectAllWinnerParticipantGym(battle_recordDTO);

		for (Battle_recordDTO battle_record : Battle_record_datas) {
			battle_record.setBattle_record_crew_profile(srServletContext.getContextPath() + "/crew_img_folder/" + battle_record.getBattle_record_crew_profile());
		}

		//해당 암벽장에서 승리한 크루 목록 로직 종료
		//---------------------------------------------------------------------------
		//해당 암벽장에 등록되어 있는 크루전 정보 로직 시작
		// View에서 전달해준 암벽장 번호를 DTO에 저장하고
		gymDTO.setModel_gym_condition("GYM_ONE"); //TODO 컨디션 추가해야함
		// Battle selectOne으로 Model에 해당 암벽장에서 크루전 정보 요청
		//데이터 : 크루전 번호 / 크루전 날짜
		GymDTO gym_data = gymDAO.selectOne(gymDTO);
		model_gym_battle_num = gym_data.getModel_gym_battle_num();
		model_gym_battle_game_date = gym_data.getModel_gym_battle_game_date();
		//해당 암벽장에 등록되어 있는 크루전 정보 로직 종료
		//---------------------------------------------------------------------------
		//로그인한 사용자라면
		if(member_id != null) {
			//사용자 포인트 요청 로직 시작
			//사용자 아이디를 Member DTO에 저장하고
			//memberDTO.setMember_condition("MEMBER_SEARCH_ID");//TODO 컨디션 추가해야함
			memberDTO.setMember_id(member_id);
			//Member selectOne으로 Model에 해당 사용자의 사용가능 포인트요청
			MemberDTO member_data = memberService.selectOneSearchId(memberDTO);
			int current_point = 0 ;
			//데이터 : 사용가능 포인트
			if(member_data != null) {
				current_point = member_data.getMember_current_point();
				//View로 사용 가능 포인트 전달 model_gym_member_current_point
				model.addAttribute("model_gym_member_current_point", current_point);
			}

			//사용자 포인트 요청 로직 종료
			//---------------------------------------------------------------------------
			//좋아요 여부 로직 시작
			//View에서 전달해준 암벽장 번호와 사용자 아이디를 favorite DTO에 저장하고
			favoriteDTO.setFavorite_gym_num(gym_num);
			favoriteDTO.setFavorite_member_id(member_id);
			//Favorite selectOne으로 Model에 좋아요 여부를 요청합니다.
			FavoriteDTO favorite_null_check = favoriteDAO.selectOne(favoriteDTO);
			String favorite = "delete"; //찜목록 데이터 초기화 없다면 delete 있다면 insert
			//만약 없으면 좋아요 안한 사용자.	
			if(favorite_null_check != null) {
				//만약 있으면 좋아요 한 사용자.
				favorite = "insert";
				System.out.println("찜목록 if 안 : "+favorite);
			}
			System.out.println("찜목록 if 밖 : " + favorite);
			//View로 좋아요 여부 전달 model_favorite
			System.out.println("GymMainController.java favorite log = ["+favorite+"]");
			model.addAttribute("model_favorite", favorite);
			//좋아요 여부 로직 종료
		}//if(member_id != null) { 종료
		//---------------------------------------------------------------------------	
		//View로 암벽장 승리 크루 전달 Battle_record_datas
		model.addAttribute("Battle_record_datas", Battle_record_datas);
		//View로 암벽장 정보 전달 
		/*
		model_gym_num
		model_gym_name
		model_gym_profile
		model_gym_description
		model_gym_location
		model_gym_price
		 */
		model.addAttribute("model_gym_num", model_gym_num);
		model.addAttribute("model_gym_name", model_gym_name);
		model.addAttribute("model_gym_profile", model_gym_profile);
		model.addAttribute("model_gym_description", model_gym_description);
		model.addAttribute("model_gym_location", model_gym_location);
		model.addAttribute("model_gym_price", model_gym_price);

		//View로 암벽장 크루전 정보 전달
		/*
		Battle_num
		Battle_game_date
		 */
		model.addAttribute("Battle_num", model_gym_battle_num);
		model.addAttribute("model_gym_battle_game_date", model_gym_battle_game_date);

		return path;
	}

}
