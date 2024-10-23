package com.coma.app.biz.reservation;


public class ReservationDTO {
	private int model_reservation_num;          // 예약번호
	private String model_reservation_date;        // 예약날짜 
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private int model_reservation_gym_num;      // 암벽장 FK
	private String model_reservation_member_id; // 예약 사용자 FK
	private int model_reservation_price; // 사용자 실제 결제 금액
	
	//DTO에만 존재하는 데이터 
	private int model_reservation_cnt;          // 예약인원
	private int model_reservation_point_status; // Y,N 개인 포인트 차감 사용여부 
	private int model_reservation_total;        // 예약 개수
	private String model_reservation_gym_name;        // 예약 암벽장 이름
	private int model_reservation_max_num;      // 페이지네이션 데이터
	private int model_reservation_min_num;      // 페이지네이션 데이터
	private String model_reservation_condition; // 개발자 데이터 검색 
	

	public String getModel_reservation_gym_name() {
		return model_reservation_gym_name;
	}
	public void setModel_reservation_gym_name(String model_reservation_gym_name) {
		this.model_reservation_gym_name = model_reservation_gym_name;
	}
	public int getModel_reservation_num() {
		return model_reservation_num;
	}
	public void setModel_reservation_num(int model_reservation_num) {
		this.model_reservation_num = model_reservation_num;
	}
	public String getModel_reservation_date() {
		return model_reservation_date;
	}
	public void setModel_reservation_date(String model_reservation_date) {
		this.model_reservation_date = model_reservation_date;
	}
	public int getModel_reservation_gym_num() {
		return model_reservation_gym_num;
	}
	public void setModel_reservation_gym_num(int model_reservation_gym_num) {
		this.model_reservation_gym_num = model_reservation_gym_num;
	}
	public String getModel_reservation_member_id() {
		return model_reservation_member_id;
	}
	public void setModel_reservation_member_id(String model_reservation_member_id) {
		this.model_reservation_member_id = model_reservation_member_id;
	}
	public int getModel_reservation_price() {
		return model_reservation_price;
	}
	public void setModel_reservation_price(int model_reservation_price) {
		this.model_reservation_price = model_reservation_price;
	}
	public int getModel_reservation_cnt() {
		return model_reservation_cnt;
	}
	public void setModel_reservation_cnt(int model_reservation_cnt) {
		this.model_reservation_cnt = model_reservation_cnt;
	}
	public int getModel_reservation_point_status() {
		return model_reservation_point_status;
	}
	public void setModel_reservation_point_status(int model_reservation_point_status) {
		this.model_reservation_point_status = model_reservation_point_status;
	}
	public int getModel_reservation_total() {
		return model_reservation_total;
	}
	public void setModel_reservation_total(int model_reservation_total) {
		this.model_reservation_total = model_reservation_total;
	}
	public int getModel_reservation_max_num() {
		return model_reservation_max_num;
	}
	public void setModel_reservation_max_num(int model_reservation_max_num) {
		this.model_reservation_max_num = model_reservation_max_num;
	}
	public int getModel_reservation_min_num() {
		return model_reservation_min_num;
	}
	public void setModel_reservation_min_num(int model_reservation_min_num) {
		this.model_reservation_min_num = model_reservation_min_num;
	}
	public String getModel_reservation_condition() {
		return model_reservation_condition;
	}
	public void setModel_reservation_condition(String model_reservation_condition) {
		this.model_reservation_condition = model_reservation_condition;
	}
	@Override
	public String toString() {
		return "ReservationDTO [model_reservation_num=" + model_reservation_num + ", model_reservation_date="
				+ model_reservation_date + ", model_reservation_gym_num=" + model_reservation_gym_num
				+ ", model_reservation_member_id=" + model_reservation_member_id + ", model_reservation_price="
				+ model_reservation_price + ", model_reservation_cnt=" + model_reservation_cnt
				+ ", model_reservation_point_status=" + model_reservation_point_status + ", model_reservation_total="
				+ model_reservation_total + ", model_reservation_gym_name=" + model_reservation_gym_name
				+ ", model_reservation_max_num=" + model_reservation_max_num + ", model_reservation_min_num="
				+ model_reservation_min_num + ", model_reservation_condition=" + model_reservation_condition + "]";
	}
	
	
}
