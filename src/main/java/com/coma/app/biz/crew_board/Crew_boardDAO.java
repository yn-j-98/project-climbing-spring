package com.coma.app.biz.crew_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Crew_boardDAO {
	// 특정 크루 채팅창 전체 출력
	private final String ALL_CREW_BOARD = "SELECT CB.CREW_BOARD_NUM, CB.CREW_BOARD_WRITER_ID, " +
			"CB.CREW_BOARD_CONTENT, C.CREW_NUM, " +
			"M.MEMBER_PROFILE, M.MEMBER_NAME " +
			"FROM CREW_BOARD CB " +
			"JOIN MEMBER M ON M.MEMBER_ID = CB.CREW_BOARD_WRITER_ID " +
			"JOIN CREW C ON M.MEMBER_CREW_NUM = C.CREW_NUM " +
			"WHERE CB.CREW_NUM = ? " +
			"ORDER BY CB.CREW_BOARD_NUM DESC";

	// 크루 게시판 글 작성
	private final String INSERT = "INSERT INTO CREW_BOARD(CREW_BOARD_WRITER_ID, CREW_BOARD_CONTENT) " +
			"VALUES (?, ?)";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 크루 게시판 글 작성
	public boolean insert(Crew_boardDTO crewBoardDTO) {
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.insert 시작");
		int result = jdbcTemplate.update(INSERT, crewBoardDTO.getCrew_board_writer_id(),
				crewBoardDTO.getCrew_board_content());
		if (result <= 0) {
			System.err.println("com.coma.app.biz.crew_board.Crew_boardDAO.insert sql 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.insert 성공");
		return true;
	}

	// 내용 변경
	private boolean update(Crew_boardDTO crewBoardDTO) {
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.update 시작");
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.update 성공");
		return false;
	}

	// 크루 게시판 글 삭제
	private boolean delete(Crew_boardDTO crewBoardDTO) {
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.delete 시작");
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.delete 성공");
		return false;
	}

	private Crew_boardDTO selectOne(Crew_boardDTO crewBoardDTO) {
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.selectOne 시작");
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.selectOne 성공");
		return null;
	}

	// 특정 크루 채팅창 전체 출력
	public List<Crew_boardDTO> selectAll(Crew_boardDTO crewBoardDTO) {
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.selectAll 시작");
		List<Crew_boardDTO> result = null;
		Object[] args = {crewBoardDTO.getCrew_num()};
		try {
			result = jdbcTemplate.query(ALL_CREW_BOARD, args, new CrewBoardRowMapper());
		} catch (Exception e) {
			System.err.println("com.coma.app.biz.crew_board.Crew_boardDAO.selectAll 실패");
			e.printStackTrace();
			return null;
		}
		System.out.println("com.coma.app.biz.crew_board.Crew_boardDAO.selectAll 성공");
		return result;
	}
}

class CrewBoardRowMapper implements RowMapper<Crew_boardDTO> {
	@Override
	public Crew_boardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew_board.selectAllCrewBoard 검색 성공");
		Crew_boardDTO crewBoardDTO = new Crew_boardDTO();
		try {
			crewBoardDTO.setCrew_board_num(resultSet.getInt("CREW_BOARD_NUM"));
		} catch (Exception e) {
			System.err.println("Board_num = 0");
			crewBoardDTO.setCrew_board_num(0);
		}
		try {
			crewBoardDTO.setCrew_board_writer_id(resultSet.getString("CREW_BOARD_WRITER_ID"));
		} catch (Exception e) {
			System.err.println("Crew_board_writer_id = null");
			crewBoardDTO.setCrew_board_writer_id(null);
		}
		try {
			crewBoardDTO.setCrew_board_content(resultSet.getString("CREW_BOARD_CONTENT"));
		} catch (Exception e) {
			System.err.println("Crew_board_content = null");
			crewBoardDTO.setCrew_board_content(null);
		}
		try {
			crewBoardDTO.setCrew_num(resultSet.getInt("CREW_NUM"));
		} catch (Exception e) {
			System.err.println("Crew_num = null");
			crewBoardDTO.setCrew_num(0);
		}
		try {
			crewBoardDTO.setCrew_board_writer_profile(resultSet.getString("MEMBER_PROFILE"));
		} catch (Exception e) {
			System.err.println("Crew_board_writer_profile = null");
			crewBoardDTO.setCrew_board_writer_profile(null);
		}
		try {
			crewBoardDTO.setCrew_board_writer_name(resultSet.getString("MEMBER_NAME"));
		} catch (Exception e) {
			System.err.println("Crew_board_writer_name = null");
			crewBoardDTO.setCrew_board_writer_name(null);
		}
		return crewBoardDTO;
	}
}