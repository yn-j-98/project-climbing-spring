package com.coma.app.biz.battle;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BattleDTO {
	private int battle_num;	 			        // 크루전 PK
	private int battle_gym_num; 		      	// 암벽장 번호 FK
	private String battle_registration_date; // 크루전 등록날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private String battle_game_date; 		    // 크루전의 실제 게임하는 날짜
	//FIXME SQL DATE형식이라 String으로 변환하여 줄것
	private String battle_status; // 크루전 활성화 상태 여부 (T,F)

	//DTO에만 존재하는 데이터
	private String battle_gym_name;	//크루전 참여크루 이름
	private String battle_gym_profile;	//크루전 참여크루 사진 url
	private String battle_gym_location;	//크루전 참여크루 지역
	private	String battle_crew_profile;		//크루전 참여 크루 프로필
	private	int battle_crew_num;		//크루전 참여 크루 번호
	private int battle_total;			       // 전체 게시글 총수
	private String battle_search_keyword;      // 크루전 검색목록
	private String battle_content;      // 크루전 검색내용

	private int battle_page;  		     // 페이지네이션 데이터
}
