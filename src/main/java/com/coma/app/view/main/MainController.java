package com.coma.app.view.main;


import java.util.List;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class MainController{


    @Autowired
    private ServletContext servletContext;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/main.do")
    public String main(Model model, BattleDTO battleDTO, MemberDTO memberDTO, BoardDTO boardDTO) {
        //	ActionForward forward = new ActionForward();
        //	String path = "main.jsp"; // 메인 페이지로 이동
        //    boolean flagRedirect = false; // 포워드 방식 사용 여부 설정 (false = forward 방식)

        // 로그인 정보 보내주기 네비게이션 바 때문에
//        String login[] = LoginCheck.Success(request, response);
//        System.out.println("log : 로그인 성공 " + login[0] );
        //크루전 정보 부분

//		battleDTO.setBattle_condition("BATTLE_ALL_TOP4");//크루전 정보4개 컨디션

//        List<BattleDTO> battle_datas = battleService.selectAllBattleAllTop4(battleDTO);
        //암벽장 프로필 생각

//		String filename = battleDTO.getModel_battle_gym_profile();
        //    프로필foreach하고 set으로 주소값추가

//        for(BattleDTO data : battle_datas) {
//            String contextPath = request.getServletContext().getContextPath();
//            String profilePath = contextPath + "/profile_img/" + data.getBattle_gym_profile();
//            data.setBattle_gym_profile(profilePath);
//        }

        //    request.setAttribute("model_battle_datas", model_battle_datas);
//        model.addAttribute("battle_datas", battle_datas);
        //============================하드코딩된 부분=====================================

        //크루랭킹부분

//        memberDTO.setMember_condition("MEMBER_ALL_TOP10_CREW_RANK");//크루랭킹 10개 컨디션

        List<MemberDTO> crew_rank_datas = memberService.selectAllTop10CrewRank(memberDTO);
        //model_crew_rank_datas에 담아서 보내주기

        //model_member_rank_datas에 담아서 보내기
        for(MemberDTO data : crew_rank_datas) {
            String contextPath = servletContext.getContextPath();

            String profilePath = contextPath + "/profile_img/" + data.getMember_crew_profile();
            data.setMember_crew_profile(profilePath);
        }

        //    request.setAttribute("model_crew_rank_datas", model_crew_rank_datas);
        model.addAttribute("crew_rank_datas",crew_rank_datas);


        //개인 랭킹 부분

//        memberDTO.setMember_condition("MEMBER_ALL_TOP10_RANK");//개인 랭킹 10개 컨디션

        List<MemberDTO> member_rank_datas = memberService.selectAllTop10Rank(memberDTO);

        for (MemberDTO data : member_rank_datas) {
            String profileFileName = data.getMember_profile();
            String contextPath = servletContext.getContextPath();

            String profilePath = contextPath + "/profile_img/" + profileFileName;
            data.setMember_profile(profilePath);
        }
        //    request.setAttribute("model_member_rank_datas", model_member_rank_datas);
        model.addAttribute("member_rank_datas",member_rank_datas);

        //board DTO, DAO로 selectAll - 게시판 6개 컨디션
        //글6개 부분

//        boardDTO.setBoard_condition("BOARD_ALL_ROWNUM");//글 6개 컨디션

        List<BoardDTO> board_datas = boardService.selectAllRownum(boardDTO);

        model.addAttribute("board_datas", board_datas);
        //model_board_datas에 담아서 보내기


        return "views/main";
    }

}

