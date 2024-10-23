package com.coma.app.biz.board;

public class BoardDTO {
	private int model_board_num;          // 게시판 글 번호
	private String model_board_title;     // 게시판제목
	private String model_board_content;   // 글 내용
	private int model_board_cnt;          // 글 조회수
	private String model_board_location;  // 게시글의 위치 지역
	private String model_board_writer_id; // 작성자 FK
	
	//DTO에만 존재하는 데이터
	private int model_board_total;   //전체 커뮤니티 게시글 총 개수
	private int model_board_max_num; //페이지네이션 데이터
	private int model_board_min_num; //페이지네이션 데이터
	private String model_board_searchKeyword;    // 사용자 텍스트 기반 검색 FIXME
	private String model_board_condition;       // 개발자 데이터 검색 조건 지역, 작성자, 날짜 범위 기능이면 필요
	
	public int getModel_board_num() {
		return model_board_num;
	}
	public void setModel_board_num(int model_board_num) {
		this.model_board_num = model_board_num;
	}
	public String getModel_board_title() {
		return model_board_title;
	}
	public void setModel_board_title(String model_board_title) {
		this.model_board_title = model_board_title;
	}
	public String getModel_board_content() {
		return model_board_content;
	}
	public void setModel_board_content(String model_board_content) {
		this.model_board_content = model_board_content;
	}
	public int getModel_board_cnt() {
		return model_board_cnt;
	}
	public void setModel_board_cnt(int model_board_cnt) {
		this.model_board_cnt = model_board_cnt;
	}
	public String getModel_board_location() {
		return model_board_location;
	}
	public void setModel_board_location(String model_board_location) {
		this.model_board_location = model_board_location;
	}
	public String getModel_board_writer_id() {
		return model_board_writer_id;
	}
	public void setModel_board_writer_id(String model_board_writer_id) {
		this.model_board_writer_id = model_board_writer_id;
	}
	public int getModel_board_total() {
		return model_board_total;
	}
	public void setModel_board_total(int model_board_total) {
		this.model_board_total = model_board_total;
	}
	public int getModel_board_max_num() {
		return model_board_max_num;
	}
	public void setModel_board_max_num(int model_board_max_num) {
		this.model_board_max_num = model_board_max_num;
	}
	public int getModel_board_min_num() {
		return model_board_min_num;
	}
	public void setModel_board_min_num(int model_board_min_num) {
		this.model_board_min_num = model_board_min_num;
	}
	public String getModel_board_searchKeyword() {
		return model_board_searchKeyword;
	}
	public void setModel_board_searchKeyword(String model_board_searchKeyword) {
		this.model_board_searchKeyword = model_board_searchKeyword;
	}
	public String getModel_board_condition() {
		return model_board_condition;
	}
	public void setModel_board_condition(String model_board_condition) {
		this.model_board_condition = model_board_condition;
	}
	@Override
	public String toString() {
		return "BoardDTO [model_board_num=" + model_board_num + ", model_board_title=" + model_board_title
				+ ", model_board_content=" + model_board_content + ", model_board_cnt=" + model_board_cnt
				+ ", model_board_location=" + model_board_location + ", model_board_writer_id=" + model_board_writer_id
				+ ", model_board_total=" + model_board_total + ", model_board_max_num=" + model_board_max_num
				+ ", model_board_min_num=" + model_board_min_num + ", model_board_searchKeyword="
				+ model_board_searchKeyword + ", model_board_condition=" + model_board_condition + "]";
	}
	
}