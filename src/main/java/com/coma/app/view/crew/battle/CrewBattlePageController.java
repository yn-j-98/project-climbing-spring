package com.coma.app.view.crew.battle;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.battle.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;

import jakarta.servlet.http.HttpSession;


@Controller("crewBattlePageController")
public class CrewBattlePageController {

   @Autowired
   private BattleService battleService;

   @RequestMapping("/CrewBattlePage.do")
   public String crewBattlePage(HttpSession session,Model model, BattleDTO battleDTO, BattleDAO battleDAO) {
      String path = "crewBattleMain";//크루전 메인 페이지로 이동
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

      // 로그인 정보 보내주기 네비게이션 바 때문에
      String member_id = (String)session.getAttribute("MEMBER_ID");
      //선택한 크루 정보
      int crew_num = 0;
      if(member_id!=null) {
         System.err.println("CrewBattlePageAction crew_num = "+(String)session.getAttribute("CREW_CHECK"));
         crew_num = Integer.parseInt((String)session.getAttribute("CREW_CHECK"));
      }
      int pageNum = 1; // 페이지 번호 초기화
      // 페이지네이션 부분
         pageNum = battleDTO.getPage(); // 페이지 번호가 있을 경우 변환하여 저장
      int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
      int minBoard = 1; // 최소 게시글 수 초기화
      int maxBoard = 1; // 최대 게시글 수 초기화

      // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
      if(pageNum <= 1) {
         // 페이지 번호가 1 이하일 경우
         minBoard = 1; // 최소 게시글 번호를 1로 설정
         maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
      }
      else {
         // 페이지 번호가 2 이상일 경우
         minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
         maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
      }

      int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

      battleDTO.setBattle_crew_num(crew_num);//크루pk
      //battleDTO.setBattle_condition("BATTLE_SEARCH_MEMBER_BATTLE");//내 크루전 컨디션
      battleDTO = battleDAO.selectOneSearchMemberBattle(battleDTO);
      if(battleDTO != null) {
    	  battleDTO.setBattle_gym_profile("https://"+battleDTO.getBattle_gym_profile());
    	  System.out.println("이미지 : "+battleDTO.getBattle_gym_profile());    	  
      }

      battleDTO.setBattle_min_num(minBoard);
      //battle_data.setBattle_max_num(maxBoard);
      //battleDTO.setBattle_condition("BATTLE_ALL_ACTIVE");//전체 크루전 목록 컨디션
      List<BattleDTO> Battle_datas = battleService.selectAllActive(battleDTO);
      
      if(Battle_datas != null) {
    	  for(BattleDTO data : Battle_datas) {
    		  System.out.println("이미지 : "+data.getBattle_gym_profile());
    		  data.setBattle_gym_profile("https://"+data.getBattle_gym_profile());
    	  }    	  
      }

      //battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");//크루전 총 개수 컨디션(페이지네이션)
      listNum = battleService.selectOneCountActive(battleDTO).getBattle_total();
      
      model.addAttribute("model_my_battle", battleDTO);//내크루전(암벽장, 주소, 날짜, 번호)
      model.addAttribute("Battle_total", listNum);//전체 게시글 총수 
      model.addAttribute("currentPage", pageNum);//현재페이지번호
      model.addAttribute("Battle_datas", Battle_datas);//크루전목록

      return path;
   }

}
