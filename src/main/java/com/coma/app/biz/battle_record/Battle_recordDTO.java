package com.coma.app.biz.battle_record;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
}
