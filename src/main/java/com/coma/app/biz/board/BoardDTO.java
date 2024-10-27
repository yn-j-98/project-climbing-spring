package com.coma.app.biz.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
	private int board_num;          // 게시판 글 번호
	private String board_title;     // 게시판제목
	private String board_content;   // 글 내용
	private int board_cnt;          // 글 조회수
	private String board_location;  // 게시글의 위치 지역
	private String board_writer_id; // 작성자 FK
	
	//DTO에만 존재하는 데이터
	private int board_total;   //전체 커뮤니티 게시글 총 개수
	private String board_search_keyword;    // 전체 게시글 검색 목록
	private String board_search_content;       // 전체 게시글 검색 내용

	private int board_page; //페이지네이션 데이터
	
}