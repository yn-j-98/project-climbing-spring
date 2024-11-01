package com.coma.app.view.crew.battle;


import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle.BattleService;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;


@Slf4j
@Controller
public class CrewBattleController {
    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private BattleService battleService;

    @LoginCheck
    @GetMapping("/crewBattleDetail.do")
    public String crewBattle(Model model, BattleDTO battleDTO, Battle_recordDTO battle_recordDTO) {
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
        int battle_num = battleDTO.getBattle_num();

        //크루전 번호로 크루전 검색
        log.info("검색할 크루전 pk = [{}]", battle_num);
        battle_recordDTO.setBattle_record_battle_num(battle_num);
        battle_recordDTO = this.battle_recordService.selectOneBattle(battle_recordDTO);
        log.info("crewBattle.battle_recordDTO [{}]", battle_recordDTO);
        model.addAttribute("battle_record", battle_recordDTO);

        //크루전 번호로 참여크루 총 개수 검색
        battleDTO.setBattle_num(battle_num);
        int battle_total = this.battleService.selectOneSearchCountActive(battleDTO).getTotal();
        log.info("크루전 참여크루 총 개수 = [{}]", battle_total);
        model.addAttribute("battle_total", battle_total);

        //크루전 번호로 참여한 크루들 정보 검색
        battle_recordDTO.setBattle_record_battle_num(battle_num);
        List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllParticipantCrew(battle_recordDTO);
        if (battle_record_datas.isEmpty()) {
            log.error("battle_record_datas 비어있음");
        }
        for (Battle_recordDTO data : battle_record_datas) {
            log.info("crewBattle.battle_record_datas [{}]", data);
        }
        model.addAttribute("battle_record_datas", battle_record_datas);

        //크루전 종료 여부
        boolean flag = false;
        for (Battle_recordDTO data : battle_record_datas) {
            log.info("crewBattle.battle_record_datas [{}]", data);
            if (data.getBattle_record_is_winner().equals("T")) {
                flag = true;
                break;
            }
        }
        model.addAttribute("battle_flag", flag);
        if (flag) {
            log.info("경기 결과 있음");
        } else {
            log.info("경기 시작전");
        }

        return "views/crewBattleContent";
    }

    @LoginCheck
    @GetMapping("/crewBattle.do")
    public String crewBattle(Model model, BattleDTO battleDTO, @SessionAttribute("MEMBER_ID") String member_id, @SessionAttribute("CREW_CHECK") Integer crew_num) {
        log.info("crewBattle.session.member_id = [{}]", member_id);
        log.info("crewBattle.session.crew_num = [{}]", crew_num);
        /*
         * 페이지네이션을 하기위해 뷰에게서 page_num을 받아오고
         * 페이지네이션 계산 후
         * memberDTO와 DAO 생성
         * DTO에 member_id를 set해주고
         * 내 크루를 찾기위해 selectOne
         * 컨디션은 MEMBER_SEARCH_MY_CREW
         *
         * battleDTO와 DAO 생성
         * DTO에 battle_crew_num을 set해주고
         * 내 크루 크루전을 찾기위해 selectOne
         * 컨디션은 VATTLE_SEARCH_MEMBER_BATTLE
         *
         * battleDTO를 따로 하나 더 생성해서
         * 크루전 목록  selectAll
         * min_num과 max_num을 set
         * 컨디션은 BATTLE_ALL_ACTIVE
         *
         * battleDTO 또 하나 더 생성해서
         * 크루전 총 개수 selectOne
         * 컨디션은 BATTLE_ONE_COUNT_ACTIVE
         */
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화

        if (crew_num == null || crew_num <= 0) {
            model.addAttribute("title", "페이지 접근 실패");
            model.addAttribute("msg", "가입된 크루가 없습니다");
            model.addAttribute("path", "crewList.do");
            return "views/info";
        }
        int pageNum = battleDTO.getPage(); //자동바인딩
        if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
            pageNum = 1;
        }
        minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
        model.addAttribute("page", pageNum);//현재페이지번호

        //내 크루가 참여신청한 크루전 출력
        battleDTO.setBattle_crew_num(crew_num);
        BattleDTO my_battle = this.battleService.selectOneSearchMemberBattle(battleDTO);
        log.info("이미지 = [{}]", battleDTO.getBattle_gym_profile());
        model.addAttribute("my_battle", battleDTO);//내크루전 정보

        //전체 크루전 목록 출력 + 페이지네이션
        battleDTO.setBattle_min_num(minBoard);
        List<BattleDTO> battle_datas = this.battleService.selectAllActive(battleDTO);

        model.addAttribute("battle_datas", battle_datas);//활성화 되어있는 크루전 전체목록

        //활성화되어있는 크루전 총 개수 출력
        BattleDTO battle_count = this.battleService.selectOneSearchCountActive(battleDTO);
        int listNum = battle_count.getTotal();
        model.addAttribute("total", listNum);//전체 게시글 총개수

        return "views/crewBattleMain";
    }

}
