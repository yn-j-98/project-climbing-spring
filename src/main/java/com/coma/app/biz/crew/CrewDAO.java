package com.coma.app.biz.crew;//package model.crew;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coma.app.biz.common.JDBCUtil;

public class CrewDAO {
	//크루 PK로 크루 검색 CREW_NUM
	private final String ONE = "SELECT CREW_NUM,CREW_NAME,CREW_DESCRIPTION,CREW_MAX_MEMBER_SIZE,CREW_LEADER,CREW_BATTLE_STATUS,CREW_PROFILE FROM CREW WHERE CREW_NUM = ?";

	//(페이지네이션) 크루 전체 목록 model_crew_min_num, model_crew_max_num
	private final String ALL = "SELECT\r\n"
			+ "	RN,\r\n"
			+ "	CREW_NUM,\r\n"
			+ "    CREW_NAME,\r\n"
			+ "    CREW_DESCRIPTION,\r\n"
			+ "    CREW_MAX_MEMBER_SIZE,\r\n"
			+ "    CREW_LEADER,\r\n"
			+ "    CREW_BATTLE_STATUS,\r\n"
			+ "    CREW_PROFILE\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        CREW_NUM,\r\n"
			+ "    	CREW_NAME,\r\n"
			+ "    	CREW_DESCRIPTION,\r\n"
			+ "    	CREW_MAX_MEMBER_SIZE,\r\n"
			+ "    	CREW_LEADER,\r\n"
			+ "    	CREW_BATTLE_STATUS,\r\n"
			+ "    	CREW_PROFILE,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY CREW_NUM) AS RN\r\n"
			+ "    FROM \r\n"
			+ "       	CREW\r\n"
			+ ")\r\n"
			+ "WHERE RN BETWEEN ? AND ?";

	//크루 총 개수
	private final String ONE_COUNT = "SELECT COUNT(*) AS CREW_TOTAL FROM CREW";


	//특정 크루 현재 인원수 CREW_NUM
	private final String ONE_COUNT_CURRENT_MEMBER_SIZE = "SELECT \r\n"
			+ "CREW_NUM,CREW_NAME,CREW_DESCRIPTION,CREW_MAX_MEMBER_SIZE,CREW_LEADER,CREW_BATTLE_STATUS,CREW_PROFILE,\r\n"
			+ "(SELECT count(*) from member where MEMBER_CREW_NUM = ?) AS CREW_CURRENT_MEMBER_SIZE\r\n"
			+ "FROM CREW WHERE CREW_NUM = ?";
	public boolean insert(CrewDTO crewDTO) {
		System.out.println("crew.CrewDAO.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글작성 reply_content, reply_board_num, reply_writer_id
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("crew.CrewDAO.insert 실패");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("crew.CrewDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("crew.CrewDAO.insert 성공");
		return true;
	}
	public boolean update(CrewDTO crewDTO) {
		System.out.println("crew.CrewDAO.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글 내용 수정 reply_content, reply_num
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("crew.CrewDAO.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("crew.CrewDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("crew.CrewDAO.update 성공");
		return true;
	}
	public boolean delete(CrewDTO crewDTO) {
		System.err.println("reply.ReplyDAO.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//댓글 삭제 reply_num
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("crew.CrewDAO.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("crew.CrewDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("crew.CrewDAO.delete 성공");
		return true;
	}

	public CrewDTO selectOne(CrewDTO crewDTO){
		System.out.println("crew.CrewDAO.selectOne 시작");
		CrewDTO data = null;
		String sqlq = ""; // 쿼리수행결과 구분용 데이터
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//크루 PK로 크루 검색 CREW_NUM
			if(crewDTO.getModel_crew_condition().equals("CREW_ONE")) {
				pstmt=conn.prepareStatement(ONE);
				pstmt.setInt(1, crewDTO.getModel_crew_num());
				sqlq = "one";
			}
			//크루 총 개수
			else if(crewDTO.getModel_crew_condition().equals("CREW_ONE_COUNT")) {
				pstmt=conn.prepareStatement(ONE_COUNT);
				sqlq = "count";
			}
			//해당 크루 현재 인원수 CREW_NUM
			else if(crewDTO.getModel_crew_condition().equals("CREW_ONE_COUNT_CURRENT_MEMBER_SIZE")) {
				pstmt=conn.prepareStatement(ONE_COUNT_CURRENT_MEMBER_SIZE);
				pstmt.setInt(1, crewDTO.getModel_crew_num());
				pstmt.setInt(2, crewDTO.getModel_crew_num());

				sqlq = "one";
			}
			else {
				System.err.println("condition값 잘못됨");
				return null;
			}
			System.out.println("쿼리수행결과 구분용 데이터 = "+sqlq);
			ResultSet rs = pstmt.executeQuery();
			boolean flag = rs.next();
			if(flag && sqlq.equals("one")) {
				System.out.println("crew.CrewDAO.selectOne 검색 성공");
				data = new CrewDTO();
				data.setModel_crew_num(rs.getInt("CREW_NUM"));
				data.setModel_crew_name(rs.getString("CREW_NAME"));
				data.setModel_crew_description(rs.getString("CREW_DESCRIPTION"));
				data.setModel_crew_battle_status(rs.getString("CREW_BATTLE_STATUS"));
				data.setModel_crew_leader(rs.getString("CREW_LEADER"));
				data.setModel_crew_profile(rs.getString("CREW_PROFILE"));
				data.setModel_crew_max_member_size(rs.getInt("CREW_MAX_MEMBER_SIZE"));
				try {
					data.setModel_crew_current_member_size(rs.getInt("CREW_CURRENT_MEMBER_SIZE"));
				}catch(SQLException e) {
					System.err.println("CREW_CURRENT_MEMBER_SIZE = null");
					data.setModel_crew_current_member_size(0);
				}
			}
			else if(flag && sqlq.equals("count")) {
				data = new CrewDTO();
				try {
					data.setModel_crew_current_member_size(rs.getInt("CREW_CURRENT_MEMBER_SIZE"));
				}catch(SQLException e) {
					System.err.println("CREW_CURRENT_MEMBER_SIZE = null");
					data.setModel_crew_current_member_size(0);
				}

				try {
					data.setModel_crew_total(rs.getInt("CREW_TOTAL"));
				}catch(SQLException e) {
					System.err.println("CREW_CURRENT_MEMBER_SIZE = null");
					data.setModel_crew_current_member_size(0);
				}
			}
		} catch (SQLException e) {
			System.err.println("crew.CrewDAO.selectOne SQL문 실패");
			e.printStackTrace();
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("crew.CrewDAO.selectOne 성공");
		return data;
	}

	public ArrayList<CrewDTO> selectAll(CrewDTO crewDTO){
		System.out.println("crew.CrewDAO.selectAll 시작");
		ArrayList<CrewDTO> datas = new ArrayList<CrewDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//(페이지네이션) 크루 전체 목록 model_crew_min_num, model_crew_max_num
			if(crewDTO.getModel_crew_condition().equals("CREW_ALL")) {
				pstmt=conn.prepareStatement(ALL);
				pstmt.setInt(1, crewDTO.getModel_crew_min_num());
				pstmt.setInt(2, crewDTO.getModel_crew_max_num());
			}
			else {
				System.err.println("condition값 잘못됨");
				return datas;
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				CrewDTO data = new CrewDTO();
				data.setModel_crew_num(rs.getInt("CREW_NUM"));
				data.setModel_crew_name(rs.getString("CREW_NAME"));
				data.setModel_crew_description(rs.getString("CREW_DESCRIPTION"));
				data.setModel_crew_max_member_size(rs.getInt("CREW_MAX_MEMBER_SIZE"));
				data.setModel_crew_leader(rs.getString("CREW_LEADER"));
				data.setModel_crew_battle_status(rs.getString("CREW_BATTLE_STATUS"));
				data.setModel_crew_profile(rs.getString("CREW_PROFILE"));
				datas.add(data);
				rsCnt++;
			}

		}catch(SQLException e) {
			System.err.println("crew.CrewDAO.selectAll SQL문 실패");
			e.printStackTrace();
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("crew.CrewDAO.selectAll 성공");
		return datas;
	}
}
