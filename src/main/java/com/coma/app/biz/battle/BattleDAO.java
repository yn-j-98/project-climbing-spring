package com.coma.app.biz.battle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coma.app.biz.common.JDBCUtil;
import com.coma.app.biz.gym.GymDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BattleDAO {

	//(페이지네이션)활성화 되있는 크루전 전체 출력 내림차순 model_battle_min_num, model_battle_max_num
	private final String ALL_ACTIVE = "SELECT \r\n"
			+ "				BATTLE_NUM,\r\n"
			+ "				BATTLE_GYM_NAME,\r\n"
			+ "				BATTLE_REGISTRATION_DATE,\r\n"
			+ "				GYM_LOCATION,\r\n"
			+ "				BATTLE_GAME_DATE,\r\n"
			+ "				GYM_PROFILE\r\n"
			+ "			FROM (\r\n"
			+ "				SELECT \r\n"
			+ "				    BATTLE_NUM,\r\n"
			+ "				    GYM_NAME AS BATTLE_GYM_NAME,\r\n"
			+ "				    BATTLE_REGISTRATION_DATE,\r\n"
			+ "				    GYM_LOCATION,\r\n"
			+ "				    BATTLE_GAME_DATE,\r\n"
			+ "				    GYM_PROFILE,\r\n"
			+ "				    ROW_NUMBER() OVER (ORDER BY BATTLE_NUM DESC) AS RN\r\n"
			+ "				FROM (\r\n"
			+ "				    SELECT DISTINCT\r\n"
			+ "				        BATTLE_NUM,\r\n"
			+ "				        GYM_NAME,\r\n"
			+ "				        BATTLE_REGISTRATION_DATE,\r\n"
			+ "				        GYM_LOCATION,\r\n"
			+ "				        BATTLE_GAME_DATE,\r\n"
			+ "				        GYM_PROFILE\r\n"
			+ "				    FROM \r\n"
			+ "				        COMA.BATTLE B\r\n"
			+ "				    JOIN \r\n"
			+ "				        COMA.GYM G\r\n"
			+ "				    ON \r\n"
			+ "				        B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
			+ "				    JOIN\r\n"
			+ "				        COMA.BATTLE_RECORD BR\r\n"
			+ "				    ON\r\n"
			+ "				        B.BATTLE_NUM = BR.BATTLE_RECORD_BATTLE_NUM\r\n"
			+ "				    WHERE\r\n"
			+ "				        BR.BATTLE_RECORD_MVP_ID IS NULL\r\n"
			+ "				) ROW_NUM1\r\n"
			+ "			) ROW_NUM2\r\n"
			+ "            \r\n"
			+ "			WHERE RN LIMIT ?,?";

	//특정 사용자가 참여한 크루전 찾기 BATTLE_RECORD_CREW_NUM
	private final String SEARCH_MEMBER_BATTLE = "SELECT\r\n"
			+ "	B.BATTLE_NUM,\r\n"
			+ "	G.GYM_NAME,\r\n"
			+ "	B.BATTLE_GAME_DATE,\r\n"
			+ "	G.GYM_LOCATION,\r\n"
			+ "	G.GYM_PROFILE\r\n"
			+ "FROM\r\n"
			+ "	COMA.BATTLE B\r\n"
			+ "JOIN\r\n"
			+ "	COMA.GYM G\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
			+ "JOIN\r\n"
			+ "	COMA.BATTLE_RECORD BR\r\n"
			+ "ON\r\n"
			+ "	BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\r\n"
			+ "WHERE\r\n"
			+ "	BR.BATTLE_RECORD_CREW_NUM = ? AND\r\n"
			+ "	B.BATTLE_GAME_DATE > (SELECT SYSDATE() FROM DUAL)";

	//활성화 되있는 크루전 총 개수
	private final String ONE_COUNT_ACTIVE = "SELECT COUNT(DISTINCT B.BATTLE_NUM) AS BATTLE_TOTAL\r\n"
			+ "FROM\r\n"
			+ "	COMA.BATTLE B\r\n"
			+ "JOIN\r\n"
			+ "	COMA.BATTLE_RECORD BR\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_NUM = BR.BATTLE_RECORD_BATTLE_NUM\r\n"
			+ "WHERE\r\n"
			+ "	BR.BATTLE_RECORD_MVP_ID IS NULL";

	//활성화 되있는 크루전 총 개수
	private final String ONE_SEARCH_BATTLE = "SELECT\r\n"
			+ "	BATTLE_NUM\r\n"
			+ "	BATTLE_GYM_NUM,\r\n"
			+ "	BATTLE_GAME_DATE\r\n"
			+ "FROM\r\n"
			+ "	COMA.BATTLE\r\n"
			+ "WHERE\r\n"
			+ "	BATTLE_NUM = ? and\r\n"
			+ "	(BATTLE_GAME_DATE > (SELECT SYSDATE() FROM DUAL) OR\r\n"
			+ "	BATTLE_GAME_DATE IS NULL)";

	//해당 암벽장에서 실행된 크루전 전부 출력 BATTLE_GYM_NUM
	private final String ALL_GYM_BATTLE = "SELECT\r\n"
			+ "	BATTLE_NUM,\r\n"
			+ "	BATTLE_GYM_NUM,\r\n"
			+ "	BATTLE_GAME_DATE\r\n"
			+ "FROM\r\n"
			+ "	COMA.BATTLE B\r\n"
			+ "JOIN\r\n"
			+ "	COMA.GYM G\r\n"
			+ "ON\r\n"
			+ "	B.BATTLE_GYM_NUM = G.GYM_NUM\r\n"
			+ "WHERE\r\n"
			+ "	B.BATTLE_GYM_NUM = ?";

	//크루전 추가 BATTLE_GYM_NUM, BATTLE_GAME_DATE
	private final String INSERT = "INSERT INTO COMA.BATTLE(BATTLE_GYM_NUM,BATTLE_GAME_DATE) VALUES (?,?)";

	//크루전 최신 4개만 출력
	private final String ALL_TOP4 = "SELECT \r\n"
			+ "    B.BATTLE_NUM,\r\n"
			+ "    B.BATTLE_GYM_NUM,\r\n"
			+ "    B.BATTLE_REGISTRATION_DATE,\r\n"
			+ "    B.BATTLE_GAME_DATE\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        B.BATTLE_NUM,\r\n"
			+ "        B.BATTLE_GYM_NUM,\r\n"
			+ "        B.BATTLE_REGISTRATION_DATE,\r\n"
			+ "        B.BATTLE_GAME_DATE\r\n"
			+ "    FROM \r\n"
			+ "        COMA.BATTLE B\r\n"
			+ "    ORDER BY \r\n"
			+ "        BATTLE_NUM DESC\r\n"
			+ ") B\r\n"
			+ "WHERE ROWNUM <= 4";

	//게임날짜 업데이트 BATTLE_GAME_DATE, BATTLE_NUM
	private final String UPDATE = "UPDATE BATTLE SET BATTLE_GAME_DATE = ? WHERE BATTLE_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(BattleDTO battleDTO) {
		System.out.println("com.coma.app.biz.battle.insert 시작");
		int result=jdbcTemplate.update(INSERT, battleDTO.getModel_battle_gym_num(), battleDTO.getModel_battle_game_date());
		if(result<=0) {
			System.out.println("com.coma.app.biz.battle.insert 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.battle.insert 성공");
		return true;
	}

	public boolean update(BattleDTO battleDTO) {
		System.out.println("com.coma.app.biz.battle.update 시작");
		int result=jdbcTemplate.update(UPDATE, battleDTO.getModel_battle_game_date(), battleDTO.getModel_battle_num());
		if(result<=0) {
			System.out.println("com.coma.app.biz.battle.update 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.battle.update 성공");
		return true;
	}

	private boolean delete(BattleDTO battleDTO) {
		return false;
	}

	public BattleDTO selectOne(BattleDTO battleDTO){
		System.out.println("com.coma.app.biz.battle.selectOne 시작");

		String sqlq; // 쿼리수행결과 구분용 데이터
		String sql="";
		Object[] args={};

		//특정 사용자가 참여한 크루전 찾기 BATTLE_RECORD_CREW_NUM
		if(battleDTO.getModel_battle_condition().equals("BATTLE_SEARCH_MEMBER_BATTLE")) {
			sqlq="one";
			sql=SEARCH_MEMBER_BATTLE;
			args=new Object[]{battleDTO.getModel_battle_crew_num()};
		}

		//활성화 되있는 크루전 총 개수 ONE_SEARCH_BATTLE
		else if(battleDTO.getModel_battle_condition().equals("ONE_SEARCH_BATTLE")) {
			sqlq="one";
			sql=ONE_SEARCH_BATTLE;
			args=new Object[]{battleDTO.getModel_battle_num()};
		}

		//활성화 되있는 크루전 총 개수 ONE_COUNT_ACTIVE
		else if(battleDTO.getModel_battle_condition().equals("BATTLE_ONE_COUNT_ACTIVE")) {
			sqlq="count";
			sql=ONE_COUNT_ACTIVE;
		}

		else {
			System.out.println("com.coma.app.biz.battle.selectOne SQL문 실패");
			return null;
		}

		BattleDTO data=null;

		if(sqlq=="one") {
			data= jdbcTemplate.queryForObject(sql, args, new BattleRowMapper());
		}
		else if(sqlq=="count") {
			data= jdbcTemplate.queryForObject(sql, new BattleRowMapper());
		}
		System.out.println("com.coma.app.biz.battle.selectOne 성공");
		return data;
	}

	public List<BattleDTO> selectAll(BattleDTO battleDTO){
		System.out.println("battle.BattleDAO.selectAll 시작");

		String sqlq;
		String sql="";
		Object[] args={};

		//(페이지네이션)활성화 되있는 크루전 전체 출력 내림차순 model_battle_min_num, model_battle_max_num
		if(battleDTO.getModel_battle_condition().equals("BATTLE_ALL_ACTIVE")) {
			sqlq="all";
			sql=ALL_ACTIVE;
			args=new Object[]{battleDTO.getModel_battle_min_num(),10};
		}
		//해당 암벽장에서 실행된 크루전 전부 출력 BATTLE_GYM_NUM
		else if(battleDTO.getModel_battle_condition().equals("BATTLE_ALL_GYM_BATTLE")) {
			sqlq="all";
			sql=ALL_GYM_BATTLE;
			args=new Object[]{battleDTO.getModel_battle_gym_num()};
		}
		//최신 크루전 4개만 출력
		else if(battleDTO.getModel_battle_condition().equals("BATTLE_ALL_TOP4")) {
			sqlq="count";
			sql=ALL_TOP4;
		}

		else {
			System.out.println("");
			return null;
		}

		List<BattleDTO> datas = null;

		if(sqlq=="all") {
			datas= jdbcTemplate.query(sql, args, new BattleRowMapper());
		}
		else if(sqlq=="count") {
			datas= jdbcTemplate.query(sql, new BattleRowMapper());
		}
		return datas;
	}
}

class BattleRowMapper implements RowMapper<BattleDTO> {

	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BattleDTO battleDTO=new BattleDTO();
		System.out.print("DB에서 가져온 데이터 {");
		battleDTO.setModel_battle_num(rs.getInt("GYM_NUM"));
		System.err.println("gym_num = ["+battleDTO+"]");

		System.out.println("}");
		return battleDTO;
	};
}
