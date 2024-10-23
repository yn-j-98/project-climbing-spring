package com.coma.app.view.crew.battle;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.battle.BattleService;
import com.coma.app.biz.battle_record.Battle_recordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;

@Controller("crewBattleOnePageController")
public class CrewBattleOnePageController {

	@Autowired
	private BattleService battleService;
    @Autowired
    private Battle_recordServiceImpl battle_recordService;

	@RequestMapping("/CrewBattleOnePage.do")
	public String crewBattleOnePage(Model model, Battle_recordDTO battle_recordDTO, Battle_recordDAO battle_recordDAO,
			BattleDTO battleDTO, BattleDAO battleDAO) {
		String path = "crewBattleContent";

		/*
		 * 크루전 상세 페이지 이동
		 * 뷰에게서 view_battle_num을 받아옵니다.
		 * battle_record DTO와 DAO를 생성
		 * DTO에 battle_record_battle_num을 set
		 * 크루전 내용을 위해 selectOne
		 * 컨디션은 BATTLE_RECORD_ONE_BATTLE
		 * 
		 * DTO를 따로 하나 더 파서
		 * DTO에 battle_record_battle_num을 set
		 * 크루전 참가 크루를 위해 selectAll
		 * 컨디션은 BATTLE_RECORD_ALL_PARTICIPANT_CREW
		 */

		int view_battle_num =battleDTO.getBattle_num();
		System.out.println("44 CrewBattleOnePageAction view_battle_num = "+view_battle_num);

		//크루전 내용
		battle_recordDTO.setBattle_record_battle_num(view_battle_num);
		battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ONE_BATTLE");//크루전 내용 컨디션
		battle_recordDTO = battle_recordService.selectOneBattle(battle_recordDTO);
		System.out.println("51 "+battle_recordDTO);

		//참여크루 총 개수
		battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");
		battleDTO = battleService.selectOneCountActive(battleDTO);

		//참여한 크루들 정보
		battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_PARTICIPANT_CREW");
		List<Battle_recordDTO> Battle_record_datas = battle_recordService.selectAllParticipantCrew(battle_recordDTO);
		boolean flag = false; //크루전 종료 여부
		for(Battle_recordDTO data : Battle_record_datas) {
			System.out.println("61 "+data);
			if(data.getBattle_record_is_winner().equals("T")) {
				flag = true;
			}
		}
		if(flag) {
			System.err.println("경기 결과 있음");
		}
		else {
			System.err.println("경기 시작전");
		}
		
		
		model.addAttribute("Battle_record_datas", Battle_record_datas);
		model.addAttribute("Battle_record", battle_recordDTO);
		model.addAttribute("Battle_total", battleDTO);
		model.addAttribute("Battle_flag", flag);

		return path;
	}

}
