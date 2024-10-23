package com.coma.app.view.crew.community;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.crew.CrewDAO;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;


@Controller("crewPageController")
public class CrewPageController{

	@Autowired
	private Battle_recordService battle_recordService;

	@Autowired
	private MemberService memberService;

	@RequestMapping("/CrewPage.do")
	public String crewPage(ServletContext ServletContext, HttpSession session, Model model, CrewDTO crewDTO, CrewDAO crewDAO,
			MemberDTO memberDTO, MemberDAO memberDAO,
			Battle_recordDTO battle_recordDTO, Battle_recordDAO battle_recordDAO) {
		String path = "myCrewPage";//크루가 있다는 것을 가정

		//로그인 정보 확인하고
		//크루 정보를 확인한다.
		//크루 정보를 확인한다면,
		//만약 크루가 있다면 크루마이페이지로
		//만약 없다면 크루가입페이지(CrewJoinPage.do)로 간다.

		//로그인 정보가 있는지 확인해주고
		//사용자 아이디
		String member_id = (String)session.getAttribute("MEMBER_ID");
		//사용자 아이디
		if (member_id == null) {
			// 로그인 페이지로 전달
			path = "LOGINACTION.do";
		} 
		else {
			//사용자 크루 정보
			int crew_num = Integer.parseInt((String)session.getAttribute("CREW_CHECK"));
			//크루가 없다면
			if(crew_num<=0) {
				path = "CrewListPage.do";
			}
			//크루가 있다면
			else {

				//자신의 크루의 크루원들의 정보를 위해 member selectAll
				//자신의 크루의 승리목록의 정보를 위해 battle selectAll
				crewDTO.setCrew_num(crew_num);//세션에 저장되어있는 로그인된 사용자의 크루pk
				System.out.println("57 CrewPageAction crew_num "+crew_num);
				
				crewDTO.setCrew_condition("CREW_ONE");//내 크루정보 셀렉원 컨디션
				crewDTO = crewDAO.selectOne(crewDTO);
				String filename = "";
				if (crewDTO == null) {//혹시모를 에러잡기 위해
					filename = "default.jpg"; // 디폴트(기본) 이미지

				} else {
					filename = crewDTO.getCrew_profile(); // 사용자의 프로필을 받아옴

				}
				crewDTO.setCrew_profile(ServletContext.getContextPath() + "/crew_img_folder/" + filename);

				//memberService.setModel_member_crew_num(crew_num);
				//memberDTO.setModel_member_condition("MEMBER_ALL_SEARCH_CREW_MEMBER_NAME");//크루원 셀렉올 컨디션

				 List<MemberDTO> model_member_crew_datas = memberService.selectAllSearchCrewMemberName(memberDTO);
				System.out.println("77 model_member_crew_datas 사이즈 = "+model_member_crew_datas.size());

				battle_recordDTO.setBattle_record_condition("BATTLE_RECORD_ALL_WINNER");//내 크루 승리목록 컨디션
				battle_recordDTO.setBattle_record_crew_num(crew_num);
				List<Battle_recordDTO> Battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);
				int cnt = 1;
				for(Battle_recordDTO data : Battle_record_datas) {
					System.err.println(cnt+"번째 CrewPageAction 내 크루 승리목록 정보 시작");
					System.out.println("크루전 pk = "+data.getBattle_record_battle_num());
					System.out.println("크루전기록 pk = "+data.getBattle_record_num());
					System.out.println("크루 pk = "+data.getBattle_record_crew_num());
					System.out.println("크루 이름 = "+data.getBattle_record_crew_name());
					System.out.println("암벽장 장소 = "+data.getBattle_record_gym_location());
					System.out.println("mvp = "+data.getBattle_record_mvp_id());
					System.err.println("CrewPageAction 내 크루 승리목록 정보 끝");
					cnt++;
				}
				model.addAttribute("CREW", crewDTO);
				model.addAttribute("model_member_crew_datas", model_member_crew_datas);
				model.addAttribute("Battle_record_datas", Battle_record_datas);
			}

		}
		return path;
	}
}
