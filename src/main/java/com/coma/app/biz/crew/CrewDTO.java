package com.coma.app.biz.crew;

public class CrewDTO {
	private int model_crew_num;             //크루 번호
	private String model_crew_name;	        //크루 이름
	private String model_crew_description;	//크루 설명
	private int model_crew_max_member_size; //크루 최대 인원수
	private String model_crew_leader;	    //크루 리더
	private String model_crew_battle_status; //크루전 신청여부 Y,N(char1)
	private String model_crew_profile; 		//크루 이미지 url
	
	//DTO에만 존재하는 데이터 
	private int model_crew_max_num; 	// 페이지네이션 데이터
	private int model_crew_min_num;	// 페이지네이션 데이터
	private int model_crew_total;  //현재 크루 개수
	private int model_crew_current_member_size; //크루의 현재 인원수
	private String model_crew_condition;    // 개발자 데이터 검색
	public int getModel_crew_num() {
		return model_crew_num;
	}
	public void setModel_crew_num(int model_crew_num) {
		this.model_crew_num = model_crew_num;
	}
	public String getModel_crew_name() {
		return model_crew_name;
	}
	public void setModel_crew_name(String model_crew_name) {
		this.model_crew_name = model_crew_name;
	}
	public String getModel_crew_description() {
		return model_crew_description;
	}
	public void setModel_crew_description(String model_crew_description) {
		this.model_crew_description = model_crew_description;
	}
	public int getModel_crew_max_member_size() {
		return model_crew_max_member_size;
	}
	public void setModel_crew_max_member_size(int model_crew_max_member_size) {
		this.model_crew_max_member_size = model_crew_max_member_size;
	}
	public String getModel_crew_leader() {
		return model_crew_leader;
	}
	public void setModel_crew_leader(String model_crew_leader) {
		this.model_crew_leader = model_crew_leader;
	}
	public String getModel_crew_battle_status() {
		return model_crew_battle_status;
	}
	public void setModel_crew_battle_status(String model_crew_battle_status) {
		this.model_crew_battle_status = model_crew_battle_status;
	}
	public String getModel_crew_condition() {
		return model_crew_condition;
	}
	public void setModel_crew_condition(String model_crew_condition) {
		this.model_crew_condition = model_crew_condition;
	}
	public String getModel_crew_profile() {
		return model_crew_profile;
	}
	public void setModel_crew_profile(String model_crew_profile) {
		this.model_crew_profile = model_crew_profile;
	}
	public int getModel_crew_max_num() {
		return model_crew_max_num;
	}
	public void setModel_crew_max_num(int model_crew_max_num) {
		this.model_crew_max_num = model_crew_max_num;
	}
	public int getModel_crew_min_num() {
		return model_crew_min_num;
	}
	public void setModel_crew_min_num(int model_crew_min_num) {
		this.model_crew_min_num = model_crew_min_num;
	}
	public int getModel_crew_total() {
		return model_crew_total;
	}
	public void setModel_crew_total(int model_crew_total) {
		this.model_crew_total = model_crew_total;
	}
	public int getModel_crew_current_member_size() {
		return model_crew_current_member_size;
	}
	public void setModel_crew_current_member_size(int model_crew_current_member_size) {
		this.model_crew_current_member_size = model_crew_current_member_size;
	}
	@Override
	public String toString() {
		return "CrewDTO [model_crew_num=" + model_crew_num + ", model_crew_name=" + model_crew_name
				+ ", model_crew_description=" + model_crew_description + ", model_crew_max_member_size="
				+ model_crew_max_member_size + ", model_crew_leader=" + model_crew_leader
				+ ", model_crew_battle_status=" + model_crew_battle_status + ", model_crew_profile="
				+ model_crew_profile + ", model_crew_max_num=" + model_crew_max_num + ", model_crew_min_num="
				+ model_crew_min_num + ", model_crew_total=" + model_crew_total + ", model_crew_current_member_size="
				+ model_crew_current_member_size + ", model_crew_conditon=" + model_crew_condition + "]";
	}
	
}
