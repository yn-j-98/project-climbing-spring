package com.coma.app.biz.gym;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GymDTO {
	private int gym_num;                // 암벽장 PK
	private String gym_name;            // 암벽장 이름
	private String gym_profile;         // 암벽장 사진 Url
	private String gym_description;     // 암벽장 설명
	private String gym_location;        // 암벽장의 위치
	private int gym_reservation_cnt; // 예약 가능 개수
	private int gym_price;           // 이용 가격
	private String gym_admin_battle_verified; // 관리자 승인 여부
	
	//DTO에만 존재하는 데이터
	private String gym_search_keyword;        // 암벽장 검색 목록
	private String gym_search_content;        // 암벽장 검색 내용
	private int page;				//페이지 네이션
	private int gym_battle_num;		//현재 암벽장의 크루전 pk
	private String gym_battle_game_date;	//현재 암벽장의 크루전 게임날짜
	private int total;		//암벽장 총 개수
}
