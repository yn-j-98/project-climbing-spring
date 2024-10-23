package com.coma.app.view.member;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;



@Controller("main")
public class MainPageAction{
	
	
	@RequestMapping("/MAINPAGEACTION.do")
	public String main(Model model, MemberDAO memberDAO, MemberDTO memberDTO) throws Exception {
																				// 커맨드 객체
		// mav 객체 생성
		
		// 경로 기본값 설정
		
		

        // 로그인 정보 보내주기 네비게이션 바 때문에
//        String login[] = LoginCheck.Success(request, response); TODO
//        System.out.println("log : 로그인 성공 " + login[0] ); TODO
        //크루전 정보 부분
        //배틀 DTO, DAO로 selectAll - 크루전 정보 보내주기 컨디션
//        BattleDAO battleDAO = new BattleDAO(); TODO 배틀 달아줘야함~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        BattleDTO battleDTO = new BattleDTO();
//        battleDTO.setModel_battle_condition("BATTLE_ALL_TOP4");//크루전 정보4개 컨디션        
        
//        ArrayList<BattleDTO> model_battle_datas = battleDAO.selectAll(battleDTO);
        //암벽장 프로필 생각
       
//        String filename = battleDTO.getModel_battle_gym_profile();
//        프로필foreach하고 set으로 주소값추가
        
//        for(BattleDTO data : model_battle_datas) {
//            String contextPath = request.getServletContext().getContextPath();
//            String profilePath = contextPath + "/profile_img/" + data.getModel_battle_gym_profile();
//            data.setModel_battle_gym_profile(profilePath);
//        }
//        request.setAttribute("model_battle_datas", model_battle_datas); TODO
        
        //크루랭킹부분
        //크루프로필 생각	
		
        memberDTO.setMember_condition("MEMBER_ALL_TOP10_CREW_RANK");//크루랭킹 10개 컨디션
		
        // 컨테이너 생성(new memberDAO 할 수있는 컨테이너)
//		AbstractApplicationContext factory = new GenericXmlApplicationContext("MemberApplicationContext.xml");
		
		// memberService 메서드 쓸거야(memberDAO), member 패키지안에 어노테이션 (memberService) 스캔해줘
//		MemberService memberservice = (MemberService)factory.getBean("memberService");
		
        List<MemberDTO> member_all_top10_crew_rank= memberDAO.selectAll(memberDTO);
		
//		member_all_top10_crew_rank=memberservice.selectAll(memberDTO);
		
        
        //model_crew_rank_datas에 담아서 보내주기
                
        //Member_rank_datas에 담아서 보내기
//        for(MemberDTO data : datas) {
//        	String contextPath = request.getServletContext().getContextPath();
//        	String profilePath = contextPath + "/crew_img_folder/" + data.getMember_crew_profile();
//        	data.setMember_crew_profile(profilePath);
//        } TODO 바꿔야함~~~~~~~~~~~~~~
		
        model.addAttribute("model_crew_rank_datas",member_all_top10_crew_rank);
        
        
        
        //개인 랭킹 부분
		memberDTO.setMember_condition("MEMBER_ALL_TOP10_RANK");//개인 랭킹 10개 컨디션
        
		List<MemberDTO> member_all_top_rank= memberDAO.selectAll(memberDTO);
//		member_all_top_rank = memberservice.selectAll(memberDTO);
        
//        for (MemberDTO data : datas) {
//            String profileFileName = data.getMember_profile();
//            String contextPath = request.getServletContext().getContextPath();
//            String profilePath = contextPath + "/profile_img/" + profileFileName;
//            data.setMember_profile(profilePath);
//        }TODO 바꿔야함~~~~~~~~~~~~~~~~~
        
		model.addAttribute("Member_rank_datas",member_all_top_rank);
                
        //board DTO, DAO로 selectAll - 게시판 6개 컨디션
        //글6개 부분
//        BoardDAO boardDAO = new BoardDAO();
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setModel_board_condition("BOARD_ALL_ROWNUM");//글 6개 컨디션
//                
//        ArrayList<BoardDTO> model_board_datas = boardDAO.selectAll(boardDTO);
//        request.setAttribute("model_board_datas", model_board_datas);
//
//       //model_board_datas에 담아서 보내기
//        
//        forward.setPath(path);
//        forward.setRedirect(flagRedirect);
        return "main";
	}
}
