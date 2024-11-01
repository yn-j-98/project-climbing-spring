package com.coma.app.biz.reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(ReplyDTO replyDTO) {
		//댓글작성 REPLY_CONTENT, REPLY_BOARD_NUM, REPLY_WRITER_ID
		int result=jdbcTemplate.update(INSERT,replyDTO.getReply_content(),replyDTO.getReply_board_num(),replyDTO.getReply_writer_id());
		if(result<=0) {
			return false;
		}
		return true;
	}

	public boolean update(ReplyDTO replyDTO) {
		//댓글 내용 수정 REPLY_CONTENT, REPLY_NUM
		int result=jdbcTemplate.update(UPDATE,replyDTO.getReply_content(),replyDTO.getReply_num());
		if(result<=0) {
			return false;
		}
		return true;
	}

	public boolean delete(ReplyDTO replyDTO) {
		//댓글 삭제 REPLY_NUM
		int result=jdbcTemplate.update(DELETE,replyDTO.getReply_num());
		if(result<=0) {
			return false;
		}
		return true;
	}

	public ReplyDTO selectOne(ReplyDTO replyDTO){
		ReplyDTO data = null;
		Object[] args = {replyDTO.getReply_num()};
		try {
			//댓글 선택 SELECTONE REPLY_NUM
			data= jdbcTemplate.queryForObject(SELECTONE,args,new ReplySelectRowMapper());
		}
		catch (Exception e) {
		}
		return data;
	}

	public List<ReplyDTO> selectAll(ReplyDTO replyDTO){
		List<ReplyDTO> datas=null;
		Object[] args = {replyDTO.getReply_board_num()};
		try {
			// 해당글에 댓글목록출력 REPLY_BOARD_NUM
			datas= jdbcTemplate.query(SELECTALL,args,new ReplySelectRowMapper());
		}
		catch (Exception e) {
		}
		return datas;
	}
}

class ReplySelectRowMapper implements RowMapper<ReplyDTO> {

	public ReplyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReplyDTO replyDTO=new ReplyDTO();
		System.out.print("ReplySelectRowMapper DB에서 가져온 데이터 {");
		replyDTO.setReply_num(rs.getInt("REPLY_NUM"));
		System.err.println("reply_num = ["+replyDTO.getReply_num()+"]");
		replyDTO.setReply_board_num(rs.getInt("REPLY_BOARD_NUM"));
		System.err.println("reply_board_num = ["+replyDTO.getReply_board_num()+"]");
		replyDTO.setReply_content(rs.getString("REPLY_CONTENT"));
		System.err.println("reply_content = ["+replyDTO.getReply_content()+"]");
		replyDTO.setReply_writer_id(rs.getString("REPLY_WRITER_ID"));
		System.err.print("reply_writer_id = ["+replyDTO.getReply_writer_id()+"]");
		System.out.println("}");
		return replyDTO;
	};
}