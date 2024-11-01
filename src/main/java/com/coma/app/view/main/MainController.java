package com.coma.app.view.main;


import java.util.List;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.coma.app.biz.battle.BattleDTO;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;



import org.springframework.web.bind.annotation.GetMapping;





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

        List<MemberDTO> crew_rank_datas = this.memberService.selectAllTop10CrewRank(memberDTO);
        //model_crew_rank_datas에 담아서 보내주기

        model.addAttribute("crew_rank_datas",crew_rank_datas);
        //===================================================================
        //개인 랭킹 부분

//        memberDTO.setMember_condition("MEMBER_ALL_TOP10_RANK");//개인 랭킹 10개 컨디션

        List<MemberDTO> member_rank_datas = this.memberService.selectAllTop10Rank(memberDTO);

        model.addAttribute("member_rank_datas",member_rank_datas);
        //==================================================================
        //board DTO, DAO로 selectAll - 게시판 6개 컨디션
        //글6개 부분

//        boardDTO.setBoard_condition("BOARD_ALL_ROWNUM");//글 6개 컨디션
        List<BoardDTO> board_datas = this.boardService.selectAllRowNum(boardDTO);

        model.addAttribute("board_datas", board_datas);
        //model_board_datas에 담아서 보내기


        return "views/main";
    }

}

