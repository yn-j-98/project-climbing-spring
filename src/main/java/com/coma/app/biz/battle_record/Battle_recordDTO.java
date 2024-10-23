package com.coma.app.biz.battle_record;


public class Battle_recordDTO {
	private int model_battle_record_num;          // 크루전 참여기록 PK
	private int model_battle_record_battle_num;    // 크루전 FK
	private int model_battle_record_crew_num;     // 크루 FK
	private String model_battle_record_is_winner; // 승리여부 
	private String model_battle_record_mvp_id;       // 크루전 MVP
	//DTO에만 있는 데이터
	private int model_battle_record_gym_num;	//암벽장 번호
	private int model_battle_record_total;            // 전체 게시글 총수
	private String model_battle_record_gym_name;		//크루전 암벽장 이름
	private String model_battle_record_gym_location;	//크루전 암벽장 지역
	private String model_battle_record_game_date;	//크루전 날짜
	private String model_battle_record_crew_leader; //해당 크루의 크루장
	private String model_battle_record_crew_name;	//해당 크루의 이름
	private String model_battle_record_crew_profile; //해당 크루의 사진
	private String model_battle_record_searchKeyword; // 사용자 텍스트 기반검색
	private String model_battle_record_condition;      // 개발자 데이터 검색
	
	public int getModel_battle_record_gym_num() {
		return model_battle_record_gym_num;
	}
	public void setModel_battle_record_gym_num(int model_battle_record_gym_num) {
		this.model_battle_record_gym_num = model_battle_record_gym_num;
	}
	public String getModel_battle_record_gym_name() {
		return model_battle_record_gym_name;
	}
	public void setModel_battle_record_gym_name(String model_battle_record_gym_name) {
		this.model_battle_record_gym_name = model_battle_record_gym_name;
	}
	public String getModel_battle_record_gym_location() {
		return model_battle_record_gym_location;
	}
	public void setModel_battle_record_gym_location(String model_battle_record_gym_location) {
		this.model_battle_record_gym_location = model_battle_record_gym_location;
	}

	public String getModel_battle_record_game_date() {
		return model_battle_record_game_date;
	}
	public void setModel_battle_record_game_date(String model_battle_record_game_date) {
		this.model_battle_record_game_date = model_battle_record_game_date;
	}
	public int getModel_battle_record_num() {
		return model_battle_record_num;
	}
	public void setModel_battle_record_num(int model_battle_record_num) {
		this.model_battle_record_num = model_battle_record_num;
	}
	public String getModel_battle_record_crew_profile() {
		return model_battle_record_crew_profile;
	}
	public void setModel_battle_record_crew_profile(String model_battle_record_crew_profile) {
		this.model_battle_record_crew_profile = model_battle_record_crew_profile;
	}
	public int getModel_battle_record_battle_num() {
		return model_battle_record_battle_num;
	}
	public void setModel_battle_record_battle_num(int model_battle_record_battle_num) {
		this.model_battle_record_battle_num = model_battle_record_battle_num;
	}
	public int getModel_battle_record_crew_num() {
		return model_battle_record_crew_num;
	}
	public void setModel_battle_record_crew_num(int model_battle_record_crew_num) {
		this.model_battle_record_crew_num = model_battle_record_crew_num;
	}
	public String getModel_battle_record_is_winner() {
		return model_battle_record_is_winner;
	}
	public void setModel_battle_record_is_winner(String model_battle_record_is_winner) {
		this.model_battle_record_is_winner = model_battle_record_is_winner;
	}
	public String getModel_battle_record_mvp_id() {
		return model_battle_record_mvp_id;
	}
	public void setModel_battle_record_mvp_id(String model_battle_record_mvp_id) {
		this.model_battle_record_mvp_id = model_battle_record_mvp_id;
	}
	public int getModel_battle_record_total() {
		return model_battle_record_total;
	}
	public void setModel_battle_record_total(int model_battle_record_total) {
		this.model_battle_record_total = model_battle_record_total;
	}
	public String getModel_battle_record_searchKeyword() {
		return model_battle_record_searchKeyword;
	}
	public void setModel_battle_record_searchKeyword(String model_battle_record_searchKeyword) {
		this.model_battle_record_searchKeyword = model_battle_record_searchKeyword;
	}
	public String getModel_battle_record_condition() {
		return model_battle_record_condition;
	}
	public void setModel_battle_record_condition(String model_battle_record_condition) {
		this.model_battle_record_condition = model_battle_record_condition;
	}
	public String getModel_battle_record_crew_leader() {
		return model_battle_record_crew_leader;
	}
	public void setModel_battle_record_crew_leader(String model_battle_record_crew_leader) {
		this.model_battle_record_crew_leader = model_battle_record_crew_leader;
	}
	public String getModel_battle_record_crew_name() {
		return model_battle_record_crew_name;
	}
	public void setModel_battle_record_crew_name(String model_battle_record_crew_name) {
		this.model_battle_record_crew_name = model_battle_record_crew_name;
	}
	@Override
	public String toString() {
		return "Battle_recordDTO [model_battle_record_num=" + model_battle_record_num
				+ ", model_battle_record_battle_num=" + model_battle_record_battle_num
				+ ", model_battle_record_crew_num=" + model_battle_record_crew_num + ", model_battle_record_is_winner="
				+ model_battle_record_is_winner + ", model_battle_record_mvp_id=" + model_battle_record_mvp_id
				+ ", model_battle_record_gym_num=" + model_battle_record_gym_num + ", model_battle_record_total="
				+ model_battle_record_total + ", model_battle_record_gym_name=" + model_battle_record_gym_name
				+ ", model_battle_record_gym_location=" + model_battle_record_gym_location
				+ ", model_battle_record_game_date=" + model_battle_record_game_date
				+ ", model_battle_record_crew_leader=" + model_battle_record_crew_leader
				+ ", model_battle_record_crew_name=" + model_battle_record_crew_name
				+ ", model_battle_record_crew_profile=" + model_battle_record_crew_profile
				+ ", model_battle_record_searchKeyword=" + model_battle_record_searchKeyword
				+ ", model_battle_record_condition=" + model_battle_record_condition + "]";
	}
}
