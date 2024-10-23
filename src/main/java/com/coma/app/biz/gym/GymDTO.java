package com.coma.app.biz.gym;

public class GymDTO {
	private int model_gym_num;                // 암벽장 PK
	private String model_gym_name;            // 암벽장 이름
	private String model_gym_profile;         // 암벽장 사진 Url
	private String model_gym_description;     // 암벽장 설명
	private String model_gym_location;        // 암벽장의 위치
	private int model_gym_reservation_cnt; // 예약 가능 개수
	private int model_gym_price;           // 이용 가격
	
	//DTO에만 존재하는 데이터
	private String model_gym_condition;        // 개발자 데이터 검색
	private int model_gym_max_num; 		//페이지네이션 데이터
	private int model_gym_min_num; 		//페이지네이션 데이터
	private int model_gym_battle_num;		//현재 암벽장의 크루전 pk
	private String model_gym_battle_game_date;	//현재 암벽장의 크루전 게임날짜
	private int model_gym_total;		//암벽장 총 개수
	
	private int page;		//암벽장 페이지 네이션
	
	
	public int getModel_gym_price() {
		return model_gym_price;
	}
	public void setModel_gym_price(int model_gym_price) {
		this.model_gym_price = model_gym_price;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getModel_gym_max_num() {
		return model_gym_max_num;
	}
	public void setModel_gym_max_num(int model_gym_max_num) {
		this.model_gym_max_num = model_gym_max_num;
	}
	public int getModel_gym_min_num() {
		return model_gym_min_num;
	}
	public void setModel_gym_min_num(int model_gym_min_num) {
		this.model_gym_min_num = model_gym_min_num;
	}
	public int getModel_gym_num() {
		return model_gym_num;
	}
	public void setModel_gym_num(int model_gym_num) {
		this.model_gym_num = model_gym_num;
	}
	public String getModel_gym_name() {
		return model_gym_name;
	}
	public void setModel_gym_name(String model_gym_name) {
		this.model_gym_name = model_gym_name;
	}
	public String getModel_gym_profile() {
		return model_gym_profile;
	}
	public void setModel_gym_profile(String model_gym_profile) {
		this.model_gym_profile = model_gym_profile;
	}
	public String getModel_gym_description() {
		return model_gym_description;
	}
	public void setModel_gym_description(String model_gym_description) {
		this.model_gym_description = model_gym_description;
	}
	public String getModel_gym_location() {
		return model_gym_location;
	}
	public int getModel_gym_total() {
		return model_gym_total;
	}
	public void setModel_gym_total(int model_gym_total) {
		this.model_gym_total = model_gym_total;
	}
	public void setModel_gym_location(String model_gym_location) {
		this.model_gym_location = model_gym_location;
	}
	public int getModel_gym_reservation_cnt() {
		return model_gym_reservation_cnt;
	}
	public void setModel_gym_reservation_cnt(int model_gym_reservation_cnt) {
		this.model_gym_reservation_cnt = model_gym_reservation_cnt;
	}

	public int getModel_gym_battle_num() {
		return model_gym_battle_num;
	}
	public void setModel_gym_battle_num(int model_gym_battle_num) {
		this.model_gym_battle_num = model_gym_battle_num;
	}
	public String getModel_gym_battle_game_date() {
		return model_gym_battle_game_date;
	}
	public void setModel_gym_battle_game_date(String model_gym_battle_game_date) {
		this.model_gym_battle_game_date = model_gym_battle_game_date;
	}
	public String getModel_gym_condition() {
		return model_gym_condition;
	}
	public void setModel_gym_condition(String model_gym_condition) {
		this.model_gym_condition = model_gym_condition;
	}
	@Override
	public String toString() {
		return "GymDTO [model_gym_num=" + model_gym_num + ", model_gym_name=" + model_gym_name + ", model_gym_profile="
				+ model_gym_profile + ", model_gym_description=" + model_gym_description + ", model_gym_location="
				+ model_gym_location + ", model_gym_reservation_cnt=" + model_gym_reservation_cnt + ", model_gym_price="
				+ model_gym_price + ", model_gym_condition=" + model_gym_condition + ", model_gym_max_num="
				+ model_gym_max_num + ", model_gym_min_num=" + model_gym_min_num + ", model_gym_battle_num="
				+ model_gym_battle_num + ", model_gym_battle_game_date=" + model_gym_battle_game_date
				+ ", model_gym_total=" + model_gym_total + "]";
	}
	
	
}
