package com.coma.app.view.gym;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;

@Controller("gymMain")
public class GymMainPageAction{
	
	@Autowired
	private GymDAO gymDAO;
	
	@RequestMapping("/GYMMAINPAGEACTION.do")
	public String gymMain(Model model, GymDTO gymDTO) {
		
		
		//---------------------------------------------------------------------------
		//해당 페이지에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (페이지 번호)변수
		
		// int타입은 null 체크를 할 수 없으므로 Integer타입으로 저장
		
		//---------------------------------------------------------------------------
		//페이지 네이션을 위해 암벽장 전체 개수를 요청 selectOne
		//페이지 네이션을 위한 페이지 개수를 구하는 로직을 구현
		int page_num = gymDTO.getPage(); // page_num 초기 변수 지정
		
		if(page_num<1) {
			page_num=1;
		}
		
		int gym_size = 6; // 한 페이지에 표시할 게시글 수 설정
		int min_gym = 1; // 최소 게시글 수 초기화

		// 페이지 번호에 따라 최소 및 최대 게시글 수 설정
		if(page_num <= 1) {
			// 페이지 번호가 1 이하일 경우
			min_gym = 1; // 최소 게시글 번호를 1로 설정
		}
		else {
			// 페이지 번호가 2 이상일 경우
			min_gym = ((page_num - 1) * gym_size) + 1; // 최소 게시글 번호 계산
			
		}
		//페이지네이션 값과 condition 값을 DTO에 추가하여 (6개출력)
		gymDTO.setGym_min_num(min_gym);
		gymDTO.setGym_condition("GYM_ONE_COUNT"); //컨디션 추가해야함

		//암벽장 총 개수를 요청 selectOne
		int Gym_total = this.gymDAO.selectOne(gymDTO).getGym_total();
		
		System.out.println("min_gym"+min_gym);
		
		//암벽장 리스트를 model에 요청 selectAll
		//암벽장 테이블에서 받을 값(암벽장 번호 / 암벽장 이름 / 암벽장 주소)
		List<GymDTO> Gym_datas = this.gymDAO.selectAll(gymDTO);
		System.out.println(Gym_datas);
		//------------------------------------------------------------
		//지도 API를 사용하기 위해 json 형식으로 보내는 로직
		String json = "[";
		//Gym_datas 비어 있지 않다면
		if(!Gym_datas.isEmpty()) {
			for (GymDTO data_gym : Gym_datas) {
				// datas(크롤링한 암벽장 데이터)만큼 암벽장 이름과
				json += "{\"title\":\"" + data_gym.getGym_name() + "\",";
				// 암벽장 장소를 json 형식으로 쓰여져있는 String 값으로 추가해서
				json += "\"address\":\"" + data_gym.getGym_location() + "\"},";
			}
			// 끝에있는 쉼표 제거한 뒤 저장
			json = json.substring(0, json.lastIndexOf(",")); 
		}
		json+="]";
		System.out.println(json);
		//------------------------------------------------------------

		//암벽장 리스트를 View로 전달
		model.addAttribute("Gym_datas", Gym_datas);
		
		//암벽장 전체 개수를 View로 전달
		model.addAttribute("Gym_total", Gym_total);
		
		//json 형식 데이터 전송
		model.addAttribute("datas", json);
		
		//암벽장 페이지 페이지 번호를 전달.
		model.addAttribute("page_num", page_num);
		
		return "gymMain";
	}

}
