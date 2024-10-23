package com.coma.app.biz.crew_board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class Crew_boardDAO {
	//(페이지 네이션) 특정 크루 글 출력 CREW_BOARD_WRITER_ID, Crew_board_min_num, Crew_board_max_num
	private final String ALL_CREW_BOARD = "SELECT CB.CREW_BOARD_NUM, CB.CREW_BOARD_WRITER_ID, CB.CREW_BOARD_TITLE, " +
			"CB.CREW_BOARD_CONTENT, CB.CREW_BOARD_CNT, M.MEMBER_PROFILE " +
			"FROM CREW_BOARD CB " +
			"JOIN MEMBER M ON M.MEMBER_ID = CB.CREW_BOARD_WRITER_ID " +
			"JOIN CREW C ON M.MEMBER_CREW_NUM = C.CREW_NUM " +
			"WHERE C.CREW_NUM = ( " +
			"    SELECT MM.MEMBER_CREW_NUM " +
			"    FROM MEMBER MM " +
			"    WHERE MM.MEMBER_ID = ? " +
			") " +
			"ORDER BY CB.CREW_BOARD_NUM DESC " +
			"LIMIT ?,?";


	//해당 크루 커뮤니티 전체글 총 개수 CREW_BOARD_WRITER_ID
	private final String ONE_COUNT = "SELECT COUNT(*) AS CREW_BOARD_TOTAL " +
			"FROM CREW_BOARD CB " +
			"JOIN MEMBER M ON M.MEMBER_ID = CB.CREW_BOARD_WRITER_ID " +
			"JOIN CREW C ON M.MEMBER_CREW_NUM = C.CREW_NUM " +
			"WHERE C.CREW_NUM = ( " +
			"    SELECT MM.MEMBER_CREW_NUM " +
			"    FROM MEMBER MM " +
			"    WHERE MM.MEMBER_ID = ? " +
			")";


	//크루 게시판 글 작성 CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_TITLE
	private final String INSERT = "INSERT INTO CREW_BOARD(CREW_BOARD_NUM, CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_TITLE) " +
			"VALUES ((SELECT IFNULL(MAX(CREW_BOARD_NUM), 0) + 1 FROM CREW_BOARD), ?, ?, ?)";

	//크루 게시판 글 삭제 CREW_NUM
	private final String DELETE = "DELETE FROM CREW_BOARD WHERE CREW_BOARD_NUM = ?";

	//PK로 검색 CREW_BOARD_NUM
	private final String ONE = "SELECT CREW_BOARD_NUM, CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_CNT FROM CREW_BOARD WHERE CREW_BOARD_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체
	
	public boolean insert(Crew_boardDTO crew_boardDTO) {//기능 미구현
		System.out.println("com.coma.app.biz.crew_board.insert 시작");
		//크루 게시판 글 작성 CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_TITLE
		int result=jdbcTemplate.update(INSERT,crew_boardDTO.getCrew_board_writer_id(),crew_boardDTO.getCrew_board_content(),crew_boardDTO.getCrew_board_title());
		if(result<=0) {
			System.out.println("com.coma.app.biz.crew_board.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.crew_board.insert 성공");
		return true;
	}

	private boolean update(Crew_boardDTO crew_boardDTO) { // TODO 여기없는 CRUD
		System.out.println("com.coma.app.biz.crew_board.update 시작");
		System.out.println("com.coma.app.biz.crew_board.update 성공");
		return true;
	}

	public boolean delete(Crew_boardDTO crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.delete 시작");
		//크루 게시판 글 삭제 CREW_NUM
		int result=jdbcTemplate.update(DELETE,crew_boardDTO.getCrew_board_num());
		if(result<=0) {
			System.out.println("com.coma.app.biz.crew_board.delete SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.crew_board.delete 성공");
		return true;
	}

	public Crew_boardDTO selectOne(Crew_boardDTO crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.selectOne 시작");

		Crew_boardDTO data = null;
		Object[] args={crew_boardDTO.getCrew_board_num()};
		try {
			//PK로 검색 CREW_BOARD_NUM
			data=jdbcTemplate.queryForObject(ONE,args,new CrewBoardRowMapperOne());
		}
		catch(Exception e) {
			System.out.println("com.coma.app.biz.crew_board.selectOne SQL문 실패");
		}
		System.out.println("com.coma.app.biz.crew_board.selectOne 성공");
		return data;
	}

	public Crew_boardDTO selectOneCount(Crew_boardDTO crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.selectOne 시작");

		Crew_boardDTO data = null;
		Object[] args={crew_boardDTO.getCrew_board_writer_id()};
		try {
			//해당 크루 커뮤니티 전체글 총 개수 CREW_BOARD_WRITER_ID
			data=jdbcTemplate.queryForObject(ONE_COUNT,args,new CrewBoardCountRowMapperOne());
		}
		catch(Exception e) {
			System.out.println("com.coma.app.biz.crew_board.selectOne SQL문 실패");
		}
		System.out.println("com.coma.app.biz.crew_board.selectOne 성공");
		return data;
	}

	public List<Crew_boardDTO> selectAll(Crew_boardDTO crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.selectAll 시작");

		List<Crew_boardDTO> datas = null;
		Object[] args={crew_boardDTO.getCrew_board_min_num(),10,crew_boardDTO.getCrew_board_writer_id()};
		try {
			//(페이지 네이션) 특정 크루 글 출력 CREW_BOARD_WRITER_ID, Crew_board_min_num, Crew_board_max_num
			datas=jdbcTemplate.query(ALL_CREW_BOARD, args, new CrewBoardRowMapperAll());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.crew_board.selectAll SQL문 실패");
		}
		System.out.println("com.coma.app.biz.crew_board.selectAll 성공");
		return datas;
	}
}

class CrewBoardRowMapperOne implements RowMapper<Crew_boardDTO> {

	public Crew_boardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		Crew_boardDTO crew_boardDTO=new Crew_boardDTO();
		System.out.print("GymCountRowMapper DB에서 가져온 데이터 {");
		crew_boardDTO.setCrew_board_num(rs.getInt("CREW_BOARD_NUM"));
		System.err.println("crew_board_num = ["+crew_boardDTO.getCrew_board_num()+"]");
		crew_boardDTO.setCrew_board_writer_id(rs.getString("CREW_BOARD_WRITER_ID"));
		System.err.println("crew_board_writer_id = ["+crew_boardDTO.getCrew_board_writer_id()+"]");
		crew_boardDTO.setCrew_board_content(rs.getString("CREW_BOARD_CONTENT"));
		System.err.println("crew_board_content = ["+crew_boardDTO.getCrew_board_content()+"]");
		crew_boardDTO.setCrew_board_cnt(rs.getInt("CREW_BOARD_CNT"));
		System.err.print("crew_board_cnt = ["+crew_boardDTO.getCrew_board_cnt()+"]");
		System.out.println("}");
		return crew_boardDTO;
	};
}

class CrewBoardCountRowMapperOne implements RowMapper<Crew_boardDTO> {

	public Crew_boardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		Crew_boardDTO crew_boardDTO=new Crew_boardDTO();
		System.out.print("GymCountRowMapper DB에서 가져온 데이터 {");
		crew_boardDTO.setCrew_board_total(rs.getInt("CREW_BOARD_TOTAL"));
		System.err.print("crew_board_total = ["+crew_boardDTO.getCrew_board_total()+"]");
		System.out.println("}");
		return crew_boardDTO;
	};
}

class CrewBoardRowMapperAll implements RowMapper<Crew_boardDTO> {

	public Crew_boardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		Crew_boardDTO crew_boardDTO=new Crew_boardDTO();
		System.out.print("GymCountRowMapper DB에서 가져온 데이터 {");
		crew_boardDTO.setCrew_board_num(rs.getInt("CREW_BOARD_NUM"));
		System.err.println("crew_board_num = ["+crew_boardDTO.getCrew_board_num()+"]");
		crew_boardDTO.setCrew_board_writer_id(rs.getString("CREW_BOARD_WRITER_ID"));
		System.err.println("crew_board_writer_id = ["+crew_boardDTO.getCrew_board_writer_id()+"]");
		crew_boardDTO.setCrew_board_title(rs.getString("CREW_BOARD_TITLE"));
		System.err.println("crew_board_title = ["+crew_boardDTO.getCrew_board_title()+"]");
		crew_boardDTO.setCrew_board_content(rs.getString("CREW_BOARD_CONTENT"));
		System.err.println("crew_board_content = ["+crew_boardDTO.getCrew_board_content()+"]");
		crew_boardDTO.setCrew_board_cnt(rs.getInt("CREW_BOARD_CNT"));
		System.err.println("crew_board_cnt = ["+crew_boardDTO.getCrew_board_cnt()+"]");
		crew_boardDTO.setCrew_board_member_profile(rs.getString("MEMBER_PROFILE"));
		System.err.print("crew_board_member_profile = ["+crew_boardDTO.getCrew_board_member_profile()+"]");
		System.out.println("}");
		return crew_boardDTO;
	};
}
