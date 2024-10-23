package com.coma.app.biz.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.coma.app.biz.common.JDBCUtil;

@Repository
public class ReplyDAO {
	// 해당글에 댓글목록출력 REPLY_BOARD_NUM
	private final String SELECTALL ="SELECT \r\n"
			+ "	R.REPLY_NUM, \r\n"
			+ "	R.REPLY_CONTENT,\r\n"
			+ "	R.REPLY_BOARD_NUM,\r\n"
			+ "	R.REPLY_WRITER_ID\r\n"
			+ "FROM\r\n"
			+ "	REPLY R\r\n"
			+ "JOIN\r\n"
			+ "	BOARD B\r\n"
			+ "ON\r\n"
			+ "	B.BOARD_NUM=R.REPLY_BOARD_NUM\r\n"
			+ "WHERE\r\n"
			+ "	REPLY_BOARD_NUM = ?";

	//댓글작성 REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID
	// PK는 AUTO-INCREMENT사용으로 서브쿼리문 사용X
	private final String INSERT = "INSERT INTO REPLY (REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID) VALUES (?, ?, ?)";

	//댓글 내용 수정 REPLY_CONTENT, REPLY_NUM
	private final String UPDATE="UPDATE REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUM = ?";

	//댓글 삭제 REPLY_NUM
	private final String DELETE="DELETE FROM REPLY WHERE REPLY_NUM=?";

	//댓글 선택 SELECTONE REPLY_NUM
	private final String SELECTONE = "SELECT REPLY_NUM, REPLY_BOARD_NUM, REPLY_CONTENT, REPLY_WRITER_ID FROM REPLY WHERE REPLY_NUM = ?";

	public boolean insert(ReplyDTO replyDTO) {
		System.out.println("reply.ReplyDAO.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글작성 reply_content, reply_board_num, reply_writer_id
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, replyDTO.getModel_reply_content());
			pstmt.setInt(2, replyDTO.getModel_reply_board_num());
			pstmt.setString(3, replyDTO.getModel_reply_writer_id());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("reply.ReplyDAO.insert 실패");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("reply.ReplyDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("reply.ReplyDAO.insert 성공");
		return true;
	}
	public boolean update(ReplyDTO replyDTO) {
		System.out.println("reply.ReplyDAO.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글 내용 수정 reply_content, reply_num
			pstmt=conn.prepareStatement(UPDATE);
			pstmt.setString(1, replyDTO.getModel_reply_content());
			pstmt.setInt(2, replyDTO.getModel_reply_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("reply.ReplyDAO.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("reply.ReplyDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("reply.ReplyDAO.update 성공");
		return true;
	}
	public boolean delete(ReplyDTO replyDTO) {
		System.err.println("reply.ReplyDAO.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글 삭제 reply_num
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setInt(1, replyDTO.getModel_reply_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("reply.ReplyDAO.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("reply.ReplyDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("reply.ReplyDAO.delete 성공");
		return true;
	}

	public ReplyDTO selectOne(ReplyDTO replyDTO){
		System.out.println("reply.ReplyDAO.selectOne 시작");
		ReplyDTO data = null;
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글 선택 SELECTONE REPLY_NUM
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, replyDTO.getModel_reply_num());

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("reply.ReplyDAO.selectOne 검색 성공");
				data = new ReplyDTO();
				data.setModel_reply_num(rs.getInt("REPLY_NUM"));
				data.setModel_reply_content(rs.getString("REPLY_CONTENT"));
				data.setModel_reply_board_num(rs.getInt("REPLY_BOARD_NUM"));
				data.setModel_reply_writer_id(rs.getString("REPLY_WRITER_ID"));
			}
		} catch (SQLException e) {
			System.err.println("reply.ReplyDAO.selectOne SQL문 실패");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("reply.ReplyDAO.selectOne 성공");
		return data;
	}

	public ArrayList<ReplyDTO> selectAll(ReplyDTO replyDTO){
		System.out.println("reply.ReplayDAO.selectAll 시작");
		ArrayList<ReplyDTO> datas = new ArrayList<ReplyDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//해당글에 댓글목록출력 reply_board_num
			pstmt=conn.prepareStatement(SELECTALL);
			pstmt.setInt(1,replyDTO.getModel_reply_board_num());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				ReplyDTO data = new ReplyDTO();
				data.setModel_reply_num(rs.getInt("REPLY_NUM"));
				data.setModel_reply_content(rs.getString("REPLY_CONTENT"));
				data.setModel_reply_writer_id(rs.getString("REPLY_WRITER_ID"));
				datas.add(data);
				rsCnt++;
			}

		}catch(SQLException e) {
			System.err.println("reply.ReplyDAO selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("reply.ReplayDAO.selectAll 성공");
		return datas;

	}
}