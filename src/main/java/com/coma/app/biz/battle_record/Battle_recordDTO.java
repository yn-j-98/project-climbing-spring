package com.coma.app.biz.battle_record;

public class Battle_recordDTO {
	private int battle_record_num;          // 크루전 참여기록 PK
	private int battle_record_battle_num;    // 크루전 FK
	private int battle_record_crew_num;     // 크루 FK
	private String battle_record_is_winner; // 승리여부
	private String battle_record_mvp_id;       // 크루전 MVP
	//DTO에만 있는 데이터
	private int battle_record_gym_num;	//암벽장 번호
	private int battle_record_total;            // 전체 게시글 총수
	private String battle_record_gym_name;		//크루전 암벽장 이름
	private String battle_record_gym_location;	//크루전 암벽장 지역
	private String battle_record_game_date;	//크루전 날짜
	private String battle_record_crew_leader; //해당 크루의 크루장
	private String battle_record_crew_name;	//해당 크루의 이름
	private String battle_record_crew_profile; //해당 크루의 사진
	private String battle_record_searchKeyword; // 사용자 텍스트 기반검색
	private String battle_record_condition;      // 개발자 데이터 검색

	public int getBattle_record_num() {
		return battle_record_num;
	}

	public void setBattle_record_num(int battle_record_num) {
		this.battle_record_num = battle_record_num;
	}

	public int getBattle_record_battle_num() {
		return battle_record_battle_num;
	}

	public void setBattle_record_battle_num(int battle_record_battle_num) {
		this.battle_record_battle_num = battle_record_battle_num;
	}

	public int getBattle_record_crew_num() {
		return battle_record_crew_num;
	}

	public void setBattle_record_crew_num(int battle_record_crew_num) {
		this.battle_record_crew_num = battle_record_crew_num;
	}

	public String getBattle_record_is_winner() {
		return battle_record_is_winner;
	}

	public void setBattle_record_is_winner(String battle_record_is_winner) {
		this.battle_record_is_winner = battle_record_is_winner;
	}

	public String getBattle_record_mvp_id() {
		return battle_record_mvp_id;
	}

	public void setBattle_record_mvp_id(String battle_record_mvp_id) {
		this.battle_record_mvp_id = battle_record_mvp_id;
	}

	public int getBattle_record_gym_num() {
		return battle_record_gym_num;
	}

	public void setBattle_record_gym_num(int battle_record_gym_num) {
		this.battle_record_gym_num = battle_record_gym_num;
	}

	public int getBattle_record_total() {
		return battle_record_total;
	}

	public void setBattle_record_total(int battle_record_total) {
		this.battle_record_total = battle_record_total;
	}

	public String getBattle_record_gym_name() {
		return battle_record_gym_name;
	}

	public void setBattle_record_gym_name(String battle_record_gym_name) {
		this.battle_record_gym_name = battle_record_gym_name;
	}

	public String getBattle_record_gym_location() {
		return battle_record_gym_location;
	}

	public void setBattle_record_gym_location(String battle_record_gym_location) {
		this.battle_record_gym_location = battle_record_gym_location;
	}

	public String getBattle_record_game_date() {
		return battle_record_game_date;
	}

	public void setBattle_record_game_date(String battle_record_game_date) {
		this.battle_record_game_date = battle_record_game_date;
	}

	public String getBattle_record_crew_leader() {
		return battle_record_crew_leader;
	}

	public void setBattle_record_crew_leader(String battle_record_crew_leader) {
		this.battle_record_crew_leader = battle_record_crew_leader;
	}

	public String getBattle_record_crew_name() {
		return battle_record_crew_name;
	}

	public void setBattle_record_crew_name(String battle_record_crew_name) {
		this.battle_record_crew_name = battle_record_crew_name;
	}

	public String getBattle_record_crew_profile() {
		return battle_record_crew_profile;
	}

	public void setBattle_record_crew_profile(String battle_record_crew_profile) {
		this.battle_record_crew_profile = battle_record_crew_profile;
	}

	public String getBattle_record_searchKeyword() {
		return battle_record_searchKeyword;
	}

	public void setBattle_record_searchKeyword(String battle_record_searchKeyword) {
		this.battle_record_searchKeyword = battle_record_searchKeyword;
	}

	public String getBattle_record_condition() {
		return battle_record_condition;
	}

	public void setBattle_record_condition(String battle_record_condition) {
		this.battle_record_condition = battle_record_condition;
	}

	@Override
	public String toString() {
		return "Battle_recordDTO{" +
				"battle_record_num=" + battle_record_num +
				", battle_record_battle_num=" + battle_record_battle_num +
				", battle_record_crew_num=" + battle_record_crew_num +
				", battle_record_is_winner='" + battle_record_is_winner + '\'' +
				", battle_record_mvp_id='" + battle_record_mvp_id + '\'' +
				", battle_record_gym_num=" + battle_record_gym_num +
				", battle_record_total=" + battle_record_total +
				", battle_record_gym_name='" + battle_record_gym_name + '\'' +
				", battle_record_gym_location='" + battle_record_gym_location + '\'' +
				", battle_record_game_date='" + battle_record_game_date + '\'' +
				", battle_record_crew_leader='" + battle_record_crew_leader + '\'' +
				", battle_record_crew_name='" + battle_record_crew_name + '\'' +
				", battle_record_crew_profile='" + battle_record_crew_profile + '\'' +
				", battle_record_searchKeyword='" + battle_record_searchKeyword + '\'' +
				", battle_record_condition='" + battle_record_condition + '\'' +
				'}';
	}
}
