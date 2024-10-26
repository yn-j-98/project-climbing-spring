package com.coma.app.view.crew.battle;



import com.coma.app.biz.battle.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


@Controller



public class CrewBattleController {

   @Autowired
   private Battle_recordService battle_recordService;

   @Autowired
   private BattleService battleService;


   @RequestMapping(value="/crewBattleDetail.do", method=RequestMethod.POST)
   public String crewBattle(HttpServletRequest request, HttpServletResponse response, Model model, BattleDTO battleDTO, Battle_recordDTO battle_recordDTO) {
      //		ActionForward forward = new ActionForward();
      //		String path = "crewBattleContent.jsp";
      //		boolean flagRedirect = false;

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

      // 로그인,크루 정보가 있는지 확인

      String login[] = LoginCheck.Success(request, response);
      //사용자 아이디
      String member_id = login[0];
      //사용자 크루 정보
      int crew_num = Integer.parseInt(login[1]);

//		int view_battle_num = Integer.parseInt(request.getParameter("view_battle_num"));
//		System.out.println("CrewBattleOnePageAction view_battle_num = "+view_battle_num);
      //		Battle_recordDTO battle_recordDTO = new Battle_recordDTO();
      //		Battle_recordDAO battle_recordDAO = new Battle_recordDAO();

      //크루전 내용
      //		battle_recordDTO.setModel_battle_record_battle_num(view_battle_num);
      //		battle_recordDTO.setModel_battle_record_condition("BATTLE_RECORD_ONE_BATTLE");//크루전 내용 컨디션
      battle_recordDTO = battle_recordService.selectOneBattle(battle_recordDTO);
      //		System.out.println("51 "+battle_recordDTO);

      //참여크루 총 개수
      //		BattleDTO battle_total = new BattleDTO();
      //		BattleDAO battleDAO = new BattleDAO();
//        battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");
      battleDTO = battleService.selectOneCountActive(battleDTO);


      //		Battle_recordDTO datas = new Battle_recordDTO();

      //참여한 크루들 정보
      //		datas.setModel_battle_record_battle_num(view_battle_num);
//        battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_PARTICIPANT_CREW");
      List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllParticipantCrew(battle_recordDTO);

      boolean flag = false; //크루전 종료 여부

      for(Battle_recordDTO data : battle_record_datas) {
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
      //		request.setAttribute("model_battle_record_datas", model_battle_record_datas);
      //		request.setAttribute("model_battle_record", battle_recordDTO);
      //		request.setAttribute("model_battle_total", battleDTO);
      //		request.setAttribute("model_battle_flag", flag);

      model.addAttribute("battle_record_datas", battle_record_datas);
      model.addAttribute("battle_record", battle_recordDTO);
      model.addAttribute("battle_total", battleDTO);
      model.addAttribute("battle_flag", flag);



      //		forward.setPath(path);
      //		forward.setRedirect(flagRedirect);
      //		return forward;

      return "crewBattleContent";
   }

   //이름 바꿀수도
   //그럴수도
   @RequestMapping(value="/crewBattle.do", method=RequestMethod.POST)
   public String crewBattle(HttpServletRequest request, HttpServletResponse response, Model model, BattleDTO battleDTO) {
//	      ActionForward forward = new ActionForward();
//	      String path = "crewBattleMain.jsp";//크루전 메인 페이지로 이동
//	      boolean flagRediect = false;//로그인정보나 크루정보
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
      String login[] = LoginCheck.Success(request, response);
      String member_id = login[0];
      //선택한 크루 정보
      int crew_num = 0;
      if(member_id!=null) {
         System.err.println("CrewBattlePageAction crew_num = "+login[1]);
         crew_num = Integer.parseInt(login[1]);
      }

//	      int pageNum = 1; // 페이지 번호 초기화
      // 페이지네이션 부분
//	      if(request.getParameter("page") != null) {
//	         pageNum = Integer.parseInt(request.getParameter("page")); // 페이지 번호가 있을 경우 변환하여 저장
//	      }


//	      int maxBoard = 1; // 최대 게시글 수 초기화

      // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
//	      if(pageNum <= 1) {
//	         // 페이지 번호가 1 이하일 경우
//	         minBoard = 1; // 최소 게시글 번호를 1로 설정
//        maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
//	      }
//	      else {
      // 페이지 번호가 2 이상일 경우
      //페이지 1보다 작다면
      //페이지넘 == 0
      //2이상이라면 페이지넘을 *boardSize

//	         maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산

      int pageNum = battleDTO.getPage();//요거 필요
      int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
      int minBoard = 1; // 최소 게시글 수 초기화

      minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
      int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화




//	      BattleDTO battleDTO = new BattleDTO();
//	      BattleDAO battleDAO = new BattleDAO();
      battleDTO.setBattle_crew_num(crew_num);//크루pk


//        battleDTO.setBattle_condition("BATTLE_SEARCH_MEMBER_BATTLE");//내 크루전 컨디션
      battleDTO = battleService.selectOneSearchMemberBattle(battleDTO);
      if(battleDTO != null) {
         battleDTO.setBattle_gym_profile("https://"+battleDTO.getBattle_gym_profile());
         System.out.println("이미지 : "+battleDTO.getBattle_gym_profile());
      }

//	      BattleDTO battle_data = new BattleDTO();
//	      battle_data.setModel_battle_max_num(maxBoard);



      battleDTO.setBattle_min_num(minBoard);

//        battleDTO.setBattle_condition("BATTLE_ALL_ACTIVE");//전체 크루전 목록 컨디션
      List<BattleDTO> battle_datas = battleService.selectAllActive(battleDTO);

//	      if(battle_datas != null) {
//	    	  for(BattleDTO data : battle_datas) {
//	    		  System.out.println("이미지 : "+data.getBattle_gym_profile());
//	    		  data.setModel_battle_gym_profile("https://"+data.getModel_battle_gym_profile());
//	    	  }
//	      }


//	      BattleDTO battle_count = new BattleDTO();

//        battleDTO.setBattle_condition("BATTLE_ONE_COUNT_ACTIVE");//크루전 총 개수 컨디션(페이지네이션)
      BattleDTO battle_count = battleService.selectOneCountActive(battleDTO);

      listNum = battle_count.getBattle_total();



      model.addAttribute("my_battle", battleDTO);//내크루전(암벽장, 주소, 날짜, 번호)
      model.addAttribute("currentPage", pageNum);//전체 게시글 총수
      model.addAttribute("battle_total", listNum);//현재페이지번호
      model.addAttribute("battle_datas", battle_datas);//크루전목록


//	      forward.setPath(path);
//	      forward.setRedirect(flagRediect);

      return "views/crewBattleMain";
   }





}
