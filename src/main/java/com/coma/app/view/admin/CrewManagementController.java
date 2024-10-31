package com.coma.app.view.admin;

import com.coma.app.biz.admin.CrewManagementService;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import com.coma.app.biz.member.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class CrewManagementController {


    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private CrewManagementService crewManagementService;

    @GetMapping("/crewManagement.do")
    public String crewManagement(Model model, BattleDTO battleDTO) {
        String search_keyword = battleDTO.getSearch_keyword();
        log.info("crewManagement.search_keyword = [{}]", search_keyword);
        log.info("crewManagement.search_content = [{}]", battleDTO.getSearch_content());
        // 페이지네이션
        int page = battleDTO.getPage();
        int size = 10;
        if(page <= 0) {
            page = 1;
        }
        int min_num = (page - 1) * size;
        log.info("min_num = {}", min_num);
        battleDTO.setBattle_min_num(min_num);

        // 검색 키워드
        // 크루전 번호, 암벽장 이름, 크루전 진행 날짜, 크루전 생성일
        List<BattleDTO> datas = crewManagementService.selectAll(battleDTO);
        if(datas.isEmpty() || datas == null) {
            model.addAttribute("title", "알수 없는 오류 발생");
            model.addAttribute("msg", "");
            model.addAttribute("path", "crewManagement.do");
            return "views/info";
        }
        log.info("datas = [{}]", datas);
        model.addAttribute("datas", datas);
        model.addAttribute("page", page);
        model.addAttribute("total", datas.size());
        model.addAttribute("search_keyword", search_keyword);

        return "admin/crewManagement";
    }

    @GetMapping("/crewManagementDetail.do")
    public String crewManagementDetail(Model model, BattleDTO battleDTO) {
        log.info("crewManagementDetail.battle_num = [{}]", battleDTO.getBattle_num());
        // 크루전 정보 - 암벽장
        // 암벽장 명 , 게임일, 승리한 크루, MVP
        BattleDTO data = this.crewManagementService.selectOneSearchWinner(battleDTO);
        model.addAttribute("data", data);

        // 참여 크루
        // 크루명 / 크루장 / 크루원 수
        List<BattleDTO> datas = crewManagementService.selectAllSearchPariticipants(battleDTO);
        model.addAttribute("datas", datas);

        return "admin/crewManagementDetail";
    }



    // 크루전 관리
    @PostMapping("/crewManagement.do")
    public String crewManagementMvp(Model model, Battle_recordDTO battle_recordDTO, CrewDTO crewDTO) {
        log.info("crewManagementMvp Start");
        String infoPath = "crewManagement.do";
        String title = "크루전 정보 등록 성공";
        String msg = "크루전 등록에 성공했습니다.";

        //승리크루 크루이름을 전달해 크루 번호를 받아옵니다.
        CrewDTO battle_record_winner_crew_num = crewManagementService.battleRecord(crewDTO);
        log.info("battle_record_winner_crew_num : [{}]", battle_record_winner_crew_num);

        //받아온 mvp 이름을 추가하여 model 에 전달합니다.
        battle_recordDTO.setBattle_record_crew_num(battle_record_winner_crew_num.getCrew_num());

        //update 여부를 확인하여 view 로 정보를 전달합니다.
        if(!crewManagementService.updateBattleRecord(battle_recordDTO)){
             title = "크루전 정보 등록 실패";
             msg = "서버 오류로 크루전 등록에 실패했습니다.";
        }

        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", infoPath);

        return "views/info";
    }



    @PostMapping("/crewBattleManagementDetail.do")
    public String crewBattleManagementDetail(Model model) {

        //		크루전 진행한 전체 크루 사람 selectall
        //
        //		크루전 진행한 1개 크루명 자체를 불러오기 selectall
        //
        //		selectall* selectall 재활용(C)
		/*
		!!!!!!!!!!!!!!!!!!!!!!!비동기
		암벽장 이름
		크루전 진행 날짜
		승리 크루 크루명 (크루전 진행한 크루의 크루명 selectall 검색해야하기때문에)
		크루전 MVP
		↑ INSERT


		 */

        return null;
    }
}
