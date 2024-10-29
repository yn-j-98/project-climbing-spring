package com.coma.app.view.admin;

import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
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
    private CrewService crewService;

    @GetMapping("/crewManagement.do")
    public String crewManagement(Model model, Battle_recordDTO battle_recordDTO) {

        // TODO MODEL 끝나면 주석풀기
        // 페이지네이션
//        int page = battle_recordDTO.getPage();
//        int size = 10;
//        if(page <= 0) {
//            page = 1;
//        }
//        int min_num = (page - 1) * size;
//
//        log.info("min_num = {}", min_num);
//
//        battle_recordDTO.setBattle_record_min_num(min_num);
//
//        // 필터검색
//        // 크루전 번호, 암벽장 이름, 크루전 진행 날짜, 크루전 생성일
//
//        List<Battle_recordDTO> datas = null;
//
//        String search_keyword = battle_recordDTO.getSearch_keyword();
//
//        // 크루전 번호로 검색
//        if(search_keyword.equals("crew_battle_id")) {
//            datas = this.battle_recordService.selectAllBattleNum(battle_recordDTO);
//        }
//        // 암벽장 이름으로 검색
//        else if(search_keyword.equals("gym_name")) {
//            datas = this.battle_recordService.selectAllGymName(battle_recordDTO);
//        }
//        else {
//            model.addAttribute("title","Server Error");
//            model.addAttribute("msg", "search_keyword error");
//            model.addAttribute("path", "crewManagement.do");
//            return "views/info";
//        }
//
//        model.addAttribute("datas", datas);
//        model.addAttribute("page", page);
//        model.addAttribute("size", size);


        return "admin/crewManagement";
    }

    @GetMapping("/crewManagementDetail.do")
    public String crewManagementDetail(Model model, Battle_recordDTO battle_recordDTO, CrewDTO crewDTO) {

        // 크루전 정보 - 암벽장
        // 암벽장 명 , 게임일, 승리한 크루, MVP
        // TODO DAO 쿼리문 수정됐는지 확인하기
        List<Battle_recordDTO> data = this.battle_recordService.selectAllWinnerParticipantGym(battle_recordDTO);

        // 참여 크루
        // 크루명 / 크루장 / 크루원 수
        // TODO DAO 쿼리문 수정됐는지 확인하기
//        List<CrewDTO> datas = this.crewService.selectAllParticipant(crewDTO);

        model.addAttribute("data", data);
//        model.addAttribute("datas", datas);

        return "admin/crewManagementDetail";
    }



    // 크루전 관리
    @PostMapping("/crewBattleManagement.do")
    public String crewBattleManagement(Model model) {
		/*
		selectbox
			크루전 번호, 암벽장 이름, 크루전 진행 날짜


		크루전 번호
		암벽장 이름
		크루전 진행 날짜
		크루전 생성일
		상태 ( t/f )
		↑ SELECTALL


		 */

        // 크루 정보 등록 필요 nullcheck (t/f)


        return null;
    }


    // 비동기
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
