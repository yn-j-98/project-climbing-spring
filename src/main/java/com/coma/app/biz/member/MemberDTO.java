package com.coma.app.biz.member;

import java.sql.Date;

public class
MemberDTO {
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
	private Date member_registration_date; //회원가입날짜
	private String member_role;            //관리자 권한
	
	//DTO에만 존재하는 데이터
	private String member_grade_profile; //사용자의 등급 사진
	private String member_grade_name; //사용자의 등급 이름
	private int member_crew_current_size; //사용자의 크루의 현재 인원수
	private String member_condition;       //개발자 데이터
	private String member_crew_name;		//사용자가 속한 크루 이름
	private String member_crew_profile;	//사용자가 속한 크루 이미지 url
	private String member_crew_leader;	//사용자가 속한 크루장 pk
	private String VIEW_AUTO_LOGIN; // 자동로그인 체크
	private int VIEW_USE_POINT;
	private int member_total;	// 사용자 인원 수 // TODO 관리자페이지
	private String member_reservation_month;	// 사용자가 가입한 년도-월 // TODO 관리자페이지
	private int member_page;	// 사용자 페이지네이션 // TODO 관리자페이지

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public int getMember_current_point() {
		return member_current_point;
	}

	public void setMember_current_point(int member_current_point) {
		this.member_current_point = member_current_point;
	}

	public int getMember_total_point() {
		return member_total_point;
	}

	public void setMember_total_point(int member_total_point) {
		this.member_total_point = member_total_point;
	}

	public int getMember_crew_num() {
		return member_crew_num;
	}

	public void setMember_crew_num(int member_crew_num) {
		this.member_crew_num = member_crew_num;
	}

	public String getMember_crew_join_date() {
		return member_crew_join_date;
	}

	public void setMember_crew_join_date(String member_crew_join_date) {
		this.member_crew_join_date = member_crew_join_date;
	}

	public String getMember_location() {
		return member_location;
	}

	public void setMember_location(String member_location) {
		this.member_location = member_location;
	}

	public Date getMember_registration_date() {
		return member_registration_date;
	}

	public void setMember_registration_date(Date member_registration_date) {
		this.member_registration_date = member_registration_date;
	}

	public String getMember_role() {
		return member_role;
	}

	public void setMember_role(String member_role) {
		this.member_role = member_role;
	}

	public String getMember_grade_profile() {
		return member_grade_profile;
	}

	public void setMember_grade_profile(String member_grade_profile) {
		this.member_grade_profile = member_grade_profile;
	}

	public String getMember_grade_name() {
		return member_grade_name;
	}

	public void setMember_grade_name(String member_grade_name) {
		this.member_grade_name = member_grade_name;
	}

	public int getMember_crew_current_size() {
		return member_crew_current_size;
	}

	public void setMember_crew_current_size(int member_crew_current_size) {
		this.member_crew_current_size = member_crew_current_size;
	}

	public String getMember_condition() {
		return member_condition;
	}

	public void setMember_condition(String member_condition) {
		this.member_condition = member_condition;
	}

	public String getMember_crew_name() {
		return member_crew_name;
	}

	public void setMember_crew_name(String member_crew_name) {
		this.member_crew_name = member_crew_name;
	}

	public String getMember_crew_profile() {
		return member_crew_profile;
	}

	public void setMember_crew_profile(String member_crew_profile) {
		this.member_crew_profile = member_crew_profile;
	}

	public String getMember_crew_leader() {
		return member_crew_leader;
	}

	public void setMember_crew_leader(String member_crew_leader) {
		this.member_crew_leader = member_crew_leader;
	}

	public String getVIEW_AUTO_LOGIN() {
		return VIEW_AUTO_LOGIN;
	}

	public void setVIEW_AUTO_LOGIN(String VIEW_AUTO_LOGIN) {
		this.VIEW_AUTO_LOGIN = VIEW_AUTO_LOGIN;
	}

	public int getVIEW_USE_POINT() {
		return VIEW_USE_POINT;
	}

	public void setVIEW_USE_POINT(int VIEW_USE_POINT) {
		this.VIEW_USE_POINT = VIEW_USE_POINT;
	}

	public int getMember_total() {
		return member_total;
	}

	public void setMember_total(int member_total) {
		this.member_total = member_total;
	}

	public String getMember_reservation_month() {
		return member_reservation_month;
	}

	public void setMember_reservation_month(String member_reservation_month) {
		this.member_reservation_month = member_reservation_month;
	}

	public int getMember_page() {
		return member_page;
	}

	public void setMember_page(int member_page) {
		this.member_page = member_page;
	}

	@Override
	public String toString() {
		return "MemberDTO{" +
				"member_id='" + member_id + '\'' +
				", member_name='" + member_name + '\'' +
				", member_password='" + member_password + '\'' +
				", member_phone='" + member_phone + '\'' +
				", member_profile='" + member_profile + '\'' +
				", member_current_point=" + member_current_point +
				", member_total_point=" + member_total_point +
				", member_crew_num=" + member_crew_num +
				", member_crew_join_date='" + member_crew_join_date + '\'' +
				", member_location='" + member_location + '\'' +
				", member_registration_date=" + member_registration_date +
				", member_role='" + member_role + '\'' +
				", member_grade_profile='" + member_grade_profile + '\'' +
				", member_grade_name='" + member_grade_name + '\'' +
				", member_crew_current_size=" + member_crew_current_size +
				", member_condition='" + member_condition + '\'' +
				", member_crew_name='" + member_crew_name + '\'' +
				", member_crew_profile='" + member_crew_profile + '\'' +
				", member_crew_leader='" + member_crew_leader + '\'' +
				", VIEW_AUTO_LOGIN='" + VIEW_AUTO_LOGIN + '\'' +
				", VIEW_USE_POINT=" + VIEW_USE_POINT +
				", member_total=" + member_total +
				", member_reservation_month='" + member_reservation_month + '\'' +
				", member_page=" + member_page +
				'}';
	}
}
