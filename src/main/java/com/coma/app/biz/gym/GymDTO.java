package com.coma.app.biz.gym;

public class GymDTO {
	private int gym_num;                // 암벽장 PK
	private String gym_name;            // 암벽장 이름
	private String gym_profile;         // 암벽장 사진 Url
	private String gym_description;     // 암벽장 설명
	private String gym_location;        // 암벽장의 위치
	private int gym_reservation_cnt; // 예약 가능 개수
	private int gym_price;           // 이용 가격
	
	//DTO에만 존재하는 데이터
	private String gym_condition;        // 개발자 데이터 검색
	private int page;
	private int gym_max_num; 		//페이지네이션 데이터
	private int gym_min_num; 		//페이지네이션 데이터
	private int gym_battle_num;		//현재 암벽장의 크루전 pk
	private String gym_battle_game_date;	//현재 암벽장의 크루전 게임날짜
	private int gym_total;		//암벽장 총 개수

	public int getGym_num() {
		return gym_num;
	}

	public void setGym_num(int gym_num) {
		this.gym_num = gym_num;
	}

	public String getGym_name() {
		return gym_name;
	}

	public void setGym_name(String gym_name) {
		this.gym_name = gym_name;
	}

	public String getGym_profile() {
		return gym_profile;
	}

	public void setGym_profile(String gym_profile) {
		this.gym_profile = gym_profile;
	}

	public String getGym_description() {
		return gym_description;
	}

	public void setGym_description(String gym_description) {
		this.gym_description = gym_description;
	}

	public String getGym_location() {
		return gym_location;
	}

	public void setGym_location(String gym_location) {
		this.gym_location = gym_location;
	}

	public int getGym_reservation_cnt() {
		return gym_reservation_cnt;
	}

	public void setGym_reservation_cnt(int gym_reservation_cnt) {
		this.gym_reservation_cnt = gym_reservation_cnt;
	}

	public int getGym_price() {
		return gym_price;
	}

	public void setGym_price(int gym_price) {
		this.gym_price = gym_price;
	}

	public String getGym_condition() {
		return gym_condition;
	}

	public void setGym_condition(String gym_condition) {
		this.gym_condition = gym_condition;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getGym_max_num() {
		return gym_max_num;
	}

	public void setGym_max_num(int gym_max_num) {
		this.gym_max_num = gym_max_num;
	}

	public int getGym_min_num() {
		return gym_min_num;
	}

	public void setGym_min_num(int gym_min_num) {
		this.gym_min_num = gym_min_num;
	}

	public int getGym_battle_num() {
		return gym_battle_num;
	}

	public void setGym_battle_num(int gym_battle_num) {
		this.gym_battle_num = gym_battle_num;
	}

	public String getGym_battle_game_date() {
		return gym_battle_game_date;
	}

	public void setGym_battle_game_date(String gym_battle_game_date) {
		this.gym_battle_game_date = gym_battle_game_date;
	}

	public int getGym_total() {
		return gym_total;
	}

	public void setGym_total(int gym_total) {
		this.gym_total = gym_total;
	}

	@Override
	public String toString() {
		return "GymDTO{" +
				"gym_num=" + gym_num +
				", gym_name='" + gym_name + '\'' +
				", gym_profile='" + gym_profile + '\'' +
				", gym_description='" + gym_description + '\'' +
				", gym_location='" + gym_location + '\'' +
				", gym_reservation_cnt=" + gym_reservation_cnt +
				", gym_price='" + gym_price + '\'' +
				", gym_condition='" + gym_condition + '\'' +
				", page=" + page +
				", gym_max_num=" + gym_max_num +
				", gym_min_num=" + gym_min_num +
				", gym_battle_num=" + gym_battle_num +
				", gym_battle_game_date='" + gym_battle_game_date + '\'' +
				", gym_total=" + gym_total +
				'}';
	}
}
