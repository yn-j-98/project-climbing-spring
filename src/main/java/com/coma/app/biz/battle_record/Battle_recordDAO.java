package com.coma.app.biz.battle_record;//package com.coma.app.biz.battle_record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import com.coma.app.biz.common.JDBCUtil;

@Repository
public class Battle_recordDAO {
	//내 크루 승리목록 전부 출력 BATTLE_RECORD_CREW_NUM
	private final String ALL_WINNER = "SELECT\r\n"
			+ "	BR.BATTLE_RECORD_NUM,\r\n"
			+ "	BR.BATTLE_RECORD_BATTLE_NUM,\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM,\r\n"
			+ "	BR.BATTLE_RECORD_IS_WINNER,\r\n"
			+ "	BR.BATTLE_RECORD_MVP_ID,\r\n"
			+ "	G.GYM_NAME,\r\n"
			+ "	G.GYM_LOCATION,\r\n"
			+ "	B.BATTLE_GAME_DATE\r\n"
			+ "FROM\r\n"
			+ "	BATTLE_RECORD BR\r\n"
			+ "JOIN\r\n"
			+ "	CREW C\r\n"
			+ "ON\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\r\n"
			+ "JOIN\r\n"
			+ "	BATTLE B\r\n"
			+ "ON\r\n"
			+ "	BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\r\n"
			+ "JOIN\r\n"
			+ "	GYM G\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
			+ "WHERE\r\n"
			+ "	BR.BATTLE_RECORD_IS_WINNER = 'T' \r\n"
			+ "	AND BR.BATTLE_RECORD_CREW_NUM = ?";

	//해당 크루전 내용 BATTLE_RECORD_BATTLE_NUM
	private final String ONE_BATTLE = "SELECT\r\n"
			+ "	BR.BATTLE_RECORD_NUM,\r\n"
			+ "	G.GYM_NAME,\r\n"
			+ "	G.GYM_LOCATION,\r\n"
			+ "	B.BATTLE_GAME_DATE\r\n"
			+ "FROM\r\n"
			+ "	BATTLE_RECORD BR\r\n"
			+ "JOIN\r\n"
			+ "	BATTLE B\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_NUM = BR.BATTLE_RECORD_BATTLE_NUM\r\n"
			+ "JOIN\r\n"
			+ "	CREW C\r\n"
			+ "ON\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\r\n"
			+ "JOIN\r\n"
			+ "	GYM G\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
			+ "WHERE\r\n"
			+ "	B.BATTLE_NUM = ?";

	//크루전 등록 확인여부 BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String ONE_BATTLE_RECORD = "SELECT\r\n"
			+ "	BATTLE_NUM\r\n"
			+ "	BATTLE_GYM_NUM,\r\n"
			+ "	BATTLE_GAME_DATE\r\n"
			+ "	FROM\r\n"
			+ "		BATTLE B\r\n"
			+ "	JOIN\r\n"
			+ "		BATTLE_RECORD BR\r\n"
			+ "	ON\r\n"
			+ "		BR.BATTLE_RECORD_NUM = B.BATTLE_NUM\r\n"
			+ "	WHERE\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM = ? and\r\n"
			+ "	(BATTLE_GAME_DATE > (SELECT SYSDATE FROM DUAL) OR\r\n"
			+ "	BATTLE_GAME_DATE IS NULL)";

	//해당 크루전 참가한 크루 개수 BATTLE_RECORD_BATTLE_NUM
	private final String ONE_COUNT_CREW = "SELECT\r\n"
			+ "    BR.BATTLE_RECORD_BATTLE_NUM,\r\n"
			+ "    COUNT(DISTINCT BR.BATTLE_RECORD_CREW_NUM) OVER (PARTITION BY B.BATTLE_NUM) AS BATTLE_CREW_TOTAL\r\n"
			+ "FROM\r\n"
			+ "    BATTLE_RECORD BR\r\n"
			+ "JOIN\r\n"
			+ "    BATTLE B \r\n"
			+ "ON \r\n"
			+ "    B.BATTLE_NUM = BR.BATTLE_RECORD_BATTLE_NUM\r\n"
			+ "WHERE\r\n"
			+ "    BR.BATTLE_RECORD_BATTLE_NUM = ?";

	//해당 크루전 참가한 크루 전부 출력 BATTLE_RECORD_BATTLE_NUM
	private final String ALL_PARTICIPANT_CREW = "SELECT\r\n"
			+ "	BR.BATTLE_RECORD_BATTLE_NUM,\r\n"
			+ "	BR.BATTLE_RECORD_IS_WINNER,\r\n"
			+ "	BR.BATTLE_RECORD_MVP_ID,\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM,\r\n"
			+ "	C.CREW_NAME,\r\n"
			+ "	C.CREW_LEADER,\r\n"
			+ "	C.CREW_PROFILE\r\n"
			+ "FROM\r\n"
			+ "	BATTLE_RECORD BR\r\n"
			+ "JOIN\r\n"
			+ "	CREW C\r\n"
			+ "ON\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\r\n"
			+ "WHERE\r\n"
			+ "	BR.BATTLE_RECORD_BATTLE_NUM = ?";

	//크루전 등록 BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String INSERT = "INSERT INTO BATTLE_RECORD(BATTLE_RECORD_NUM,BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM)\r\n"
			+ "VALUES ((SELECT NVL(MAX(BATTLE_RECORD_NUM),0)+1 FROM BATTLE_RECORD),?,?)";

	//크루전 승리크루 업데이트 BATTLE_RECORD_IS_WINNER, BATTLE_RECORD_MVP_ID, BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String UPDATE = "UPDATE \r\n"
			+ "	BATTLE_RECORD \r\n"
			+ "SET \r\n"
			+ "	BATTLE_RECORD_IS_WINNER = ?,\r\n"
			+ "	BATTLE_RECORD_MVP_ID = ?\r\n"
			+ "WHERE \r\n"
			+ "	BATTLE_RECORD_BATTLE_NUM = ? \r\n"
			+ "	AND BATTLE_RECORD_CREW_NUM = ?";

	//해당 암벽장에서 실행된 크루전 전부 출력 BATTLE_GYM_NUM
		private final String ALL_PARTICIPANT_BATTLE = "SELECT\r\n"
				+ "	BATTLE_RECORD_NUM,\r\n"
				+ "	BATTLE_RECORD_BATTLE_NUM,\r\n"
				+ "	BATTLE_RECORD_CREW_NUM,\r\n"
				+ "	B.BATTLE_GAME_DATE,\r\n"
				+ "	BATTLE_RECORD_MVP_ID\r\n"
				+ "FROM\r\n"
				+ "	BATTLE_RECORD BR\r\n"
				+ "JOIN\r\n"
				+ "	BATTLE B\r\n"
				+ "ON\r\n"
				+ "	BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\r\n"
				+ "JOIN\r\n"
				+ "	GYM G\r\n"
				+ "ON\r\n"
				+ "	B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
				+ "WHERE\r\n"
				+ "	B.BATTLE_GYM_NUM = ?";

		//해당 암벽장에서 승리한 크루전 내용 전부 출력 BATTLE_GYM_NUM
		private final String ALL_WINNER_PARTICIPANT_GYM="SELECT \r\n"
				+ "    B.BATTLE_GAME_DATE, \r\n"
				+ "    C.CREW_NAME, \r\n"
				+ "    C.CREW_PROFILE, \r\n"
				+ "    BR.BATTLE_RECORD_MVP_ID\r\n"
				+ "FROM \r\n"
				+ "    BATTLE_RECORD BR\r\n"
				+ "JOIN \r\n"
				+ "    BATTLE B ON BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\r\n"
				+ "JOIN \r\n"
				+ "    CREW C ON BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\r\n"
				+ "WHERE \r\n"
				+ "    B.BATTLE_GYM_NUM = ? \r\n"
				+ "    AND BR.BATTLE_RECORD_IS_WINNER = 'T'";



	public boolean insert(Battle_recordDTO battle_recordDTO) {
		System.out.println("battle_record.Battle_recordDAO.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//크루전 등록 BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setInt(1, battle_recordDTO.getModel_battle_record_battle_num());
			pstmt.setInt(2, battle_recordDTO.getModel_battle_record_crew_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("battle_record.Battle_recordDAO.insert 실패");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("battle_record.Battle_recordDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("battle_record.Battle_recordDAO.insert 성공");
		return true;
	}
	public boolean update(Battle_recordDTO battle_recordDTO) {
		System.out.println("battle_record.Battle_recordDAO.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//크루전 승리크루 업데이트 BATTLE_RECORD_IS_WINNER, BATTLE_RECORD_MVP_ID, BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
			pstmt=conn.prepareStatement(UPDATE);
			pstmt.setString(1, battle_recordDTO.getModel_battle_record_is_winner());
			pstmt.setString(2, battle_recordDTO.getModel_battle_record_mvp_id());
			pstmt.setInt(3, battle_recordDTO.getModel_battle_record_battle_num());
			pstmt.setInt(4, battle_recordDTO.getModel_battle_record_crew_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("battle_record.Battle_recordDAO.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("battle_record.Battle_recordDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("battle_record.Battle_recordDAO.update 성공");
		return true;
	}
	private boolean delete(Battle_recordDTO battle_recordDTO) {
		System.err.println("battle_record.Battle_recordDAO.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("battle_record.Battle_recordDAO.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("battle_record.Battle_recordDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("battle_record.Battle_recordDAO.delete 성공");
		return true;
	}
	public Battle_recordDTO selectOne(Battle_recordDTO battle_recordDTO){
		System.out.println("battle_record.Battle_recordDAO.selectOne 시작");
		Battle_recordDTO data = null;
		String sqlq; // 쿼리수행결과 구분용 데이터
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//해당 크루전 내용 BATTLE_RECORD_BATTLE_NUM
			if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ONE_BATTLE")) {
				pstmt=conn.prepareStatement(ONE_BATTLE);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_battle_num());
				sqlq = "one";
			}
			//해당 크루전 내용 BATTLE_RECORD_BATTLE_NUM B
			if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ONE_BATTLE")) {
				pstmt=conn.prepareStatement(ONE_BATTLE);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_battle_num());
				sqlq = "one";
			}
			//해당 크루전 참가한 크루 개수 BATTLE_RECORD_BATTLE_NUM
			else if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ONE_BATTLE_RECORD")) {
				pstmt=conn.prepareStatement(ONE_BATTLE_RECORD);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_crew_num());
				sqlq = "one";
			}
			//해당 크루전 참가한 크루 개수 BATTLE_RECORD_BATTLE_NUM
			else if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ONE_COUNT_CREW")) {
				pstmt=conn.prepareStatement(ONE_COUNT_CREW);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_battle_num());
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
				System.out.println("battle_record.Battle_recordDAO.selectOne 검색 성공");
				data = new Battle_recordDTO();
				try {
				    data.setModel_battle_record_num(rs.getInt("BATTLE_RECORD_NUM"));
				} catch (SQLException e) {
				    System.err.println("battle_record_num = null");
				    data.setModel_battle_record_num(0);
				}
				try {
				    data.setModel_battle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
				} catch (SQLException e) {
				    System.err.println("battle_record_battle_num = null");
				    data.setModel_battle_record_battle_num(0);
				}
				try {
				    data.setModel_battle_record_crew_num(rs.getInt("BATTLE_RECORD_CREW_NUM"));
				} catch (SQLException e) {
				    System.err.println("battle_record_crew_num = null");
				    data.setModel_battle_record_crew_num(0);
				}
				try {
				    data.setModel_battle_record_is_winner(rs.getString("BATTLE_RECORD_IS_WINNER"));
				} catch (SQLException e) {
				    System.err.println("battle_record_is_winner = null");
				    data.setModel_battle_record_is_winner(null);
				}
				try {
				    data.setModel_battle_record_gym_name(rs.getString("GYM_NAME"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_gym_name = null");
				    data.setModel_battle_record_gym_name(null);
				}
				try {
				    data.setModel_battle_record_gym_location(rs.getString("GYM_LOCATION"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_gym_location = null");
				    data.setModel_battle_record_gym_location(null);
				}
				try {
				    data.setModel_battle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_battle_game_date = null");
				    data.setModel_battle_record_game_date(null);
				}
				try {
				    data.setModel_battle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_mvp_id = null");
				    data.setModel_battle_record_mvp_id(null);
				}
				try {
				    data.setModel_battle_record_crew_leader(rs.getString("C.CREW_LEADER"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_crew_leader = null");
				    data.setModel_battle_record_crew_leader(null);
				}
				try {
				    data.setModel_battle_record_crew_name(rs.getString("C.CREW_NAME"));
				} catch (SQLException e) {
				    System.err.println("model_battle_record_crew_name = null");
				    data.setModel_battle_record_crew_name(null);
				}

			}
			else if (flag && sqlq.equals("count")) {
	            System.out.println("BoardDAO.selectOne 검색 성공");
	            data = new Battle_recordDTO();
	            data.setModel_battle_record_total(rs.getInt("BATTLE_RECORD_CREW_TOTAL"));
	         }
		} catch (SQLException e) {
			System.err.println("battle_record.Battle_recordDAO.selectOne SQL문 실패");
			e.printStackTrace();
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("battle_record.Battle_recordDAO.selectOne 성공");
		return data;
	}

	public ArrayList<Battle_recordDTO> selectAll(Battle_recordDTO battle_recordDTO){
		System.out.println("battle_record.Battle_recordDAO.selectAll 시작");
		ArrayList<Battle_recordDTO> datas = new ArrayList<Battle_recordDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//내 크루 승리목록 전부 출력 CREW_NUM
			if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ALL_WINNER")) {
				pstmt=conn.prepareStatement(ALL_WINNER);
				pstmt.setInt(1,battle_recordDTO.getModel_battle_record_crew_num());
			}
			//해당 크루전 참가한 크루 전부 출력 BATTLE_RECORD_BATTLE_NUM
			else if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ALL_PARTICIPANT_CREW")) {
				pstmt=conn.prepareStatement(ALL_PARTICIPANT_CREW);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_battle_num());
			}
			//해당 암벽장에서 실행된 크루전 전부 출력 BATTLE_GYM_NUM
			else if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ALL_PARTICIPANT_BATTLE")) {
				pstmt=conn.prepareStatement(ALL_PARTICIPANT_BATTLE);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_gym_num());
			}
			//해당 암벽장에서 승리한 크루전 내용 전부 출력 BATTLE_GYM_NUM
			else if(battle_recordDTO.getModel_battle_record_condition().equals("BATTLE_RECORD_ALL_WINNER_PARTICIPANT_GYM")) {
				pstmt=conn.prepareStatement(ALL_WINNER_PARTICIPANT_GYM);
				pstmt.setInt(1, battle_recordDTO.getModel_battle_record_gym_num());
			}
			else {
				System.err.println("condition 틀림");
				return datas;
			}

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				Battle_recordDTO data = new Battle_recordDTO();
			    try {
			        data.setModel_battle_record_num(rs.getInt("BATTLE_RECORD_NUM"));
			    } catch (SQLException e) {
			        System.err.println("battle_record_num = null");
			        data.setModel_battle_record_num(0);
			    }
			    try {
			        data.setModel_battle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
			    } catch (SQLException e) {
			        System.err.println("battle_record_battle_num = null");
			        data.setModel_battle_record_battle_num(0);
			    }
			    try {
			        data.setModel_battle_record_crew_num(rs.getInt("BATTLE_RECORD_CREW_NUM"));
			    } catch (SQLException e) {
			        System.err.println("battle_record_crew_num = null");
			        data.setModel_battle_record_crew_num(0);
			    }
			    try {
			        data.setModel_battle_record_is_winner(rs.getString("BATTLE_RECORD_IS_WINNER"));
			    } catch (SQLException e) {
			        System.err.println("battle_record_is_winner = null");
			        data.setModel_battle_record_is_winner(null);
			    }
			    try {
			        data.setModel_battle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
			    } catch (SQLException e) {
			        System.err.println("battle_record_mvp_id = null");
			        data.setModel_battle_record_mvp_id(null);
			    }
			    try {
			        data.setModel_battle_record_crew_name(rs.getString("CREW_NAME"));
			    } catch (SQLException e) {
			        System.err.println("crew_name = null");
			        data.setModel_battle_record_crew_name(null);
			    }
			    try {
			        data.setModel_battle_record_crew_leader(rs.getString("CREW_LEADER"));
			    } catch (SQLException e) {
			        System.err.println("crew_leader = null");
			        data.setModel_battle_record_crew_leader(null);
			    }
			    try {
			    	data.setModel_battle_record_crew_profile(rs.getString("CREW_PROFILE"));
			    } catch (SQLException e) {
			    	System.err.println("crew_profile = null");
			    	data.setModel_battle_record_crew_profile(null);
			    }
			    try {
			    	data.setModel_battle_record_gym_name(rs.getString("GYM_NAME"));
			    } catch (SQLException e) {
			    	System.err.println("gym_name = null");
			    	data.setModel_battle_record_gym_name(null);
			    }
			    try {
			    	data.setModel_battle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
			    } catch (SQLException e) {
			    	System.err.println("battle_game_date = null");
			    	data.setModel_battle_record_game_date(null);
			    }
			    try {
			    	data.setModel_battle_record_gym_location(rs.getString("GYM_LOCATION"));
			    } catch (SQLException e) {
			    	System.err.println("gym_location = null");
			    	data.setModel_battle_record_gym_location(null);
			    }
				datas.add(data);
				rsCnt++;
			}
		}catch(SQLException e) {
			System.err.println("battle_record.Battle_recordDAO.selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("battle_record.Battle_recordDAO.selectAll 성공");
		return datas;
	}
}