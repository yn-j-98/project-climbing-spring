package com.coma.app.view.member;

import com.coma.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.view.function.ProfileUpload;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller("changeMemberController")
public class ChangeMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/CHANGEMEMBERACTION.do")
	public String changMember(HttpServletRequest request, HttpSession session, MemberDTO data) {
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "MYPAGEPAGEACTION.do";
		
		String member_id = (String)session.getAttribute("member_id");

		//만약 로그인 정보가 있다면 사용자 정보를 변경하고
		if(member_id != null) {
			//profile_img 에 파일을 저장하고
			data.setMember_id(member_id);//바꿀 사용자 아이디를 입력해줍니다.
			
			//file 업로드 확인
			String filename = ProfileUpload.upload(request);
			boolean flag = false;
			//uploadfile이 null이 아니라면 DB의 profile 이미지를 변경합니다.
			if(!filename.isEmpty()){
				System.out.println("uploadfile not null 로그 : " + filename);
				data.setMember_profile(filename);//저장한 프로필 이미지로 변경합니다.
				//data.setMember_condition("MEMBER_UPDATE_ALL");
				flag = memberService.updateAll(data);
			}
			else {
				System.out.println("uploadfile null 로그");
				//data.setMember_condition("MEMBER_UPDATE_WITHOUT_PROFILE");
				flag = memberService.updateWithoutProfile(data);
			}

			System.out.println(data);
			//프로필 이미지 저장 로그
			System.err.println(filename);

			
			if(!flag) {
				path = "CHANGEMEMBERPAGEACTION.do";
			}
		}
		//로그인 정보가 없다면 로그인 페이지로 이동시킵니다.
		else {
			path = "LOGINPAGEACTION.do";
		}
		
		return path;
	}

}