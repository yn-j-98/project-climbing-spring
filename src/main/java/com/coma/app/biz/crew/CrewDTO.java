package com.coma.app.biz.crew;

public class CrewDTO {
	private int crew_num;             //크루 번호
	private String crew_name;	        //크루 이름
	private String crew_description;	//크루 설명
	private int crew_max_member_size; //크루 최대 인원수
	private String crew_leader;	    //크루 리더
	private String crew_battle_status; //크루전 신청여부 Y,N(char1)
	private String crew_profile; 		//크루 이미지 url
	
	//DTO에만 존재하는 데이터 
	private int crew_max_num; 	// 페이지네이션 데이터
	private int crew_min_num;	// 페이지네이션 데이터
	private int crew_total;  //현재 크루 개수
	private int crew_current_member_size; //크루의 현재 인원수
	private String crew_condition;    // 개발자 데이터 검색
	
	private int page; //크루의 현재 인원수

	public int getCrew_num() {
		return crew_num;
	}

	public void setCrew_num(int crew_num) {
		this.crew_num = crew_num;
	}

	public String getCrew_name() {
		return crew_name;
	}

	public void setCrew_name(String crew_name) {
		this.crew_name = crew_name;
	}

	public String getCrew_description() {
		return crew_description;
	}

	public void setCrew_description(String crew_description) {
		this.crew_description = crew_description;
	}

	public int getCrew_max_member_size() {
		return crew_max_member_size;
	}

	public void setCrew_max_member_size(int crew_max_member_size) {
		this.crew_max_member_size = crew_max_member_size;
	}

	public String getCrew_leader() {
		return crew_leader;
	}

	public void setCrew_leader(String crew_leader) {
		this.crew_leader = crew_leader;
	}

	public String getCrew_battle_status() {
		return crew_battle_status;
	}

	public void setCrew_battle_status(String crew_battle_status) {
		this.crew_battle_status = crew_battle_status;
	}

	public String getCrew_profile() {
		return crew_profile;
	}

	public void setCrew_profile(String crew_profile) {
		this.crew_profile = crew_profile;
	}

	public int getCrew_max_num() {
		return crew_max_num;
	}

	public void setCrew_max_num(int crew_max_num) {
		this.crew_max_num = crew_max_num;
	}

	public int getCrew_min_num() {
		return crew_min_num;
	}

	public void setCrew_min_num(int crew_min_num) {
		this.crew_min_num = crew_min_num;
	}

	public int getCrew_total() {
		return crew_total;
	}

	public void setCrew_total(int crew_total) {
		this.crew_total = crew_total;
	}

	public int getCrew_current_member_size() {
		return crew_current_member_size;
	}

	public void setCrew_current_member_size(int crew_current_member_size) {
		this.crew_current_member_size = crew_current_member_size;
	}

	public String getCrew_condition() {
		return crew_condition;
	}

	public void setCrew_condition(String crew_condition) {
		this.crew_condition = crew_condition;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "CrewDTO [crew_num=" + crew_num + ", crew_name=" + crew_name + ", crew_description=" + crew_description
				+ ", crew_max_member_size=" + crew_max_member_size + ", crew_leader=" + crew_leader
				+ ", crew_battle_status=" + crew_battle_status + ", crew_profile=" + crew_profile + ", crew_max_num="
				+ crew_max_num + ", crew_min_num=" + crew_min_num + ", crew_total=" + crew_total
				+ ", crew_current_member_size=" + crew_current_member_size + ", crew_condition=" + crew_condition
				+ ", page=" + page + "]";
	}
}
