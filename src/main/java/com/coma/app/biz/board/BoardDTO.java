package com.coma.app.biz.board;

public class BoardDTO {
	private int board_num;          // 게시판 글 번호
	private String board_title;     // 게시판제목
	private String board_content;   // 글 내용
	private int board_cnt;          // 글 조회수
	private String board_location;  // 게시글의 위치 지역
	private String board_writer_id; // 작성자 FK
	
	//DTO에만 존재하는 데이터
	private int board_total;   //전체 커뮤니티 게시글 총 개수
	private int board_max_num; //페이지네이션 데이터
	private int board_min_num; //페이지네이션 데이터
	private String board_searchKeyword;    // 사용자 텍스트 기반 검색 FIXME
	private String board_condition;       // 개발자 데이터 검색 조건 지역, 작성자, 날짜 범위 기능이면 필요

	private int page; //페이지네이션 데이터
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int Board_num) {
		this.board_num = Board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String Board_title) {
		this.board_title = Board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String Board_content) {
		this.board_content = Board_content;
	}
	public int getBoard_cnt() {
		return board_cnt;
	}
	public void setBoard_cnt(int Board_cnt) {
		this.board_cnt = Board_cnt;
	}
	public String getBoard_location() {
		return board_location;
	}
	public void setBoard_location(String Board_location) {
		this.board_location = Board_location;
	}
	public String getBoard_writer_id() {
		return board_writer_id;
	}
	public void setBoard_writer_id(String Board_writer_id) {
		this.board_writer_id = Board_writer_id;
	}
	public int getBoard_total() {
		return board_total;
	}
	public void setBoard_total(int Board_total) {
		this.board_total = Board_total;
	}
	public int getBoard_max_num() {
		return board_max_num;
	}
	public void setBoard_max_num(int Board_max_num) {
		this.board_max_num = Board_max_num;
	}
	public int getBoard_min_num() {
		return board_min_num;
	}
	public void setBoard_min_num(int Board_min_num) {
		this.board_min_num = Board_min_num;
	}
	public String getBoard_searchKeyword() {
		return board_searchKeyword;
	}
	public void setBoard_searchKeyword(String Board_searchKeyword) {
		this.board_searchKeyword = Board_searchKeyword;
	}
	public String getBoard_condition() {
		return board_condition;
	}
	public void setBoard_condition(String Board_condition) {
		this.board_condition = Board_condition;
	}
	@Override
	public String toString() {
		return "BoardDTO [Board_num=" + board_num + ", Board_title=" + board_title
				+ ", Board_content=" + board_content + ", Board_cnt=" + board_cnt
				+ ", Board_location=" + board_location + ", Board_writer_id=" + board_writer_id
				+ ", Board_total=" + board_total + ", Board_max_num=" + board_max_num
				+ ", Board_min_num=" + board_min_num + ", Board_searchKeyword="
				+ board_searchKeyword + ", Board_condition=" + board_condition + ", page=" + page
				+ "]";
	}
	
}