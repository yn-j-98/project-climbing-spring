package com.coma.app.biz.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coma.app.biz.common.JDBCUtil;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	// 전체 글 출력(ALL) 페이지네이션 윈도우함수 ROW_NUMBER()사용 board_min_num, board_max_num
	private final String ALL = "SELECT\r\n"
			+ "	RN,\r\n"
			+ "	BOARD_NUM,\r\n"
			+ "    BOARD_TITLE,\r\n"
			+ "    BOARD_CONTENT,\r\n"
			+ "    BOARD_CNT,\r\n"
			+ "    BOARD_LOCATION,\r\n"
			+ "    BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        BOARD_NUM, \r\n"
			+ "        BOARD_TITLE, \r\n"
			+ "        BOARD_CONTENT, \r\n"
			+ "        BOARD_CNT, \r\n"
			+ "        BOARD_LOCATION, \r\n"
			+ "        BOARD_WRITER_ID,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS RN\r\n"
			+ "    FROM \r\n"
			+ "        BOARD\r\n"
			+ ")\r\n"
			+ "WHERE RN BETWEEN ? AND ?";

	// 최신글6개 검색
	private final String ALL_ROWNUM = "SELECT \r\n"
			+ "    BOARD_NUM,\r\n"
			+ "    BOARD_TITLE,\r\n"
			+ "    BOARD_CONTENT,\r\n"
			+ "    BOARD_CNT,\r\n"
			+ "    BOARD_LOCATION,\r\n"
			+ "    BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        B.BOARD_NUM, \r\n"
			+ "        B.BOARD_TITLE, \r\n"
			+ "        B.BOARD_CONTENT, \r\n"
			+ "        B.BOARD_CNT, \r\n"
			+ "        B.BOARD_LOCATION, \r\n"
			+ "        B.BOARD_WRITER_ID\r\n"
			+ "    FROM \r\n"
			+ "        BOARD B\r\n"
			+ "    JOIN \r\n"
			+ "        MEMBER M\r\n"
			+ "    ON \r\n"
			+ "        B.BOARD_WRITER_ID = M.MEMBER_ID\r\n"
			+ "    ORDER BY \r\n"
			+ "        B.BOARD_NUM DESC\r\n"
			+ ")\r\n"
			+ "WHERE ROWNUM <= 6";

	// 전체 글 개수 FIXME TOTAL
	private final String ONE_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD";

	//ID로 검색한 글 개수 BOARD_WRITER_ID
	private final String ONE_SEARCH_ID_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD WHERE BOARD_WRITER_ID = ?";

	//제목으로 검색한 글 개수 BOARD_TITLE
	private final String ONE_SEARCH_TITLE_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD WHERE BOARD_LOCATION LIKE '%'||?||'%' AND BOARD_TITLE LIKE '%'||?||'%'";

	//이름으로 검색한 글 개수 MEMBER_NAME AS BOARD_WRITER_ID
	private final String ONE_SEARCH_NAME_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD B, MEMBER M WHERE B.BOARD_WRITER_ID = M.MEMBER_ID AND M.MEMBER_NAME LIKE '%'||?||'%'";

	//똑같은 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	private final String ALL_SEARCH_MATCH_ID = "SELECT\r\n"
			+ "	BOARD_PAGENATION.RN,\r\n"
			+ "	BOARD_PAGENATION.BOARD_NUM,\r\n"
			+ "    BOARD_PAGENATION.BOARD_TITLE,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CONTENT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CNT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_LOCATION,\r\n"
			+ "    BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        BOARD_NUM, \r\n"
			+ "        BOARD_TITLE, \r\n"
			+ "        BOARD_CONTENT, \r\n"
			+ "        BOARD_CNT, \r\n"
			+ "        BOARD_LOCATION, \r\n"
			+ "        BOARD_WRITER_ID,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS RN\r\n"
			+ "    FROM \r\n"
			+ "        BOARD\r\n"
			+ ") BOARD_PAGENATION\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_ID = BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "WHERE  BOARD_PAGENATION.BOARD_WRITER_ID = ? \r\n"
			+ "AND BOARD_PAGENATION.RN BETWEEN ? AND ?";

	//비슷한 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
	private final String ALL_SEARCH_PATTERN_ID = "SELECT\r\n"
			+ "	BOARD_PAGENATION.RN,\r\n"
			+ "	BOARD_PAGENATION.BOARD_NUM,\r\n"
			+ "    BOARD_PAGENATION.BOARD_TITLE,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CONTENT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CNT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_LOCATION,\r\n"
			+ "    BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        BOARD_NUM, \r\n"
			+ "        BOARD_TITLE, \r\n"
			+ "        BOARD_CONTENT, \r\n"
			+ "        BOARD_CNT, \r\n"
			+ "        BOARD_LOCATION, \r\n"
			+ "        BOARD_WRITER_ID,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS RN\r\n"
			+ "    FROM \r\n"
			+ "        BOARD\r\n"
			+ ") BOARD_PAGENATION\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_ID = BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "WHERE  BOARD_PAGENATION.BOARD_WRITER_ID LIKE '%'||?||'%' \r\n"
			+ "AND BOARD_PAGENATION.RN BETWEEN ? AND ?";

	//제목으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_TITLE, board_min_num, board_max_num
	private final String ALL_SEARCH_TITLE = "SELECT\r\n"
			+ "	BOARD_PAGENATION.RN,\r\n"
			+ "	BOARD_PAGENATION.BOARD_NUM,\r\n"
			+ "    BOARD_PAGENATION.BOARD_TITLE,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CONTENT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CNT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_LOCATION,\r\n"
			+ "    BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        BOARD_NUM, \r\n"
			+ "        BOARD_TITLE, \r\n"
			+ "        BOARD_CONTENT, \r\n"
			+ "        BOARD_CNT, \r\n"
			+ "        BOARD_LOCATION, \r\n"
			+ "        BOARD_WRITER_ID,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS RN\r\n"
			+ "    FROM \r\n"
			+ "        BOARD\r\n"
			+ ") BOARD_PAGENATION\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_ID = BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "WHERE BOARD_PAGENATION.BOARD_LOCATION LIKE '%'||?||'%' and BOARD_PAGENATION.BOARD_TITLE LIKE '%'||?||'%'\r\n"
			+ "AND BOARD_PAGENATION.RN BETWEEN ? AND ?";

	//이름으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID 재사용 BOARD_WRITER_ID, board_min_num, board_max_num
	private final String ALL_SEARCH_NAME = "SELECT\r\n"
			+ "	BOARD_PAGENATION.RN,\r\n"
			+ "	BOARD_PAGENATION.BOARD_NUM,\r\n"
			+ "    BOARD_PAGENATION.BOARD_TITLE,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CONTENT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_CNT,\r\n"
			+ "    BOARD_PAGENATION.BOARD_LOCATION,\r\n"
			+ "    M.MEMBER_NAME AS BOARD_WRITER_ID\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        BOARD_NUM, \r\n"
			+ "        BOARD_TITLE, \r\n"
			+ "        BOARD_CONTENT, \r\n"
			+ "        BOARD_CNT, \r\n"
			+ "        BOARD_LOCATION, \r\n"
			+ "        BOARD_WRITER_ID,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY BOARD_NUM DESC) AS RN\r\n"
			+ "    FROM \r\n"
			+ "        BOARD\r\n"
			+ ") BOARD_PAGENATION\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_ID = BOARD_PAGENATION.BOARD_WRITER_ID\r\n"
			+ "WHERE  M.MEMBER_NAME LIKE '%'||?||'%' \r\n"
			+ "AND BOARD_PAGENATION.RN BETWEEN ? AND ?";

	// 게시글 작성 BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID
	private final String INSERT ="INSERT INTO BOARD (BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID)\r\n"
			+ "VALUES ((SELECT NVL(MAX(BOARD_NUM),0)+1 FROM BOARD),?,?,?,?)";

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

	public boolean insert(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.insert 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 게시글 작성 BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1,boardDTO.getModel_board_title().replace("'", "\'"));
			pstmt.setString(2,boardDTO.getModel_board_content().replace("'", "\'"));
			pstmt.setString(3,boardDTO.getModel_board_location());
			pstmt.setString(4, boardDTO.getModel_board_writer_id());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("board.BoardDAO.insert 실패");
				return false;
			}
		}
		catch(SQLException e) {
			System.err.println("board.BoardDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("board.BoardDAO.insert 성공");
		return true;
	}
	public boolean update(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.update 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt =null;
		try {
			//게시글 내용, 제목 변경 BOARD_CONTENT, BOARD_TITLE, BOARD_NUM FIXME
			if(boardDTO.getModel_board_condition().equals("BOARD_UPDATE_CONTENT_TITLE")) {
				pstmt = conn.prepareStatement(UPDATE_CONTENT_TITLE);
				pstmt.setString(1, boardDTO.getModel_board_content().replace("'", "\'"));
				pstmt.setString(2, boardDTO.getModel_board_title().replace("'", "\'"));
				pstmt.setString(3, boardDTO.getModel_board_location().replace("'", "\'"));
				pstmt.setInt(4, boardDTO.getModel_board_num());
			}
			//게시글 조회수 변경 BOARD_CNT BOARD_NUM
			else if(boardDTO.getModel_board_condition().equals("BOARD_UPDATE_CNT")) {
				pstmt = conn.prepareStatement(UPDATE_CNT);
				pstmt.setInt(1, boardDTO.getModel_board_cnt());
				pstmt.setInt(2, boardDTO.getModel_board_num());
			}
			else {
				System.err.println("condition 틀림");
				return false;
			}
			int rs = pstmt.executeUpdate();
			if(rs <= 0) {
				System.err.println("board.BoardDAO.update 실패");
				return false;
			}
		}catch(SQLException e) {
			System.err.println("board.BoardDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		System.out.println("board.BoardDAO.update 성공");
		return true;
	}
	public boolean delete(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.delete 시작");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt =null;
		try {
			// 게시글 삭제 BOARD_NUM
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, boardDTO.getModel_board_num());
			pstmt.setString(2, boardDTO.getModel_board_writer_id());
			int rs = pstmt.executeUpdate();
			if(rs <= 0) {
				System.err.println("board.BoardDAO.delete 실패");
				return false;
			}
		}catch(SQLException e) {
			System.err.println("board.BoardDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("board.BoardDAO.delete 성공");
		return true;
	}
	public BoardDTO selectOne(BoardDTO boardDTO) {
		System.out.println("board.BoardDAO.selectOne 시작");
		BoardDTO data =null;
		String sqlq; // 쿼리수행결과 구분용 데이터
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//게시글 PK 검색 BOARD_NUM
			if(boardDTO.getModel_board_condition().equals("BOARD_ONE")) {
				pstmt = conn.prepareStatement(ONE);
				pstmt.setInt(1, boardDTO.getModel_board_num());
				sqlq="one";
			}
			//게시글 PK 및 사용자 아이디 검색 BOARD_NUM
			else if(boardDTO.getModel_board_condition().equals("BOARD_ONE_WRITER_ID")) {
				pstmt = conn.prepareStatement(ONE_WRITER_ID);
				pstmt.setInt(1, boardDTO.getModel_board_num());
				pstmt.setString(2, boardDTO.getModel_board_writer_id());
				sqlq="one";
			}
			// 전체 글 개수 FIXME
			else if(boardDTO.getModel_board_condition().equals("BOARD_ONE_COUNT")) {
				pstmt = conn.prepareStatement(ONE_COUNT);
				sqlq = "count";
			}
			//ID로 검색한 글 개수 BOARD_WRITER_ID
			else if(boardDTO.getModel_board_condition().equals("BOARD_ONE_SEARCH_ID_COUNT")) {
				pstmt = conn.prepareStatement(ONE_SEARCH_ID_COUNT);
				pstmt.setString(1, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				sqlq = "count";
			}
			//제목으로 검색한 글 개수 BOARD_TITLE
			else if(boardDTO.getModel_board_condition().equals("BOARD_ONE_SEARCH_TITLE_COUNT")) {
				pstmt = conn.prepareStatement(ONE_SEARCH_TITLE_COUNT);
				pstmt.setString(1, boardDTO.getModel_board_location().replace("'", "\'"));
				pstmt.setString(2, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				sqlq = "count";
			}
			//이름으로 검색한 글 개수 MEMBER_NAME AS BOARD_WRITER_ID
			else if(boardDTO.getModel_board_condition().equals("BOARD_ONE_SEARCH_NAME_COUNT")) {
				pstmt = conn.prepareStatement(ONE_SEARCH_NAME_COUNT);
				pstmt.setString(1, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				sqlq = "count";
			}
			else {
				System.err.println("condition 틀림");
				return null;
			}
			//FIXME
			System.out.println("쿼리수행결과 구분용 데이터 = "+sqlq);
			ResultSet rs = pstmt.executeQuery();
			boolean flag = rs.next();
			if (flag && sqlq.equals("one")) {
				System.out.println("BoardDAO.selectOne 검색 성공");
				data = new BoardDTO();
				data.setModel_board_num(rs.getInt("BOARD_NUM"));
				data.setModel_board_title(rs.getString("BOARD_TITLE"));
				data.setModel_board_content(rs.getString("BOARD_CONTENT"));
				data.setModel_board_writer_id(rs.getString("BOARD_WRITER_ID"));
				data.setModel_board_cnt(rs.getInt("BOARD_CNT"));
				data.setModel_board_location(rs.getString("BOARD_LOCATION"));
			}
			else if (flag && sqlq.equals("count")) {
				System.out.println("BoardDAO.selectOne 검색 성공");
				data = new BoardDTO();
				data.setModel_board_total(rs.getInt("BOARD_TOTAL"));
			}
		}catch(SQLException e) {
			System.err.println("board.BoardDAO.selectOne SQL문 실패");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("board.BoardDAO.selectOne 성공");
		return data;
	}
	public ArrayList<BoardDTO> selectAll(BoardDTO boardDTO){
		System.out.println("board.BoardDAO.selectAll 시작");
		ArrayList<BoardDTO> datas = new ArrayList<BoardDTO>();
		Connection conn =JDBCUtil.connect();
		PreparedStatement pstmt = null;
		int rsCnt=1; //로그용
		try {
			//똑같은 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
			if(boardDTO.getModel_board_condition().equals("BOARD_ALL_SEARCH_MATCH_ID")) {
				pstmt = conn.prepareStatement(ALL_SEARCH_MATCH_ID);
				pstmt.setString(1, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				pstmt.setInt(2, boardDTO.getModel_board_min_num());
				pstmt.setInt(3, boardDTO.getModel_board_max_num());
			}
			//비슷한 ID로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID, board_min_num, board_max_num
			else if(boardDTO.getModel_board_condition().equals("BOARD_ALL_SEARCH_PATTERN_ID")) {
				pstmt = conn.prepareStatement(ALL_SEARCH_PATTERN_ID);
				pstmt.setString(1, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				pstmt.setInt(2, boardDTO.getModel_board_min_num());
				pstmt.setInt(3, boardDTO.getModel_board_max_num());
			}
			//제목으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_TITLE, board_min_num, board_max_num
			else if(boardDTO.getModel_board_condition().equals("BOARD_ALL_SEARCH_TITLE")) {
				pstmt = conn.prepareStatement(ALL_SEARCH_TITLE);
				pstmt.setString(1, boardDTO.getModel_board_location().replace("'", "\'"));
				pstmt.setString(2, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				pstmt.setInt(3, boardDTO.getModel_board_min_num());
				pstmt.setInt(4, boardDTO.getModel_board_max_num());
			}
			//이름으로 검색 페이지네이션 윈도우함수 ROW_NUMBER()사용 BOARD_WRITER_ID 재사용 BOARD_WRITER_ID, board_min_num, board_max_num FIXME
			else if(boardDTO.getModel_board_condition().equals("BOARD_ALL_SEARCH_NAME")) {
				pstmt = conn.prepareStatement(ALL_SEARCH_NAME);
				pstmt.setString(1, boardDTO.getModel_board_searchKeyword().replace("'", "\'"));
				pstmt.setInt(2, boardDTO.getModel_board_min_num());
				pstmt.setInt(3, boardDTO.getModel_board_max_num());
			}
			// 전체 글 출력(ALL) 페이지네이션 윈도우함수 ROW_NUMBER()사용 board_min_num, board_max_num
			else if(boardDTO.getModel_board_condition().equals("BOARD_ALL")) {
				pstmt = conn.prepareStatement(ALL);
				pstmt.setInt(1, boardDTO.getModel_board_min_num());
				pstmt.setInt(2, boardDTO.getModel_board_max_num());
			}
			// 최신글6개 검색
			else if(boardDTO.getModel_board_condition().equals("BOARD_ALL_ROWNUM")) {
				pstmt = conn.prepareStatement(ALL_ROWNUM);
			}
			else {
				System.err.println("condition 틀림");
				return datas;
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번째 행 출력중 ..");
				BoardDTO data = new BoardDTO();
				data.setModel_board_num(rs.getInt("BOARD_NUM"));
				data.setModel_board_title(rs.getString("BOARD_TITLE"));
				data.setModel_board_content(rs.getString("BOARD_CONTENT"));
				data.setModel_board_cnt(rs.getInt("BOARD_CNT"));
				data.setModel_board_location(rs.getString("BOARD_LOCATION"));
				data.setModel_board_writer_id(rs.getString("BOARD_WRITER_ID"));
				datas.add(data);
				rsCnt++;
			}
		} catch (SQLException e) {
			System.err.println("board.BoardDAO.selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("board.BoardDAO.selectAll 성공");
		return datas;

	}
}