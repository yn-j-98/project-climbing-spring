package com.coma.app.view.ranking;

import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.member.MemberService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.grade.GradeDAO;
import com.coma.app.biz.grade.GradeDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;

import jakarta.servlet.ServletContext;

@Controller("rankingController")
public class RankingController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/RankingPage.do")
	public String ranking( Model model,MemberDAO memberDAO, MemberDTO memberDTO, GradeDTO gradeDTO, GradeDAO gradeDAO) {
		String path = "personalRank"; // view에서 알려줄 예정
		
		//TODO model 컨디션값 받아서 수정해야함
		memberDTO.setMember_condition("MEMBER_SEARCH_RANK");
		
		//요청 값 : 전체 point / 등급 이미지 / 사용자 이름
		List<MemberDTO> member_datas = memberService.selectAllSearchRank(memberDTO);
		
		//등급 이미지는 서버에 저장해 두었기 때문에
		//model에서 받아온 등급 이미지 앞에 서버 주소를 추가해줍니다.
		//----------------------------------------------------------
		//model에 요청해서 등급 정보를 전부 불러오고 현재 등급 9개
		ArrayList<GradeDTO> model_grade_datas = gradeDAO.selectAll(gradeDTO);

		//불러온 랭킹에 등급 사진을 추가하기 위해 for문을 사용
		for (MemberDTO data : member_datas) {
			int member_total_point = data.getMember_total_point();

			//member_total_point 가 0보다 크면 for문 실행
			if(0 <= member_total_point) {
				//해당 등급의 최소 점수와 최대 점수를 비교
				//등급개수 만큼 for문을 돌려서 확인
				for (int i = 0; i < model_grade_datas.size(); i++) {
					int grade_min = model_grade_datas.get(i).getModel_grade_min_point();
					int grade_max = model_grade_datas.get(i).getModel_grade_max_point();

					//해당 등급 최소 점수보다 사용자의 점수가 크거나 같고 //사용자의 점수가 해당 등급의 최대 점수보다 작거나 같으면
					if(grade_min <= member_total_point && member_total_point <= grade_max) {
						data.setMember_grade_profile(servletContext.getContextPath()+"/grade_folder/" + model_grade_datas.get(i).getModel_grade_profile());
						data.setMember_grade_name(model_grade_datas.get(i).getModel_grade_name());
					}
					//만약 등급의 최대 점수보다 사용자 점수가 크다면
					else if(grade_max < member_total_point) {
						data.setMember_grade_profile(servletContext.getContextPath()+"/grade_folder/" + model_grade_datas.get(i).getModel_grade_profile());
						data.setMember_grade_name(model_grade_datas.get(i).getModel_grade_name());
						break;
					}
				}//	for (int i = 0; i < model_grade_datas.size(); i++) { 이미지 비교 for문 종료
			}// if(0 < member_total_point) { 종료
		}//	for (MemberDTO data : member_datas) { 랭킹 등급이미지 등록 for문 종료
		
		//받아온 값을 model_member_datas 값에 저장하여 전달
		model.addAttribute("model_member_datas", member_datas);
		return path;
	}
	
	@RequestMapping("/CrewRankingPage.do")
	public String crewRankingPage( Model model,MemberDAO memberDAO, MemberDTO memberDTO, GradeDTO gradeDTO, GradeDAO gradeDAO) {
		String path = "crewRank"; // view에서 알려줄 예정
		
		//크루 랭킹을 model에 요청 point 순으로 selectAll 예정
		//랭킹 페이지가 두개 이기 때문에 model에 condition 값 전달
		//TODO 컨디션값 받아서 수정해야함
		//memberDTO.setMember_condition("MEMBER_ALL_CREW_RANK");
		//요청 값 : 전체 point / 등급 이미지 / 크루 이름 / 크루장 / 크루원명
		List<MemberDTO> member_datas = memberService.selectAllCrewRank(memberDTO);

		//등급 이미지는 서버에 저장해 두었기 때문에
		//model에서 받아온 등급 이미지 앞에 서버 주소를 추가해줍니다.
		//----------------------------------------------------------

		//model에 요청해서 등급 정보를 전부 불러오고 현재 등급 9개
		ArrayList<GradeDTO> model_grade_datas = gradeDAO.selectAll(gradeDTO);

		//불러온 랭킹에 등급 사진을 추가하기 위해 for문을 사용
		for (MemberDTO data : member_datas) {
			int member_total_point = data.getMember_total_point();
			//member_total_point 가 0보다 크면 for문 실행
			if(0 <= member_total_point) {
				//해당 등급의 최소 점수와 최대 점수를 비교
				//등급개수 만큼 for문을 돌려서 확인
				for (int i = 0; i < model_grade_datas.size(); i++) {
					int grade_min = model_grade_datas.get(i).getModel_grade_min_point();
					int grade_max = model_grade_datas.get(i).getModel_grade_max_point();

					//해당 등급 최소 점수보다 사용자의 점수가 크거나 같고 //사용자의 점수가 해당 등급의 최대 점수보다 작거나 같으면
					if(grade_min <= member_total_point && member_total_point <= grade_max) {
						data.setMember_grade_profile(servletContext.getContextPath()+"/grade_folder/" + model_grade_datas.get(i).getModel_grade_profile());
						data.setMember_grade_name(model_grade_datas.get(i).getModel_grade_name());
					}
					//만약 등급의 최대 점수보다 사용자 점수가 크다면
					else if(grade_max < member_total_point) {
						data.setMember_grade_profile(servletContext.getContextPath()+"/grade_folder/" + model_grade_datas.get(i).getModel_grade_profile());
						data.setMember_grade_name(model_grade_datas.get(i).getModel_grade_name());
						break;
					}
				}//	for (int i = 0; i < model_grade_datas.size(); i++) { 이미지 비교 for문 종료
			}// if(0 < member_total_point) { 종료
		}//	for (MemberDTO data : member_datas) { 랭킹 등급이미지 등록 for문 종료

		//받아온 값을 model_member_datas 값에 저장하여 전달
		model.addAttribute("model_member_datas", member_datas);

		return path;
	}
}
