package com.coma.app.biz.battle;


public class BattleDTO {
	private int model_battle_num;	 			        // 크루전 PK
	private int model_battle_gym_num; 		      	// 암벽장 번호 FK
	private String model_battle_registration_date; // 크루전 등록날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private String model_battle_game_date; 		    // 크루전의 실제 게임하는 날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	
	//DTO에만 존재하는 데이터
	private String model_battle_gym_name;	//크루전 참여크루 이름
	private String model_battle_gym_profile;	//크루전 참여크루 사진 url
	private String model_battle_gym_location;	//크루전 참여크루 지역
	private	String model_battle_crew_profile;		//크루전 참여 크루 프로필
	private	int model_battle_crew_num;		//크루전 참여 크루 번호
	private int model_battle_total;			       // 전체 게시글 총수 
	private int model_battle_max_num; 		       // 페이지네이션 데이터
	private int model_battle_min_num;  		     // 페이지네이션 데이터
	private String model_battle_condition;      // 개발자 데이터 검색
	
	public void setModel_battle_crew_profile(String model_battle_crew_profile) {
		this.model_battle_crew_profile = model_battle_crew_profile;
	}
	public String getModel_battle_crew_profile() {
		return model_battle_crew_profile;
	}
	public int getModel_battle_num() {
		return model_battle_num;
	}
	public void setModel_battle_num(int model_battle_num) {
		this.model_battle_num = model_battle_num;
	}
	public int getModel_battle_gym_num() {
		return model_battle_gym_num;
	}
	public void setModel_battle_gym_num(int model_battle_gym_num) {
		this.model_battle_gym_num = model_battle_gym_num;
	}
	public String getModel_battle_registration_date() {
		return model_battle_registration_date;
	}
	public void setModel_battle_registration_date(String model_battle_registration_date) {
		this.model_battle_registration_date = model_battle_registration_date;
	}
	public String getModel_battle_game_date() {
		return model_battle_game_date;
	}
	public void setModel_battle_game_date(String model_battle_game_date) {
		this.model_battle_game_date = model_battle_game_date;
	}
	public int getModel_battle_total() {
		return model_battle_total;
	}
	public void setModel_battle_total(int model_battle_total) {
		this.model_battle_total = model_battle_total;
	}
	public int getModel_battle_max_num() {
		return model_battle_max_num;
	}
	public void setModel_battle_max_num(int model_battle_max_num) {
		this.model_battle_max_num = model_battle_max_num;
	}
	public int getModel_battle_min_num() {
		return model_battle_min_num;
	}
	public void setModel_battle_min_num(int model_battle_min_num) {
		this.model_battle_min_num = model_battle_min_num;
	}
	public String getModel_battle_condition() {
		return model_battle_condition;
	}
	public void setModel_battle_condition(String model_battle_condition) {
		this.model_battle_condition = model_battle_condition;
	}
	public String getModel_battle_gym_name() {
		return model_battle_gym_name;
	}
	public void setModel_battle_gym_name(String model_battle_gym_name) {
		this.model_battle_gym_name = model_battle_gym_name;
	}
	public String getModel_battle_gym_profile() {
		return model_battle_gym_profile;
	}
	public void setModel_battle_gym_profile(String model_battle_gym_profile) {
		this.model_battle_gym_profile = model_battle_gym_profile;
	}
	public String getModel_battle_gym_location() {
		return model_battle_gym_location;
	}
	public void setModel_battle_gym_location(String model_battle_gym_location) {
		this.model_battle_gym_location = model_battle_gym_location;
	}
	public int getModel_battle_crew_num() {
		return model_battle_crew_num;
	}
	public void setModel_battle_crew_num(int model_battle_crew_num) {
		this.model_battle_crew_num = model_battle_crew_num;
	}
	@Override
	public String toString() {
		return "BattleDTO [model_battle_num=" + model_battle_num + ", model_battle_gym_num=" + model_battle_gym_num
				+ ", model_battle_registration_date=" + model_battle_registration_date + ", model_battle_game_date="
				+ model_battle_game_date + ", model_battle_gym_name=" + model_battle_gym_name
				+ ", model_battle_gym_profile=" + model_battle_gym_profile + ", model_battle_gym_location="
				+ model_battle_gym_location + ", model_battle_crew_num=" + model_battle_crew_num
				+ ", model_battle_crew_profile=" + model_battle_crew_profile + ", model_battle_total="
				+ model_battle_total + ", model_battle_max_num=" + model_battle_max_num + ", model_battle_min_num="
				+ model_battle_min_num + ", model_battle_condition=" + model_battle_condition + "]";
	}
}
