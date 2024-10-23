package com.coma.app.biz.gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.coma.app.biz.common.JDBCUtil;

@Repository
public class GymDAO {
		//(페이지 네이션) 암벽장 전체출력
		private String ALL = "SELECT \r\n"
				+ "    GYM_NUM, \r\n"
				+ "    GYM_NAME, \r\n"
				+ "    GYM_PROFILE, \r\n"
				+ "    GYM_DESCRIPTION, \r\n"
				+ "    GYM_LOCATION, \r\n"
				+ "    GYM_RESERVATION_CNT, \r\n"
				+ "    GYM_PRICE, \r\n"
				+ "    BATTLE_NUM, \r\n"
				+ "    BATTLE_GAME_DATE\r\n"
				+ "FROM (\r\n"
				+ "    SELECT \r\n"
				+ "        G.GYM_NUM, \r\n"
				+ "        G.GYM_NAME, \r\n"
				+ "        G.GYM_PROFILE, \r\n"
				+ "        G.GYM_DESCRIPTION, \r\n"
				+ "        G.GYM_LOCATION, \r\n"
				+ "        G.GYM_RESERVATION_CNT, \r\n"
				+ "        G.GYM_PRICE, \r\n"
				+ "        B.BATTLE_NUM, \r\n"
				+ "        B.BATTLE_GAME_DATE,\r\n"
				+ "        ROW_NUMBER() OVER (PARTITION BY G.GYM_NAME ORDER BY G.GYM_NUM) AS RN_G,"  // GYM_NAME별로 순번 부여\r\n
				+ "        ROW_NUMBER() OVER (ORDER BY G.GYM_NUM) AS ROW_INDEX\r\n"
				+ "    FROM \r\n"
				+ "        GYM G\r\n"
				+ "    LEFT JOIN \r\n"
				+ "        BATTLE B ON G.GYM_NUM = B.BATTLE_GYM_NUM\r\n"
				+ ") AS GYM_BATTLE_CTE\r\n"
				+ "WHERE RN_G = 1\r\n"
				+ "ORDER BY GYM_NUM\r\n"
				+ "LIMIT ?, ?";  // ?는 시작 인덱스와 행 수를 위한 자리 표시자입니다.

		//암벽장 총 개수
		private final String ONE_COUNT = "SELECT COUNT(*) AS GYM_TOTAL FROM GYM";

		//암벽장 PK로 검색 GYM_NUM
		private final String ONE = "SELECT\r\n"
				+ "	G.GYM_NUM,\r\n"
				+ "	G.GYM_NAME,\r\n"
				+ "	G.GYM_PROFILE,\r\n"
				+ "	G.GYM_DESCRIPTION,\r\n"
				+ "	G.GYM_LOCATION,\r\n"
				+ "	G.GYM_RESERVATION_CNT,\r\n"
				+ "	G.GYM_PRICE,\r\n"
				+ "	B.BATTLE_NUM,\r\n"
				+ "	B.BATTLE_GAME_DATE\r\n"
				+ "FROM\r\n"
				+ "	GYM G\r\n"
				+ "LEFT JOIN\r\n"
				+ "	BATTLE B\r\n"
				+ "ON\r\n"
				+ "	G.GYM_NUM = B.BATTLE_GYM_NUM\r\n"
				+ "WHERE\r\n"
				+ "	G.GYM_NUM = ?";

		//예약가능 개수 업데이트 GYM_RESERVATION_CNT, GYM_NUM
		private final String UPDATE_RESERVATION_CNT = "UPDATE GYM SET GYM_RESERVATION_CNT = ? WHERE GYM_NUM = ?";

		//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
		private final String INSERT = "INSERT INTO GYM (GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION) "
				+ "VALUES (?, ?, ?, ?)";


		public boolean insert(GymDTO gymDTO) {
		System.out.println("com.coma.app.biz.gym.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, gymDTO.getModel_gym_name());
			pstmt.setString(2, gymDTO.getModel_gym_profile());
			pstmt.setString(3, gymDTO.getModel_gym_description());
			pstmt.setString(4, gymDTO.getModel_gym_location());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("com.coma.app.biz.gym.insert 실패");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("com.coma.app.biz.gym.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("com.coma.app.biz.gym.insert 성공");
		return true;
	}
	public boolean update(GymDTO gymDTO) {
		System.out.println("com.coma.app.biz.gym.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//예약가능 개수 업데이트 GYM_RESERVATION_CNT, GYM_NUM
			pstmt=conn.prepareStatement(UPDATE_RESERVATION_CNT);
			pstmt.setInt(1, gymDTO.getModel_gym_reservation_cnt());
			pstmt.setInt(2, gymDTO.getModel_gym_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("com.coma.app.biz.gym.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("com.coma.app.biz.gym.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("com.coma.app.biz.gym.update 성공");
		return true;
	}
	public boolean delete(GymDTO gymDTO) {
		System.err.println("com.coma.app.biz.gym.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("com.coma.app.biz.gym.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("com.coma.app.biz.gym.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("com.coma.app.biz.gym.delete 성공");
		return true;
	}

	public GymDTO selectOne(GymDTO gymDTO){
		System.out.println("com.coma.app.biz.gym.selectOne 시작");
		GymDTO data = null;
		String sqlq; //쿼리수행결과 구분용 데이터
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//암벽장 PK로 검색 GYM_NUM
			if(gymDTO.getModel_gym_condition().equals("GYM_ONE")) {
				pstmt=conn.prepareStatement(ONE);
				pstmt.setInt(1, gymDTO.getModel_gym_num());
				sqlq = "one";
			}
			//암벽장 총 개수
			else if(gymDTO.getModel_gym_condition().equals("GYM_ONE_COUNT")) {
				pstmt=conn.prepareStatement(ONE_COUNT);
				sqlq = "count";
			}
			else {
				System.err.println("condition 틀림");
				return null;
			}
			System.out.println("쿼리수행결과 구분용 데이터 = "+sqlq);
			ResultSet rs = pstmt.executeQuery();
			boolean flag = rs.next();
			if(flag && sqlq.equals("one")) {
				System.out.println("com.coma.app.biz.gym.selectOne 검색 성공");
				data = new GymDTO();
				data.setModel_gym_num(rs.getInt("GYM_NUM"));
				data.setModel_gym_name(rs.getString("GYM_NAME"));
				data.setModel_gym_profile(rs.getString("GYM_PROFILE"));
				data.setModel_gym_description(rs.getString("GYM_DESCRIPTION"));
				data.setModel_gym_location(rs.getString("GYM_LOCATION"));
				data.setModel_gym_reservation_cnt(rs.getInt("GYM_RESERVATION_CNT"));
				data.setModel_gym_price(rs.getInt("GYM_PRICE"));
				data.setModel_gym_battle_num(rs.getInt("BATTLE_NUM"));
				data.setModel_gym_battle_game_date(rs.getString("BATTLE_GAME_DATE"));
			}
			else if(flag && sqlq.equals("count")) {
				System.out.println("com.coma.app.biz.gym.selectOne 검색 성공");
				data = new GymDTO();
				data.setModel_gym_total(rs.getInt("GYM_TOTAL"));
			}
		} catch (SQLException e) {
			System.err.println("com.coma.app.biz.gym.selectOne SQL문 실패");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("com.coma.app.biz.gym.selectOne 성공");
		return data;
	}

	public ArrayList<GymDTO> selectAll(GymDTO gymDTO){
		System.out.println("com.coma.app.biz.gym.selectAll 시작");
		ArrayList<GymDTO> datas = new ArrayList<GymDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//(페이지 네이션) 암벽장 전체출력
			pstmt=conn.prepareStatement(ALL);

			int minNum = gymDTO.getModel_gym_min_num(); // 페이지에서 시작 인덱스
			int offset = minNum;

			pstmt.setInt(1, offset);
			pstmt.setInt(2, 6);// 한 번에 가져올 최대 레코드 수
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				GymDTO data = new GymDTO();
				try {
					data.setModel_gym_num(rs.getInt("GYM_NUM"));
				} catch (SQLException e) {
					System.err.println("gym_num = null");
					data.setModel_gym_num(0);
				}
				try {
					data.setModel_gym_name(rs.getString("GYM_NAME"));
				} catch (SQLException e) {
					System.err.println("gym_name = null");
					data.setModel_gym_name(null);
				}
				try {
					data.setModel_gym_profile(rs.getString("GYM_PROFILE"));
				} catch (SQLException e) {
					System.err.println("gym_profile = null");
					data.setModel_gym_profile(null);
				}
				try {
					data.setModel_gym_description(rs.getString("GYM_DESCRIPTION"));
				} catch (SQLException e) {
					System.err.println("gym_description = null");
					data.setModel_gym_description(null);
				}
				try {
					data.setModel_gym_location(rs.getString("GYM_LOCATION"));
				} catch (SQLException e) {
					System.err.println("gym_location = null");
					data.setModel_gym_location(null);
				}
				try {
					data.setModel_gym_reservation_cnt(rs.getInt("GYM_RESERVATION_CNT"));
				} catch (SQLException e) {
					System.err.println("gym_reservation_cnt = null");
					data.setModel_gym_reservation_cnt(0);
				}
				try {
					data.setModel_gym_price(rs.getInt("GYM_PRICE"));
				} catch (SQLException e) {
					System.err.println("gym_price = null");
					data.setModel_gym_price(0);
				}
				try {
					data.setModel_gym_battle_num(rs.getInt("BATTLE_NUM"));
				} catch (SQLException e) {
					System.err.println("gym_battle_num = null");
					data.setModel_gym_battle_num(0);
				}
				try {
					data.setModel_gym_battle_game_date(rs.getString("BATTLE_GAME_DATE"));
				} catch (SQLException e) {
					System.err.println("gym_battle_game_date = null");
					data.setModel_gym_battle_game_date(null);
				}
				datas.add(data);
				rsCnt++;
			}

		}catch(SQLException e) {
			System.err.println("com.coma.app.biz.gym.selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("com.coma.app.biz.gym.selectAll 성공");
		return datas;

	}
}
