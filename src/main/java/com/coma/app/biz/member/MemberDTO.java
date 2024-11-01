package com.coma.app.biz.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class MemberDTO {
	private String member_id;              //아이디
	private String member_name;            //이름
	private String member_password;        //비밀번호
	private String member_phone;           //휴대폰 번호
	private String member_profile;         // 프로필사진 url
	private int member_current_point;      //사용가능한 포인트
	private int member_total_point;        //누적포인트
	private int member_crew_num;           //크루 FK
	private String member_crew_join_date;    //크루 가입날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private String member_location;        //사용자 현재지역
	private String member_registration_date; //회원가입날짜
	private String member_role;            //관리자 권한
	
	//DTO에만 존재하는 데이터
	private MultipartFile photoUpload;
	private String member_grade_profile; //사용자의 등급 사진
	private String member_grade_name; //사용자의 등급 이름
	private int member_crew_current_size; //사용자의 크루의 현재 인원수
	private String member_crew_name;		//사용자가 속한 크루 이름
	private String member_crew_profile;	//사용자가 속한 크루 이미지 url
	private String member_crew_leader;	//사용자가 속한 크루장 pk
	private String VIEW_AUTO_LOGIN; // 자동로그인 체크
	private int member_use_point;	// 사용자가 사용한 포인트
	private int total;	// 사용자 인원 수 // TODO 관리자페이지
	private String member_reservation_month;	// 사용자가 가입한 년도-월 // TODO 관리자페이지
	private int page;	// 사용자 페이지네이션
	private String search_keyword;	// 사용자 검색 목록
	private String search_content;	// 사용자 검색 내용
	private int member_min_num;	// 페이지 네이션 시작 PK
}
