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
	//(페이지 네이션) 특정 크루 글 출력 model_crew_board_min_num, model_crew_board_max_num, CREW_BOARD_WRITER_ID
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
	private final String INSERT = "INSERT INTO CREW_BOARD (CREW_BOARD_NUM, CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_TITLE)\n" +
			"    SELECT IFNULL(MAX(CREW_BOARD_NUM), 0) + 1, ?, ?, ? \n" +
			"    FROM CREW_BOARD";

	//크루 게시판 글 삭제 CREW_NUM
	private final String DELETE = "DELETE FROM CREW_BOARD WHERE CREW_BOARD_NUM = ?";

	//PK로 검색 CREW_BOARD_NUM
	private final String ONE = "SELECT CREW_BOARD_NUM, CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT, CREW_BOARD_CNT FROM CREW_BOARD WHERE CREW_BOARD_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(Crew_boardDTO crew_boardDTO) {//기능 미구현
		System.out.println("com.coma.app.biz.crew_board.insert 시작");
		int result = jdbcTemplate.update(INSERT, crew_boardDTO.getCrew_board_writer_id(), crew_boardDTO.getCrew_board_content(), crew_boardDTO.getCrew_board_title());
		if (result <= 0) {
			System.err.println("	[에러]com.coma.app.biz.crew_board.insert sql 실패 : insert = " + INSERT);
			return false;
		}
		return true;
	}
	public boolean update(Crew_boardDTO crew_boardDTO) {//기능 미구현
		System.out.println("com.coma.app.biz.crew_board.update 시작");
		System.out.println("com.coma.app.biz.crew_board.update 성공");
		return true;
	}
	public boolean delete(Crew_boardDTO crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.delete 시작");
		int result = jdbcTemplate.update(DELETE, crew_boardDTO.getCrew_board_num());
		if (result <= 0) {
			System.err.println("	[에러]com.coma.app.biz.crew_board.delete sql 실패 : UPDATE = " + DELETE);
			return false;
		}
		return true;
	}

	//PK로 검색 CREW_BOARD_NUM
	public Crew_boardDTO selectOne(Crew_boardDTO crew_boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchIdCount 시작");
		Crew_boardDTO result = null;
		Object[] args = {crew_boardDTO.getCrew_board_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE, args, new crewBoardRowMapperOne());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchIdCount 실패 " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	//해당 크루 커뮤니티 전체글 총 개수 CREW_BOARD_WRITER_ID
	public Crew_boardDTO selectOneCount(Crew_boardDTO Crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.selectOneCount 시작");
		Crew_boardDTO result = null;
		Object[] args = {Crew_boardDTO.getCrew_board_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT, args, new crewBoardRowMapperOneCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.crew_board.selectOneCount 실패 " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	//(페이지 네이션) 특정 크루 글 출력 Crew_board_min_num, Crew_board_max_num, Crew_board_writer_id
	public List<Crew_boardDTO> selectAllCrewBoard(Crew_boardDTO Crew_boardDTO) {
		System.out.println("com.coma.app.biz.crew_board.selectAllCrewBoard 시작");
		List<Crew_boardDTO> result = null;
		int minNum = Crew_boardDTO.getCrew_board_min_num();
		int offset = minNum; //페이지네이션 시작위치
		Object[] args = {offset, 10, Crew_boardDTO.getCrew_board_writer_id()};
		try {
			result = jdbcTemplate.query(ALL_CREW_BOARD, args, new crewBoardRowAllCrewBoard());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.crew_board.selectAllCrewBoard 실패 " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}


}

class crewBoardRowMapperOne implements RowMapper<Crew_boardDTO> {
	@Override
	public Crew_boardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew_board.selectOne 검색 성공");
		Crew_boardDTO crew_boardDTO = new Crew_boardDTO();
		try {
			crew_boardDTO.setCrew_board_num(resultSet.getInt("CREW_BOARD_NUM"));
		} catch (Exception e) {
			System.err.println("Board_num = 0");
			crew_boardDTO.setCrew_board_num(0);
		}
		try {
			crew_boardDTO.setCrew_board_writer_id(resultSet.getString("CREW_BOARD_WRITER_ID"));
		} catch (Exception e) {
			System.err.println("Crew_board_writer_id = null");
			crew_boardDTO.setCrew_board_writer_id(null);
		}
		try {
			crew_boardDTO.setCrew_board_content(resultSet.getString("CREW_BOARD_CONTENT"));
		} catch (Exception e) {
			System.err.println("Crew_board_content = null");
			crew_boardDTO.setCrew_board_content(null);
		}
		try {
			crew_boardDTO.setCrew_board_cnt(resultSet.getInt("CREW_BOARD_CNT"));
		} catch (Exception e) {
			System.err.println("Crew_board_cnt = 0");
			crew_boardDTO.setCrew_board_cnt(0);
		}
		return crew_boardDTO;
	}
}

class crewBoardRowMapperOneCount implements RowMapper<Crew_boardDTO> {
	@Override
	public Crew_boardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew_board.selectOneCount 검색 성공");
		Crew_boardDTO crew_boardDTO = new Crew_boardDTO();
		try{
			crew_boardDTO.setTotal(resultSet.getInt("CREW_BOARD_TOTAL"));
		}catch (Exception e){
			System.err.println("Crew_board_total = null");
			crew_boardDTO.setTotal(0);
		}
		return crew_boardDTO;
	}
}

class crewBoardRowAllCrewBoard implements RowMapper<Crew_boardDTO> {
	@Override
	public Crew_boardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew_board.selectAllCrewBoard 검색 성공");
		Crew_boardDTO crew_boardDTO = new Crew_boardDTO();
		try {
			crew_boardDTO.setCrew_board_num(resultSet.getInt("CREW_BOARD_NUM"));
		} catch (Exception e) {
			System.err.println("Board_num = 0");
			crew_boardDTO.setCrew_board_num(0);
		}
		try {
			crew_boardDTO.setCrew_board_writer_id(resultSet.getString("CREW_BOARD_WRITER_ID"));
		} catch (Exception e) {
			System.err.println("Crew_board_writer_id = null");
			crew_boardDTO.setCrew_board_writer_id(null);
		}
		try {
			crew_boardDTO.setCrew_board_title(resultSet.getString("CREW_BOARD_TITLE"));
		} catch (Exception e) {
			System.err.println("Crew_board_title = null");
			crew_boardDTO.setCrew_board_title(null);
		}
		return crew_boardDTO;
	}
}
