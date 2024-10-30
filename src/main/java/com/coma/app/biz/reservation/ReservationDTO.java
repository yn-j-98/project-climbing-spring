package com.coma.app.biz.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationDTO {
	private String reservation_num;          // 예약번호
	private String reservation_date;        // 예약날짜 
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것!
	private int reservation_gym_num;      // 암벽장 FK
	private String reservation_member_id; // 예약 사용자 FK
	private int reservation_price; // 사용자 실제 결제 금액
	
	//DTO에만 존재하는 데이터 
	private int reservation_cnt;          // 예약인원
	private int reservation_point_status; // Y,N 개인 포인트 차감 사용여부 
	private int total;        // 예약 개수
	private int reservation_use_point;	// 예약 때 사용한 포인트
	private String reservation_gym_name;        // 예약 암벽장 이름
	private String reservation_month;	// 예약한 년도-월 // TODO 관리자 페이지
	private int page;	// 페이지네이션
	private String search_keyword;	// 예약 검색 목록
	private String search_content;	// 예약 검색 내용
	private int reservation_min_num;	// 페이지 네이션 시작 PK

}
