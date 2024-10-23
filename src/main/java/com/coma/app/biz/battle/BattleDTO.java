package com.coma.app.biz.battle;


public class BattleDTO {
	private int battle_num;	 			        // 크루전 PK
	private int battle_gym_num; 		      	// 암벽장 번호 FK
	private String battle_registration_date; // 크루전 등록날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private String battle_game_date; 		    // 크루전의 실제 게임하는 날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것

	//DTO에만 존재하는 데이터
	private String battle_gym_name;	//크루전 참여크루 이름
	private String battle_gym_profile;	//크루전 참여크루 사진 url
	private String battle_gym_location;	//크루전 참여크루 지역
	private	String battle_crew_profile;		//크루전 참여 크루 프로필
	private	int battle_crew_num;		//크루전 참여 크루 번호
	private int battle_total;			       // 전체 게시글 총수
	private int battle_min_num;  		     // 페이지네이션 데이터
	private String battle_condition;      // 개발자 데이터 검색

	private int page;  		     // 페이지네이션 데이터

	public int getBattle_num() {
		return battle_num;
	}

	public void setBattle_num(int battle_num) {
		this.battle_num = battle_num;
	}

	public int getBattle_gym_num() {
		return battle_gym_num;
	}

	public void setBattle_gym_num(int battle_gym_num) {
		this.battle_gym_num = battle_gym_num;
	}

	public String getBattle_registration_date() {
		return battle_registration_date;
	}

	public void setBattle_registration_date(String battle_registration_date) {
		this.battle_registration_date = battle_registration_date;
	}

	public String getBattle_game_date() {
		return battle_game_date;
	}

	public void setBattle_game_date(String battle_game_date) {
		this.battle_game_date = battle_game_date;
	}

	public String getBattle_gym_name() {
		return battle_gym_name;
	}

	public void setBattle_gym_name(String battle_gym_name) {
		this.battle_gym_name = battle_gym_name;
	}

	public String getBattle_gym_profile() {
		return battle_gym_profile;
	}

	public void setBattle_gym_profile(String battle_gym_profile) {
		this.battle_gym_profile = battle_gym_profile;
	}

	public String getBattle_gym_location() {
		return battle_gym_location;
	}

	public void setBattle_gym_location(String battle_gym_location) {
		this.battle_gym_location = battle_gym_location;
	}

	public String getBattle_crew_profile() {
		return battle_crew_profile;
	}

	public void setBattle_crew_profile(String battle_crew_profile) {
		this.battle_crew_profile = battle_crew_profile;
	}

	public int getBattle_crew_num() {
		return battle_crew_num;
	}

	public void setBattle_crew_num(int battle_crew_num) {
		this.battle_crew_num = battle_crew_num;
	}

	public int getBattle_total() {
		return battle_total;
	}

	public void setBattle_total(int battle_total) {
		this.battle_total = battle_total;
	}

	public int getBattle_min_num() {
		return battle_min_num;
	}

	public void setBattle_min_num(int battle_min_num) {
		this.battle_min_num = battle_min_num;
	}

	public String getBattle_condition() {
		return battle_condition;
	}

	public void setBattle_condition(String battle_condition) {
		this.battle_condition = battle_condition;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "BattleDTO{" +
				"battle_num=" + battle_num +
				", battle_gym_num=" + battle_gym_num +
				", battle_registration_date='" + battle_registration_date + '\'' +
				", battle_game_date='" + battle_game_date + '\'' +
				", battle_gym_name='" + battle_gym_name + '\'' +
				", battle_gym_profile='" + battle_gym_profile + '\'' +
				", battle_gym_location='" + battle_gym_location + '\'' +
				", battle_crew_profile='" + battle_crew_profile + '\'' +
				", battle_crew_num=" + battle_crew_num +
				", battle_total=" + battle_total +
				", battle_min_num=" + battle_min_num +
				", battle_condition='" + battle_condition + '\'' +
				", page=" + page +
				'}';
	}
}
