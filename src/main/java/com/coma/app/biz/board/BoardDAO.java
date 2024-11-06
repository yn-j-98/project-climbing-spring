package com.coma.app.biz.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {

	/* 관리자 페이지 쿼리문 */

	// 메인페이지 - selectOne
	// 게시판 전체 게시물수 count(*)으로 게산되어서 나오기
	private final String ONE_BOARD_TOTAL = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD";

	// 메인 페이지 - selectAll
	// 게시판 최신글 5개 제목과 내용 나오기
	private final String All_RECENT_BOARD5 = "SELECT BOARD_TITLE,BOARD_CONTENT FROM BOARD ORDER BY BOARD_NUM DESC LIMIT 5";

	// 게시판 관리 페이지 - selectAll
	// SelectBox 전체로 검색가능
	private final String ALL_SEARCH_BOARD = "SELECT BOARD_TITLE, BOARD_CONTENT, BOARD_CNT, BOARD_LOCATION, BOARD_WRITER_ID\n" +
			"FROM BOARD\n" +
			"WHERE BOARD_CONTENT LIKE '% ? %'";

	// 게시판 관리 페이지 - delete
	// delete 버튼 클릭 시 게시판 삭제
	private final String DELETE_SELECTED_BOARD = "DELETE FROM BOARD WHERE BOARD_NUM = ?";

	/* 사용자 페이지 쿼리문 */

	// 전체 글 출력(ALL) 페이지네이션 윈도우함수 ROW_NUMBER()사용 board_min_num, board_max_num
	//MySQL은 서브쿼리 별칭 지정이 필수적
	private final String ALL = "SELECT \n" +
			"    BOARD_NUM, \n" +
			"    BOARD_TITLE, \n" +
			"    BOARD_CONTENT, \n" +
			"    BOARD_CNT, \n" +
			"    BOARD_LOCATION, \n" +
			"    BOARD_WRITER_ID\n" +
			"FROM \n" +
			"    BOARD\n" +
			"ORDER BY \n" +
			"    BOARD_NUM DESC\n" +
			"LIMIT ? , ?";

	// 최신글6개 검색
	//ROWNUM 대신 LIMIT 구문 사용
	private final String ALL_ROWNUM = "SELECT\n" +
			"    B.BOARD_NUM,\n" +
			"    B.BOARD_TITLE,\n" +
			"    B.BOARD_CONTENT,\n" +
			"    B.BOARD_CNT,\n" +
			"    B.BOARD_LOCATION,\n" +
			"    B.BOARD_WRITER_ID\n" +
			"FROM\n" +
			"    BOARD B\n" +
			"JOIN\n" +
			"    MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID\n" +
			"ORDER BY\n" +
			"    B.BOARD_NUM DESC\n" +
			"LIMIT 6";

	// 전체 글 개수
	private final String ONE_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD";

	//ID로 검색한 글 개수 BOARD_WRITER_ID
	private final String ONE_SEARCH_ID_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD WHERE BOARD_WRITER_ID = ?";

	//제목으로 검색한 글 개수 BOARD_TITLE - 지역검색
	//문자열 연결 CONCAT함수 사용
	private final String ONE_SEARCH_TITLE_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL \r\n"
			+ "FROM BOARD \r\n"
			+ "WHERE BOARD_LOCATION LIKE CONCAT('%', ?, '%') \r\n"
			+ "  AND BOARD_TITLE LIKE CONCAT('%', ?, '%')";

	//제목 검색 count - 전체검색
	private final String ONE_SEARCH_TITLE_COUNT_All = "SELECT COUNT(*) AS BOARD_TOTAL \r\n"
			+ "FROM BOARD \r\n"
			+ "WHERE BOARD_TITLE LIKE CONCAT('%', ?, '%')";


	//이름으로 검색한 글 개수 MEMBER_NAME AS BOARD_WRITER_ID
	//문자열 연결 CONCAT함수 사용
	private final String ONE_SEARCH_NAME_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL \r\n"
			+ "FROM BOARD B \r\n"
			+ "JOIN MEMBER M ON B.BOARD_WRITER_ID = M.MEMBER_ID \r\n"
			+ "WHERE M.MEMBER_NAME LIKE CONCAT('%', ?, '%')";

	//똑같은 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	//LIMIT <OFFSET>, <ROW_COUNT>
	//BOARD_WRITER_ID, board_min_num, 10(10개씩 출력한다 가정할때 10입력)
	private final String ALL_SEARCH_MATCH_ID = "SELECT\n" +
			"    BOARD.BOARD_NUM,\n" +
			"    BOARD.BOARD_TITLE,\n" +
			"    BOARD.BOARD_CONTENT,\n" +
			"    BOARD.BOARD_CNT,\n" +
			"    BOARD.BOARD_LOCATION,\n" +
			"    BOARD.BOARD_WRITER_ID\n" +
			"FROM\n" +
			"    BOARD\n" +
			"JOIN\n" +
			"    MEMBER M\n" +
			"ON\n" +
			"    M.MEMBER_ID = BOARD.BOARD_WRITER_ID\n" +
			"WHERE\n" +
			"    BOARD.BOARD_WRITER_ID = ?\n" +
			"ORDER BY\n" +
			"    BOARD.BOARD_NUM DESC\n" +
			"LIMIT ? , ?";

	//비슷한 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	//CONCAT 함수 사용
	//LIMIT <OFFSET>, <ROW_COUNT>
	//BOARD_WRITER_ID, board_min_num, 10(10개씩 출력한다 가정할때 10입력)
	private final String ALL_SEARCH_PATTERN_ID = "SELECT\n" +
			"    B.BOARD_NUM,\n" +
			"    B.BOARD_TITLE,\n" +
			"    B.BOARD_CONTENT,\n" +
			"    B.BOARD_CNT,\n" +
			"    B.BOARD_LOCATION,\n" +
			"    B.BOARD_WRITER_ID\n" +
			"FROM\n" +
			"    BOARD B\n" +
			"JOIN\n" +
			"    MEMBER M ON M.MEMBER_ID = B.BOARD_WRITER_ID\n" +
			"WHERE\n" +
			"    B.BOARD_WRITER_ID LIKE CONCAT('%', ?, '%')\n" +
			"ORDER BY\n" +
			"    B.BOARD_NUM DESC\n" +
			"LIMIT ? , ?";


	//제목으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_TITLE, board_min_num, board_max_num
	//LIMIT <OFFSET>, <ROW_COUNT>
	//BOARD_WRITER_ID, board_min_num, 10(10개씩 출력한다 가정할때 10입력)
	private final String ALL_SEARCH_TITLE = "SELECT\n" +
			"    B.BOARD_NUM,\n" +
			"    B.BOARD_TITLE,\n" +
			"    B.BOARD_CONTENT,\n" +
			"    B.BOARD_CNT,\n" +
			"    B.BOARD_LOCATION,\n" +
			"    B.BOARD_WRITER_ID\n" +
			"FROM\n" +
			"    BOARD B\n" +
			"WHERE\n" +
			"    B.BOARD_LOCATION LIKE CONCAT('%', ?, '%') \n" +
			"    AND B.BOARD_TITLE LIKE CONCAT('%', ?, '%')\n" +
			"LIMIT ?, ?";

//	제목으로 검색,(전체 커뮤니티)
	private final String ALL_SEARCH = "SELECT\n" +
			"    B.BOARD_NUM,\n" +
			"    B.BOARD_TITLE,\n" +
			"    B.BOARD_CONTENT,\n" +
			"    B.BOARD_CNT,\n" +
			"    B.BOARD_LOCATION,\n" +
			"    B.BOARD_WRITER_ID\n" +
			"FROM\n" +
			"    BOARD B\n" +
			"WHERE\n" +
			"    B.BOARD_TITLE LIKE CONCAT('%', ?, '%')\n" +
			"LIMIT ?, ?";


	//이름으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID 재사용 BOARD_WRITER_ID, board_min_num, board_max_num
	//LIMIT <OFFSET>, <ROW_COUNT>
	//BOARD_WRITER_ID, board_min_num, 10(10개씩 출력한다 가정할때 10입력)
	private final String ALL_SEARCH_NAME = "SELECT \n" +
			"    B.BOARD_NUM,\n" +
			"    B.BOARD_TITLE,\n" +
			"    B.BOARD_CONTENT,\n" +
			"    B.BOARD_CNT,\n" +
			"    B.BOARD_LOCATION,\n" +
			"    M.MEMBER_NAME AS BOARD_WRITER_ID\n" +
			"FROM \n" +
			"    BOARD B\n" +
			"JOIN \n" +
			"    MEMBER M\n" +
			"ON \n" +
			"    M.MEMBER_ID = B.BOARD_WRITER_ID\n" +
			"WHERE \n" +
			"    M.MEMBER_NAME LIKE CONCAT('%', ?, '%')\n" +
			"LIMIT ?, ?";


	// 게시글 작성 BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID
	// PK는 AUTO-INCREMENT사용으로 서브쿼리문 사용X
	private final String INSERT = "INSERT INTO BOARD ( BOARD_TITLE, BOARD_CONTENT, BOARD_LOCATION, BOARD_WRITER_ID) VALUES (?, ?, ?, ?)";

	// 게시글 삭제 BOARD_NUM
	private final String DELETE ="DELETE FROM BOARD WHERE BOARD_NUM = ? AND BOARD_WRITER_ID = ?";

	//게시글 PK 검색
	private final String ONE = "SELECT BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_CNT,BOARD_LOCATION,BOARD_WRITER_ID FROM BOARD WHERE BOARD_NUM = ?";

	//게시글 PK 및 작성자 아이디로 검색
	private final String ONE_WRITER_ID = "SELECT BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_CNT,BOARD_LOCATION,BOARD_WRITER_ID FROM BOARD WHERE BOARD_NUM = ? AND BOARD_WRITER_ID = ?";

	//게시글 내용, 제목 변경 BOARD_CONTENT, BOARD_TITLE, BOARD_NUM
	private final String UPDATE_CONTENT_TITLE = "UPDATE BOARD SET BOARD_CONTENT = ?, BOARD_TITLE = ?, BOARD_LOCATION = ? WHERE BOARD_NUM = ?";

	//게시글 조회수 변경 BOARD_CNT BOARD_NUM
	private final String UPDATE_CNT = "UPDATE BOARD SET BOARD_CNT = ? WHERE BOARD_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.insert 시작");
		int result = jdbcTemplate.update(INSERT,boardDTO.getBoard_title().replace("'", "\'"), boardDTO.getBoard_content().replace("'", "\'"),boardDTO.getBoard_location(),boardDTO.getBoard_writer_id());
		if(result<=0){
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.insert sql 실패 : insert = " + INSERT );
			return false;
		}
		return true;
	}

	//게시글 내용, 제목 변경 BOARD_CONTENT, BOARD_TITLE, BOARD_NUM FIXME
	public boolean updateContentTitle(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.updateContentTitle 시작");
		int result = jdbcTemplate.update(UPDATE_CONTENT_TITLE,boardDTO.getBoard_content().replace("'", "\'"),boardDTO.getBoard_title().replace("'", "\'"),boardDTO.getBoard_location().replace("'", "\'"),boardDTO.getBoard_num());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.board.boardDAO.updateContentTitle sql 실패 : UPDATE = " + UPDATE_CONTENT_TITLE );
			return false;
		}
		return true;
	}

	//게시글 조회수 변경 BOARD_CNT BOARD_NUM
	public boolean updateCnt(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.updateCnt 시작");
		int result = jdbcTemplate.update(UPDATE_CNT,boardDTO.getBoard_cnt(),boardDTO.getBoard_num());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.updateCnt sql 실패 : UPDATE = " + UPDATE_CNT );
			return false;
		}
		return true;
	}

	public boolean delete(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.delete 시작");
		int result = jdbcTemplate.update(DELETE,boardDTO.getBoard_num(),boardDTO.getBoard_writer_id());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.delete sql 실패 : UPDATE = " + DELETE);
			return false;
		}
		return true;
	}

	public boolean deleteSelectedBoard(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.deleteSelectedBoard 시작");
		int result = jdbcTemplate.update(DELETE_SELECTED_BOARD,boardDTO.getBoard_num());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.deleteSelectedBoard sql 실패 : UPDATE = " + DELETE_SELECTED_BOARD);
			return false;
		}
		return true;
	}

	//게시글 PK 검색 BOARD_NUM
	public BoardDTO selectOne(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOne 시작");
		BoardDTO result = null;
		Object[] args = {boardDTO.getBoard_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE, args, new BoardRowMapperOne());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOn 실패 " + ONE);
			e.printStackTrace();
		}
		return result;
	}

	//게시글 PK 및 사용자 아이디 검색 BOARD_NUM
	public BoardDTO selectOneWriterId(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneWriterId 시작");
		BoardDTO result = null;
		Object[] args = {boardDTO.getBoard_num(), boardDTO.getBoard_writer_id()};
		try {
			result = jdbcTemplate.queryForObject(ONE_WRITER_ID, args, new BoardRowMapperOneWriterId());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneWriterId 실패 " + ONE_WRITER_ID);
			e.printStackTrace();
		}
		return result;
	}

	// 전체 글 개수
	public BoardDTO selectOneCount(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneCount 시작");
		BoardDTO result = null;
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT, new BoardRowMapperOneCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneCount 실패 " + ONE_COUNT);
			e.printStackTrace();
		}
		return result;
	}

	//ID로 검색한 글 개수 BOARD_WRITER_ID
	public BoardDTO selectOneSearchIdCount(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchIdCount 시작");
		BoardDTO result = null;
		Object[] args = {boardDTO.getSearch_content()};
		try {
			result = jdbcTemplate.queryForObject(ONE_SEARCH_ID_COUNT, args, new BoardRowMapperOneSearchIdCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchIdCount 실패 " + ONE_SEARCH_ID_COUNT);
			e.printStackTrace();
		}
		return result;
	}

	//제목으로 검색한 글 개수 BOARD_TITLE
	public BoardDTO selectOneSearchTitleCount(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchTitleCount 시작");
		BoardDTO result = null;
		String content = boardDTO.getSearch_content();
		if (content != null){
			content = boardDTO.getSearch_content().replace("'", "\'");
		}
		Object[] args = {boardDTO.getSearch_keyword().replace("'", "\'"),content};
		try {
			result = jdbcTemplate.queryForObject(ONE_SEARCH_TITLE_COUNT, args, new BoardRowMapperOneSearchTitleCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchTitleCount 실패 " + ONE_SEARCH_TITLE_COUNT);
			e.printStackTrace();
		}
		return result;
	}

	//제목으로 검색한 글 개수 BOARD_TITLE
	public BoardDTO selectOneSearchTitleCountAll(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchTitleCountAll 시작");
		BoardDTO result = null;
		String content = boardDTO.getSearch_content();
		if (content != null){
			System.out.println("BoardDAO.content :"+content);
			content = boardDTO.getSearch_content().replace("'", "\'");
		}
		Object[] args = {content};
		try {
			result = jdbcTemplate.queryForObject(ONE_SEARCH_TITLE_COUNT_All, args, new BoardRowMapperOneSearchTitleCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchTitleCountAll 실패 " + ONE_SEARCH_TITLE_COUNT_All);
			e.printStackTrace();
		}
		return result;
	}

	//이름으로 검색한 글 개수 MEMBER_NAME AS BOARD_WRITER_ID
	public BoardDTO selectOneSearchNameCount(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchNameCount 시작");
		BoardDTO result = null;
		Object[] args = {boardDTO.getSearch_content().replace("'", "\'")};
		try {
			result = jdbcTemplate.queryForObject(ONE_SEARCH_NAME_COUNT, args, new BoardRowMapperOneSearchNameCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchNameCount 실패 " + ONE_SEARCH_NAME_COUNT);
			e.printStackTrace();
		}
		return result;
	}

	// 게시판 전체 게시물수 count(*)으로 게산되어서 나오기
	public BoardDTO selectOneBoardTotal(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneBoardTotal 시작");
		BoardDTO result = null;
		try {
			result = jdbcTemplate.queryForObject(ONE_BOARD_TOTAL, new BoardRowMapperOneBoardTotal());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneBoardTotal 실패 " + ONE_BOARD_TOTAL);
			e.printStackTrace();
		}
		return result;
	}

	// 게시판 최신글 5개 제목과 내용 나오기
	public List<BoardDTO> selectAllRecentBoard5(BoardDTO boardDTO) {
		List<BoardDTO> result = null;
		try{
			result = jdbcTemplate.query(All_RECENT_BOARD5, new BoardRowMapperAllRecentBoard5());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllRecentBoard5 실패" + All_RECENT_BOARD5);
			e.printStackTrace();
		}
		return result;
	}

	// 게시판 관리 페이지 - selectAll
	// SelectBox 전체로 검색가능
	public List<BoardDTO> selectAllSearchBoard(BoardDTO boardDTO) {
		List<BoardDTO> result = null;
		Object[] args = {boardDTO.getBoard_content().replace("'", "\'")};
		try{
			result = jdbcTemplate.query(ALL_SEARCH_BOARD, args, new BoardRowMapperAllSearchBoard());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllSearchBoard 실패" + ALL_SEARCH_BOARD);
			e.printStackTrace();
		}
		return result;
	}

	//똑같은 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	//FIXME LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAllSearchMatchId(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectOneSearchNameCount 시작");
		List<BoardDTO> result = null;
		int offset = 10; //페이지네이션 시작위치
		Object[] args = {boardDTO.getBoard_writer_id().replace("'", "\'"), boardDTO.getBoard_min_num(), offset};
		try {
			result = jdbcTemplate.query(ALL_SEARCH_MATCH_ID, args, new BoardRowMapperAllSearchMatchId());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchNameCount 실패 " + ALL_SEARCH_MATCH_ID);
			e.printStackTrace();
		}
		return result;
	}

	//비슷한 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	//LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAllSearchPatternId(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectAllSearchPatternId 시작");
		List<BoardDTO> result = null;
		Object[] args = {boardDTO.getSearch_content().replace("'", "\'"), boardDTO.getBoard_min_num(), 10};
		try {
			result = jdbcTemplate.query(ALL_SEARCH_PATTERN_ID, args, new BoardRowMapperAllSearchPatternId());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchNameCount 실패 " + ALL_SEARCH_PATTERN_ID);
			e.printStackTrace();
		}
		return result;
	}

	//제목으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_TITLE, board_min_num, board_max_num
	// LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAllSearchTitle(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectAllSearchTitle 시작");
		List<BoardDTO> result = null;
		int offset = 10; //페이지네이션 시작위치
		String content = boardDTO.getSearch_content();
		if ( content != null){
		content = boardDTO.getSearch_content().replace("'", "\'");
		}
		Object[] args = {boardDTO.getSearch_keyword().replace("'", "\'"),content, boardDTO.getBoard_min_num(), offset};
		try {
			result = jdbcTemplate.query(ALL_SEARCH_TITLE, args, new BoardRowMapperAllSearchTitle());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllSearchTitle 실패 " + ALL_SEARCH_TITLE);
			e.printStackTrace();
		}
		return result;
	}

	//제목으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_TITLE, board_min_num, board_max_num
	// LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAllSearchTitleAll(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectAllSearchTitleAll 시작");
		List<BoardDTO> result = null;
		int offset = 10; //페이지네이션 시작위치
		String content = boardDTO.getSearch_content();
		Object[] args = {content, boardDTO.getBoard_min_num(), offset};
		try {
			result = jdbcTemplate.query(ALL_SEARCH, args, new BoardRowMapperAllSearchTitle());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllSearchTitleAll 실패 " + ALL_SEARCH);
			e.printStackTrace();
		}
		return result;
	}

	//이름으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID 재사용 BOARD_WRITER_ID, board_min_num, board_max_num FIXME
	//LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAllSearchName(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectallsearchName 시작");
		List<BoardDTO> result = null;
		int offset = 10; //페이지네이션 시작위치
		Object[] args = {boardDTO.getSearch_content().replace("'", "\'"), boardDTO.getBoard_min_num(), offset};
		System.out.println("search_content : ["+boardDTO.getSearch_content());
		try {
			result = jdbcTemplate.query(ALL_SEARCH_NAME, args, new BoardRowMapperAllSearchName());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllSearchName 실패 " + ALL_SEARCH_NAME);
			e.printStackTrace();
		}
		return result;
	}

	// 전체 글 출력(ALL) 페이지네이션 윈도우함수 ROW_NUMBER()사용 board_min_num, board_max_num
	//LIMIT절 사용으로 pstmt 수정
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectAll 시작");
		List<BoardDTO> result = null;
		int offset = 10; //페이지네이션 시작위치
		Object[] args = {boardDTO.getBoard_min_num(), offset};
		try {
			result = jdbcTemplate.query(ALL, args, new BoardRowMapperAll());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAll 실패 " + ALL);
			e.printStackTrace();
		}
		return result;
	}

	// 최신글6개 검색
	//ROWNUM 대신 LIMIT 구문 사용
	public List<BoardDTO> selectAllRowNum(BoardDTO boardDTO) {
		System.out.println("	[로그]com.coma.app.biz.board.BoardDAO.selectAllRowNum 시작");
		List<BoardDTO> result = null;
		try {
			result = jdbcTemplate.query(ALL_ROWNUM, new BoardRowMapperAllRownum());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectAllRowNum 실패 " + ALL_ROWNUM);
			e.printStackTrace();
		}
		return result;
	}
}

class BoardRowMapperOne implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(resultSet.getInt("BOARD_NUM"));
		}catch(SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(resultSet.getString("BOARD_TITLE"));
		}catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(resultSet.getString("BOARD_CONTENT"));
		}catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(resultSet.getInt("BOARD_CNT"));
		}catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(resultSet.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(resultSet.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}

class BoardRowMapperOneWriterId implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(resultSet.getInt("BOARD_NUM"));
		}catch(SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(resultSet.getString("BOARD_TITLE"));
		}catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(resultSet.getString("BOARD_CONTENT"));
		}catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(resultSet.getInt("BOARD_CNT"));
		}catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(resultSet.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(resultSet.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}
class BoardRowMapperOneCount implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setTotal(resultSet.getInt("BOARD_TOTAL"));
		}catch (SQLException e) {
			System.err.println("Board_total = null");
			boardDTO.setTotal(0);
		}
		return boardDTO;
	}
}
class BoardRowMapperOneSearchIdCount implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setTotal(resultSet.getInt("BOARD_TOTAL"));
		}catch (SQLException e) {
			System.err.println("Board_total = null");
			boardDTO.setTotal(0);
		}
		return boardDTO;
	}
}
class BoardRowMapperOneSearchTitleCount implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setTotal(resultSet.getInt("BOARD_TOTAL"));
		}catch (SQLException e) {
			System.err.println("Board_total = null");
			boardDTO.setTotal(0);
		}
		return boardDTO;
	}
}
class BoardRowMapperOneSearchNameCount implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setTotal(resultSet.getInt("BOARD_TOTAL"));
		}catch (SQLException e) {
			System.err.println("Board_total = null");
			boardDTO.setTotal(0);
		}
		return boardDTO;
	}
}

class BoardRowMapperOneBoardTotal implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setTotal(rs.getInt("BOARD_TOTAL"));
		}catch (SQLException e) {
			System.err.println("Board_total = null");
			boardDTO.setTotal(0);
		}
		return boardDTO;
	}
}

class BoardRowMapperAllSearchMatchId implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}
class BoardRowMapperAllSearchPatternId implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}

class BoardRowMapperAllSearchTitle implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(rs.getInt("BOARD_CNT"));
		} catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}
class BoardRowMapperAll implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(rs.getInt("BOARD_CNT"));
		} catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}

class BoardRowMapperAllSearchName implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(rs.getInt("BOARD_CNT"));
		} catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}
class BoardRowMapperAllRownum implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_num(rs.getInt("BOARD_NUM"));
		} catch (SQLException e) {
			System.err.println("Board_num = 0");
			boardDTO.setBoard_num(0);
		}
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(rs.getInt("BOARD_CNT"));
		} catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		}catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}

class BoardRowMapperAllRecentBoard5 implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = 0");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		} catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		return boardDTO;
	}
}

class BoardRowMapperAllSearchBoard implements RowMapper<BoardDTO>{
	@Override
	public BoardDTO mapRow(ResultSet rs, int i) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		try{
			boardDTO.setBoard_title(rs.getString("BOARD_TITLE"));
		} catch (SQLException e) {
			System.err.println("Board_title = null");
			boardDTO.setBoard_title(null);
		}
		try{
			boardDTO.setBoard_content(rs.getString("BOARD_CONTENT"));
		}catch (SQLException e) {
			System.err.println("Board_content = null");
			boardDTO.setBoard_content(null);
		}
		try{
			boardDTO.setBoard_cnt(rs.getInt("BOARD_CNT"));
		} catch (SQLException e) {
			System.err.println("Board_cnt = 0");
			boardDTO.setBoard_cnt(0);
		}
		try{
			boardDTO.setBoard_location(rs.getString("BOARD_LOCATION"));
		} catch (SQLException e) {
			System.err.println("Board_location = null");
			boardDTO.setBoard_location(null);
		}
		try{
			boardDTO.setBoard_writer_id(rs.getString("BOARD_WRITER_ID"));
		}catch (SQLException e) {
			System.err.println("Board_writer_id = null");
			boardDTO.setBoard_writer_id(null);
		}
		return boardDTO;
	}
}




