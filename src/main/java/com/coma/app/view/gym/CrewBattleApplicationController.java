package com.coma.app.view.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.crew.CrewDAO;
import com.coma.app.biz.crew.CrewDTO;

import jakarta.servlet.http.HttpSession;

@Controller("crewBattleApplicationController")
public class CrewBattleApplicationController{

	@Autowired
	private Battle_recordDAO battle_recordDAO;

	@RequestMapping("/CrewBattleApplication.do")
	public String crewBattleApplication(HttpSession session, Model model, CrewDTO crewDTO, CrewDAO crewDAO,
			BattleDTO battleDTO, BattleDAO battleDAO,
			Battle_recordDTO battle_recordDTO, Battle_recordDAO battle_recordDAO) {
		String path = "info"; // view에서 알려줄 예정 alert 창 띄우기 위한 JavaScript 페이지

		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");
		System.out.println("CrewBattleApplicationAction.java member_id 로그 : " + member_id);
		//가입 크루 번호
		int crew_check = 0;
		String crew_num = (String)session.getAttribute("CREW_NUM");
		if(crew_num != null) {
			crew_check = Integer.parseInt(crew_num);			
		}
		
		System.out.println("CrewBattleApplicationAction.java crew_check 로그 : " + crew_check);
		
		//------------------------------------------------------------
		//해당 페이지에서 공통으로 사용할 변수 and 객체
		//View에서 전달해주는 (크루전 번호 / 크루전 게임일 / 암벽장 번호)변수
		int view_gym_num = battleDTO.getBattle_gym_num();
		System.out.println("CrewBattleApplicationAction.java view_gym_num 로그 : " + view_gym_num);
		String error_message = "잘못된 요청";
		String error_page = "GymInformationPage.do?VIEW_GYM_NUM=" + view_gym_num;

		//------------------------------------------------------------

		if(member_id != null) {
			//------------------------------------------------------------
			//암벽장에 크루전을 신청하는 사용자가 크루장인지 확인 로직 시작
			//(사용자 아이디 / 크루 번호) 를 Crew DTO에 추가합니다.
			crewDTO.setCrew_num(crew_check);
			crewDTO.setCrew_condition("CREW_ONE");
			//Crew selectOne으로 해당 사용자가 크루장인지 확인합니다.
			CrewDTO crew_leader = crewDAO.selectOne(crewDTO);		
			boolean flag_crew_leader = false;
			if(crew_leader!=null) {
				if(crew_leader.getCrew_leader().equals(member_id)){
						//해당 크루의 크루장이라면 true
						flag_crew_leader = true;
				}
			}
			System.out.println("CrewBattleApplicationAction.java flag_crew_leader 로그 : " + flag_crew_leader);
			
			//false 가 나오면 오류를 반환해줍니다.
			if(!flag_crew_leader) {
				System.out.println("CrewBattleApplicationAction.java flag_crew_leader false 로그");
				//flag_crew_leader = false : error_message 크루전은 크루장만 개최하실 수 있습니다.
				model.addAttribute("msg", "크루전은 크루장만 개최하실 수 있습니다.");
				model.addAttribute("path", error_page);
				return path;
			}
			
			//암벽장에 크루전을 신청하는 사용자가 크루장인지 확인 로직 종료
			//------------------------------------------------------------
			//크루전 개최 되어있는지 확인하기 위한 로직 시작
			//(크루전 번호) 을 Battle DTO에 추가합니다.
			//battleDTO.setBattle_condition("ONE_SEARCH_BATTLE");//TODO 컨디션 추가해야함
			//Battle selectOne으로 해당 크루전이 개최되어 있는지 확인합니다.
			BattleDTO battle_data = battleDAO.selectOneSearchBattle(battleDTO);
			boolean flag = false;
			//개최되어 있다면 게임일을 확인해줍니다.
			if(battle_data != null){
				System.out.println("CrewBattleApplicationAction.java battle_data false 로그");
				flag = true;
				//만약 게임일이 없다면 : 게임일을 추가하기위해 Battle update 를 진행.
				if(battle_data.getBattle_game_date() == null) {
					flag = battleDAO.update(battleDTO);	
				}
			}			
			else if(battle_data == null) {
				//개최되어 있지 않은 크루전 번호라면
				//error_message : 크루전 개최에 실패하였습니다. (사유 : 없는 크루전)
				model.addAttribute("msg", "크루전 개최에 실패하였습니다. (사유 : 없는 크루전)");
				model.addAttribute("path", error_page);
				System.out.println("CrewBattleApplicationAction.java battle_data 로그 : "+error_page);
				return path;
			}
			
			if(!flag) {
				//false: error_message 크루전 개최에 실패하였습니다. (사유 : 게임일 등록 실패)
				model.addAttribute("msg", "크루전 개최에 실패하였습니다. (사유 : 게임일 등록 실패)");
				model.addAttribute("path", error_page);
				return path;
			}
			//크루전 개최 되어있는지 확인하기 위한 로직 종료
			//------------------------------------------------------------
			//크루전 등록 로직 시작
			//(크루전 번호 / 크루 번호) 를 Battle_record DTO에 추가합니다.
			battle_recordDTO.setBattle_record_crew_num(crew_check);
			battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ONE_BATTLE_RECORD");

			//크루전 등록 여부 확인을 위해 selectOne 해서 비교한다.
			Battle_recordDTO battle_record_data=battle_recordDAO.selectOneBattleRecord(battle_recordDTO);
			if(battle_record_data!=null) {
				//false: error_message 크루전을 이미 등록했습니다. (사유 : 크루전 등록 중복)
				model.addAttribute("msg", "크루전을 이미 등록했습니다. (사유 : 크루전 등록 중복)");
				model.addAttribute("path", error_page);
				return path;
			}
			//model 의 Battle_record 에 Insert 해줍니다.
			boolean flag_battle_record = battle_recordDAO.insert(battle_recordDTO);
			//True : error_message 크루전 등록에 성공하였습니다.
			if(flag_battle_record) {
				error_message = "크루전 등록에 성공하였습니다.";
			}
			//false: error_message 크루전 등록에 실패하였습니다.
			else {
				error_message = "크루전 등록에 실패하였습니다.";
				
			}
			//크루전 등록 로직 종료
			//------------------------------------------------------------
		}
		else {
			error_message = "로그인 후 이용 가능합니다.";
			System.out.println("로그인 여부 로그 메시지 : " + error_message);
		}
		System.out.println("CrewBattleApplicationAction.java 최종 error_message 로그 : " + error_message);
		model.addAttribute("msg", error_message);
		System.out.println("CrewBattleApplicationAction.java 최종 error_page 로그 : " + error_page);
		model.addAttribute("path", error_page);
		return path;

	}

}
