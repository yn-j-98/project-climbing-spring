package com.coma.app.view.main;


import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;





@Controller
public class MainController{

    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/main.do")
    public String main(Model model, BattleDTO battleDTO, MemberDTO memberDTO, BoardDTO boardDTO) {

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

