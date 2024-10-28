package com.coma.app.view.crew.battle;



import com.coma.app.biz.battle.BattleService;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


@Controller



public class CrewBattleController {

   @Autowired
   private Battle_recordService battle_recordService;

   @Autowired
   private BattleService battleService;

   @LoginCheck
   @GetMapping("/crewBattleDetail.do")
   public String crewBattle(HttpSession session, Model model, BattleDTO battleDTO, Battle_recordDTO battle_recordDTO) {


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

      //사용자 아이디
      String member_id = (String) session.getAttribute("MEMBER_ID");


      //사용자 크루 정보
      int crew_num = (Integer) session.getAttribute("CREW_NUM");

      //view_battle_num필요

      //크루전 내용
      //		battle_recordDTO.setModel_battle_record_condition("BATTLE_RECORD_ONE_BATTLE");//크루전 내용 컨디션
      battle_recordDTO = this.battle_recordService.selectOneBattle(battle_recordDTO);
      System.out.println("CrewBattleController.battle_recordDTO ["+battle_recordDTO+"]");

      //참여크루 총 개수

//        battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");
      battleDTO = this.battleService.selectOneCountActive(battleDTO);

      //참여한 크루들 정보
//        battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_PARTICIPANT_CREW");
      List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllParticipantCrew(battle_recordDTO);

      boolean flag = false; //크루전 종료 여부

      for(Battle_recordDTO data : battle_record_datas) {
         System.out.println("crewBattleDetail.battle_record_datas ["+data+"]");
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


      model.addAttribute("battle_record_datas", battle_record_datas);
      model.addAttribute("battle_record", battle_recordDTO);
      model.addAttribute("battle_total", battleDTO);
      model.addAttribute("battle_flag", flag);


      return "crewBattleContent";
   }

   //이름 바꿀수도
   //그럴수도
   @GetMapping("/crewBattle.do")
   public String crewBattle(HttpSession session, Model model, BattleDTO battleDTO) {

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


      String member_id = (String) session.getAttribute("MEMBER_ID");
      //선택한 크루 정보
      int crew_num = 0;
      if(member_id!=null) {
         System.err.println("CrewBattlePageAction crew_num = "+crew_num);
         crew_num = (Integer) session.getAttribute("CREW_NUM");
      }

      int pageNum = battleDTO.getPage();//요거 필요
      int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
      int minBoard = 1; // 최소 게시글 수 초기화

      if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
         pageNum = 1;
      }

      minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
      int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

      battleDTO.setBattle_crew_num(crew_num);//크루pk


//        battleDTO.setBattle_condition("BATTLE_SEARCH_MEMBER_BATTLE");//내 크루전 컨디션
      battleDTO = this.battleService.selectOneSearchMemberBattle(battleDTO);
      if(battleDTO != null) {
         battleDTO.setBattle_gym_profile("https://"+battleDTO.getBattle_gym_profile());
         System.out.println("이미지 : "+battleDTO.getBattle_gym_profile());
      }

      battleDTO.setBattle_min_num(minBoard);

//        battleDTO.setBattle_condition("BATTLE_ALL_ACTIVE");//전체 크루전 목록 컨디션
      List<BattleDTO> battle_datas = this.battleService.selectAllActive(battleDTO);

      if(battle_datas != null) {
         for(BattleDTO data : battle_datas) {
            System.out.println("이미지 : "+data.getBattle_gym_profile());
            data.setBattle_gym_profile("https://"+data.getBattle_gym_profile());
         }
      }

//        battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");//크루전 총 개수 컨디션(페이지네이션)
      BattleDTO battle_count = this.battleService.selectOneCountActive(battleDTO);

      listNum = battle_count.getTotal();

      model.addAttribute("my_battle", battleDTO);//내크루전(암벽장, 주소, 날짜, 번호)
      model.addAttribute("page", pageNum);//전체 게시글 총수
      model.addAttribute("total", listNum);//현재페이지번호
      model.addAttribute("battle_datas", battle_datas);//크루전목록




      return "views/crewBattleMain";
   }





}
