package com.coma.app.view.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle.BattleService;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.HttpSession;

@Controller
public class CrewBattleApplicationController {

	@Autowired
	private CrewService crewService;
	@Autowired
	private BattleService battleService;
	@Autowired
	private Battle_recordService battle_recordService;

	@Autowired
	private HttpSession session;

	@LoginCheck
	@PostMapping("/CrewBattleApplication.do")
	public String CrewBattleApplication(CrewDTO crewDTO, BattleDTO battleDTO, Battle_recordDTO battle_recordDTO, Model model) {
		String path = "views/info"; // view에서 알려줄 예정 alert 창 띄우기 위한 JavaScript 페이지
		
		//FIXME V 에서 받아오는 값 확인하고 수정해야하면 수정하기 ★★
		String error_path = "GymInfo.do?gym_num=" + (String) session.getAttribute("VIEW_GYM_NUM");
		String error_message = "잘못된 요청";
		
		String member_id = (String) session.getAttribute("MEMBER_ID");
		String crewCheck = (String) session.getAttribute("CREW_CHECK");
		int crew_check = Integer.parseInt(crewCheck);

		System.out.println("CrewBattleApplicationController.java crew_check 로그 : " + crew_check);
		//------------------------------------------------------------
		if(member_id != null) {
			//------------------------------------------------------------
			//암벽장에 크루전을 신청하는 사용자가 크루장인지 확인 로직 시작
			//(사용자 아이디 / 크루 번호) 를 Crew DTO에 추가합니다.
			crewDTO.setCrew_num(crew_check);
			//Crew selectOne으로 해당 사용자가 크루장인지 확인합니다.
			CrewDTO crew_leader = this.crewService.selectOne(crewDTO);
			boolean flag_crew_leader = false;
			if(crew_leader!=null) {
				if(crew_leader.getCrew_leader().equals(member_id)){
					//해당 크루의 크루장이라면 true
					flag_crew_leader = true;
				}
			}
			System.out.println("CrewBattleApplicationController.java flag_crew_leader 로그 : " + flag_crew_leader);

			//false 가 나오면 오류를 반환해줍니다.
			if(!flag_crew_leader) {
				System.out.println("CrewBattleApplicationController.java flag_crew_leader false 로그");
				//flag_crew_leader = false : error_message 크루전은 크루장만 개최하실 수 있습니다.
				model.addAttribute("msg", "크루전은 크루장만 개최하실 수 있습니다.");
				model.addAttribute("path", error_path);
					
				return path;
			}
			//암벽장에 크루전을 신청하는 사용자가 크루장인지 확인 로직 종료
			//------------------------------------------------------------
			//크루전 개최 되어있는지 확인하기 위한 로직 시작
			//(크루전 번호) 을 Battle DTO에 추가합니다.
			//Battle selectOne으로 해당 크루전이 개최되어 있는지 확인합니다.
			BattleDTO battle_data = this.battleService.selectOneSearchBattle(battleDTO);
			boolean flag = false;
			//개최되어 있다면 게임일을 확인해줍니다.
			if(battle_data != null){
				System.out.println("CrewBattleApplicationController.java battle_data false 로그");
				flag = true;
				//만약 게임일이 없다면 : 게임일을 추가하기위해 Battle update 를 진행.
				if(battle_data.getBattle_game_date() == null) {
					flag = this.battleService.update(battleDTO);	
				}
			}			
			else if(battle_data == null) {
				//개최되어 있지 않은 크루전 번호라면
				//error_message : 크루전 개최에 실패하였습니다. (사유 : 없는 크루전)
				model.addAttribute("msg", "크루전 개최에 실패하였습니다. (사유 : 없는 크루전)");
				model.addAttribute("path", error_path);
				System.out.println("CrewBattleApplicationController.java battle_data 로그 : "+error_path);
				return path;
			}

			if(!flag) {
				//false: error_message 크루전 개최에 실패하였습니다. (사유 : 게임일 등록 실패)
				model.addAttribute("msg", "크루전 개최에 실패하였습니다. (사유 : 게임일 등록 실패)");
				model.addAttribute("path", error_path);
				return path;
			}
			//크루전 개최 되어있는지 확인하기 위한 로직 종료
			//------------------------------------------------------------
			//크루전 등록 로직 시작
			//(크루전 번호 / 크루 번호) 를 Battle_record DTO에 추가합니다.
			battle_recordDTO.setBattle_record_crew_num(crew_check);

			//크루전 등록 여부 확인을 위해 selectOne 해서 비교한다.
			Battle_recordDTO battle_record_data=this.battle_recordService.selectOneBattleRecord(battle_recordDTO);
			if(battle_record_data!=null) {
				//false: error_message 크루전을 이미 등록했습니다. (사유 : 크루전 등록 중복)
				model.addAttribute("msg", "크루전을 이미 등록했습니다. (사유 : 크루전 등록 중복)");
				model.addAttribute("path", error_path);
					
				return path;
			}
			//model 의 Battle_record 에 Insert 해줍니다.
			boolean flag_battle_record = this.battle_recordService.insert(battle_recordDTO);
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
		System.out.println("CrewBattleApplicationAction.java 최종 error_page 로그 : " + error_path);
		model.addAttribute("path", error_path);
		return path;
	}
}
