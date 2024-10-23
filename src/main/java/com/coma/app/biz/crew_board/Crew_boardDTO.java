package com.coma.app.biz.crew_board;

public class Crew_boardDTO {
	private int crew_board_num;            //크루 글 번호
	private String crew_board_writer_id;     //크루 글 작성자
	private String crew_board_content;    //크루 글 내용
	private String crew_board_title;      //크루 글 제목
	private int crew_board_cnt; 		//크루 글 조회수
	//DTO에만 존재하는 데이터 
	private String crew_board_member_profile;	//사용자 프로필
	private int crew_board_total;		      // 전체 커뮤니티 게시글 총 개수
	private int crew_board_max_num; 	    // 페이지네이션 데이터
	private int crew_board_min_num;	      // 페이지네이션 데이터
	private String crew_board_condition;  // 개발자 데이터

	public int getCrew_board_num() {
		return crew_board_num;
	}

	public void setCrew_board_num(int crew_board_num) {
		this.crew_board_num = crew_board_num;
	}

	public String getCrew_board_writer_id() {
		return crew_board_writer_id;
	}

	public void setCrew_board_writer_id(String crew_board_writer_id) {
		this.crew_board_writer_id = crew_board_writer_id;
	}

	public String getCrew_board_content() {
		return crew_board_content;
	}

	public void setCrew_board_content(String crew_board_content) {
		this.crew_board_content = crew_board_content;
	}

	public String getCrew_board_title() {
		return crew_board_title;
	}

	public void setCrew_board_title(String crew_board_title) {
		this.crew_board_title = crew_board_title;
	}

	public int getCrew_board_cnt() {
		return crew_board_cnt;
	}

	public void setCrew_board_cnt(int crew_board_cnt) {
		this.crew_board_cnt = crew_board_cnt;
	}

	public String getCrew_board_member_profile() {
		return crew_board_member_profile;
	}

	public void setCrew_board_member_profile(String crew_board_member_profile) {
		this.crew_board_member_profile = crew_board_member_profile;
	}

	public int getCrew_board_total() {
		return crew_board_total;
	}

	public void setCrew_board_total(int crew_board_total) {
		this.crew_board_total = crew_board_total;
	}

	public int getCrew_board_max_num() {
		return crew_board_max_num;
	}

	public void setCrew_board_max_num(int crew_board_max_num) {
		this.crew_board_max_num = crew_board_max_num;
	}

	public int getCrew_board_min_num() {
		return crew_board_min_num;
	}

	public void setCrew_board_min_num(int crew_board_min_num) {
		this.crew_board_min_num = crew_board_min_num;
	}

	public String getCrew_board_condition() {
		return crew_board_condition;
	}

	public void setCrew_board_condition(String crew_board_condition) {
		this.crew_board_condition = crew_board_condition;
	}

	@Override
	public String toString() {
		return "Crew_boardDTO{" +
				"crew_board_num=" + crew_board_num +
				", crew_board_writer_id='" + crew_board_writer_id + '\'' +
				", crew_board_content='" + crew_board_content + '\'' +
				", crew_board_title='" + crew_board_title + '\'' +
				", crew_board_cnt=" + crew_board_cnt +
				", crew_board_member_profile='" + crew_board_member_profile + '\'' +
				", crew_board_total=" + crew_board_total +
				", crew_board_max_num=" + crew_board_max_num +
				", crew_board_min_num=" + crew_board_min_num +
				", crew_board_condition='" + crew_board_condition + '\'' +
				'}';
	}
}
