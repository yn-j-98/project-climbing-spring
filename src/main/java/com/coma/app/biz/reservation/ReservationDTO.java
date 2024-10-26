package com.coma.app.biz.reservation;


public class ReservationDTO {
	private int reservation_num;          // 예약번호
	private String reservation_date;        // 예약날짜 
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것!
	private int reservation_gym_num;      // 암벽장 FK
	private String reservation_member_id; // 예약 사용자 FK
	private int reservation_price; // 사용자 실제 결제 금액
	
	//DTO에만 존재하는 데이터 
	private int reservation_cnt;          // 예약인원
	private int reservation_point_status; // Y,N 개인 포인트 차감 사용여부 
	private int reservation_total;        // 예약 개수
	private String reservation_gym_name;        // 예약 암벽장 이름
	private int reservation_max_num;      // 페이지네이션 데이터
	private int reservation_min_num;      // 페이지네이션 데이터
	private String reservation_condition; // 개발자 데이터 검색
	private String reservation_month;	// 예약한 년도-월 // TODO 관리자 페이지

	public int getReservation_num() {
		return reservation_num;
	}

	public void setReservation_num(int reservation_num) {
		this.reservation_num = reservation_num;
	}

	public String getReservation_date() {
		return reservation_date;
	}

	public void setReservation_date(String reservation_date) {
		this.reservation_date = reservation_date;
	}

	public int getReservation_gym_num() {
		return reservation_gym_num;
	}

	public void setReservation_gym_num(int reservation_gym_num) {
		this.reservation_gym_num = reservation_gym_num;
	}

	public String getReservation_member_id() {
		return reservation_member_id;
	}

	public void setReservation_member_id(String reservation_member_id) {
		this.reservation_member_id = reservation_member_id;
	}

	public int getReservation_price() {
		return reservation_price;
	}

	public void setReservation_price(int reservation_price) {
		this.reservation_price = reservation_price;
	}

	public int getReservation_cnt() {
		return reservation_cnt;
	}

	public void setReservation_cnt(int reservation_cnt) {
		this.reservation_cnt = reservation_cnt;
	}

	public int getReservation_point_status() {
		return reservation_point_status;
	}

	public void setReservation_point_status(int reservation_point_status) {
		this.reservation_point_status = reservation_point_status;
	}

	public int getReservation_total() {
		return reservation_total;
	}

	public void setReservation_total(int reservation_total) {
		this.reservation_total = reservation_total;
	}

	public String getReservation_gym_name() {
		return reservation_gym_name;
	}

	public void setReservation_gym_name(String reservation_gym_name) {
		this.reservation_gym_name = reservation_gym_name;
	}

	public int getReservation_max_num() {
		return reservation_max_num;
	}

	public void setReservation_max_num(int reservation_max_num) {
		this.reservation_max_num = reservation_max_num;
	}

	public int getReservation_min_num() {
		return reservation_min_num;
	}

	public void setReservation_min_num(int reservation_min_num) {
		this.reservation_min_num = reservation_min_num;
	}

	public String getReservation_condition() {
		return reservation_condition;
	}

	public void setReservation_condition(String reservation_condition) {
		this.reservation_condition = reservation_condition;
	}

	public String getReservation_month() {
		return reservation_month;
	}

	public void setReservation_month(String reservation_month) {
		this.reservation_month = reservation_month;
	}

	@Override
	public String toString() {
		return "ReservationDTO{" +
				"reservation_num=" + reservation_num +
				", reservation_date='" + reservation_date + '\'' +
				", reservation_gym_num=" + reservation_gym_num +
				", reservation_member_id='" + reservation_member_id + '\'' +
				", reservation_price=" + reservation_price +
				", reservation_cnt=" + reservation_cnt +
				", reservation_point_status=" + reservation_point_status +
				", reservation_total=" + reservation_total +
				", reservation_gym_name='" + reservation_gym_name + '\'' +
				", reservation_max_num=" + reservation_max_num +
				", reservation_min_num=" + reservation_min_num +
				", reservation_condition='" + reservation_condition + '\'' +
				", reservation_month='" + reservation_month + '\'' +
				'}';
	}
}
