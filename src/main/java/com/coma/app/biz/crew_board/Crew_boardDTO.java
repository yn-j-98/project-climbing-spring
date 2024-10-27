package com.coma.app.biz.crew_board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Crew_boardDTO {
	private int crew_board_num;            //크루 글 번호
	private String crew_board_writer_id;     //크루 글 작성자
	private String crew_board_content;    //크루 글 내용

	//DTO에만 존재하는 데이터
	private String crew_board_member_profile;	//사용자 프로필
	private String crew_board_writer_name;		//사용자 이름
}
