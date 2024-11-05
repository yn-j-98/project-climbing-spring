package com.coma.app.biz.crew;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrewDTO {
	private int crew_num;             //크루 번호
	private String crew_name;	        //크루 이름
	private String crew_description;	//크루 설명
	private int crew_max_member_size; //크루 최대 인원수
	private String crew_leader;	    //크루 리더
	private String crew_battle_status; //크루전 신청여부 T,F(char1)
	private String crew_profile; 		//크루 이미지 url
	
	//DTO에만 존재하는 데이터 
	private int total;  //현재 크루 개수
	private int crew_current_member_size; //크루의 현재 인원수
	private int crew_min_num;
	private int crew_battle_num;

	private int page; //크루의 현재 인원수
}
