package com.coma.app.view.main;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle.BattleService;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController{
	
    @Autowired
    private ServletContext servletContext;  // 필드로 주입

    @Autowired
    private BattleService battleService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;
	
	@GetMapping("/MAINPAGEACTION.do")
	public String mainPage(Model model, BattleDTO battleDTO, MemberDTO memberDTO, BoardDTO boardDTO) {
		String path = "main"; // 메인 페이지로 이동

        // 로그인 정보 보내주기 네비게이션 바 때문에
        //String login[] = LoginCheck.Success(request, response);
        //System.out.println("log : 로그인 성공 " + login[0] );
        //battleDTO.setBattle_condition("BATTLE_ALL_TOP4");
        List<BattleDTO> Battle_datas = battleService.selectAllBattleAllTop4(battleDTO);
        //암벽장 프로필 생각

// 프로필foreach하고 set으로 주소값추가
        for(BattleDTO data : Battle_datas) {
            String profilePath = servletContext.getContextPath() + "/profile_img/" + data.getBattle_gym_profile();
            data.setBattle_gym_profile(profilePath);
        }
        model.addAttribute("Battle_datas", Battle_datas);
        
        //크루랭킹부분
        //크루프로필 생각				
        memberDTO.setMember_condition("MEMBER_ALL_TOP10_CREW_RANK");//크루랭킹 10개 컨디션
        List<MemberDTO> model_crew_rank_datas = memberService.selectAllTop10CrewRank(memberDTO);
        //model_crew_rank_datas에 담아서 보내주기
                
        //Member_rank_datas에 담아서 보내기
        for(MemberDTO data : model_crew_rank_datas) {
        	String profilePath = servletContext.getContextPath() + "/crew_img_folder/" + data.getMember_crew_profile();
        	data.setMember_crew_profile(profilePath);
        }
        model.addAttribute("model_crew_rank_datas", model_crew_rank_datas);
        
        //개인 랭킹 부분
        memberDTO.setMember_condition("MEMBER_ALL_TOP10_RANK");//개인 랭킹 10개 컨디션
        List<MemberDTO> member_rank_datas = memberService.selectAllTop10Rank(memberDTO);
        for (MemberDTO data : member_rank_datas) {
            String profileFileName = data.getMember_profile();
            String profilePath = servletContext.getContextPath() + "/profile_img/" + profileFileName;
            data.setMember_profile(profilePath);
        }
        model.addAttribute("member_rank_datas", member_rank_datas);
                
        //board DTO, DAO로 selectAll - 게시판 6개 컨디션
        //글6개 부분
        //boardDTO.setBoard_condition("BOARD_ALL_ROWNUM");//글 6개 컨디션
        List<BoardDTO> Board_datas = boardService.selectAllRownum(boardDTO);
        model.addAttribute("Board_datas", Board_datas);

        return path;
	}

}
