//package com.coma.app.view.crew.join;
//
//import com.coma.app.biz.member.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.coma.app.biz.member.MemberDAO;
//import com.coma.app.biz.member.MemberDTO;
//
//import jakarta.servlet.http.HttpSession;
//
//@Controller("crewJoinController")
//public class CrewJoinController{
//
//	@Autowired
//	private MemberService memberService;
//
//	@RequestMapping("CrewJoin.do")
//	public String crewJoin(HttpSession session, Model model, MemberDTO memberDTO, MemberDAO memberDAO) {
//		String path = "info"; // 크루 가입 안내 페이지
//
//		// 로그인 상태 체크
//		String member_id = (String)session.getAttribute("MEMBER_ID");
//
//		// 로그인 되어 있지 않은 경우
//		if (member_id == null) {
//			path = "LOGINACTION.do"; // 로그인 페이지로 이동
//		} else {
//			// 크루 번호 확인 및 유효성 검사
//			try {
//				int crew_num = Integer.parseInt((String)session.getAttribute("CREW_CHECK"));
//
//				// 이미 크루에 가입된 경우
//				if (crew_num > 0) {
//					model.addAttribute("msg", "이미 소속된 크루가 있습니다.");
//					model.addAttribute("path", "CrewListPage.do");
//				} else {
//					// 크루 번호 파라미터 확인
//					int view_crew_num = memberDTO.getMember_crew_num();
//					if (view_crew_num <= 0) {
//						model.addAttribute("msg", "잘못된 요청입니다.");
//						model.addAttribute("path", "CrewListPage.do");
//						return path; // 유효하지 않은 요청 시 바로 종료
//					}
//
//					// 크루 가입 처리
//					memberDTO.setMember_id(member_id);
//					memberDTO.setMember_crew_num(view_crew_num);
//					//memberDTO.setMember_condition("MEMBER_UPDATE_CREW");
//
//
//					if (memberService.updateCrew(memberDTO)) {
//						// 업데이트 성공 시 세션 갱신
//						session.setAttribute("CREW_CHECK", view_crew_num);
//
//						model.addAttribute("msg", "해당 크루에 가입을 완료했습니다.");
//						model.addAttribute("path", "CrewPage.do");
//					} else {
//						// 업데이트 실패 시
//						model.addAttribute("msg", "크루 가입에 실패했습니다.");
//						model.addAttribute("path", "CrewListPage.do");
//					}
//				}
//			} catch (NumberFormatException e) {
//				// 숫자 변환 실패 (유효하지 않은 파라미터)
//				model.addAttribute("msg", "잘못된 요청입니다.");
//				model.addAttribute("path", "CrewListPage.do");
//			}
//		}
//
//		return path;
//	}
//}
