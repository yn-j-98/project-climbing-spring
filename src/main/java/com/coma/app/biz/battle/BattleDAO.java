package com.coma.app.biz.battle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
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
			+ "				) AS ROW_NUM1\r\n"
			+ "			) AS ROW_NUM2\r\n"
			+ "            \r\n"
			+ "			WHERE RN LIMIT ?,?";

	//특정 사용자가 참여한 크루전 찾기 BATTLE_RECORD_CREW_NUM
	private final String SEARCH_MEMBER_BATTLE = "SELECT\n"
			+ "  B.BATTLE_NUM,\n"
			+ "  G.GYM_NAME,\n"
			+ "  B.BATTLE_GAME_DATE,\n"
			+ "  G.GYM_LOCATION,\n"
			+ "  G.GYM_PROFILE\n"
			+ "FROM\n"
			+ "  COMA.BATTLE B\n"
			+ "JOIN\n"
			+ "  COMA.GYM G\n"
			+ "ON\n"
			+ "  B.BATTLE_GYM_NUM = G.GYM_NUM\n"
			+ "JOIN\n"
			+ "  COMA.BATTLE_RECORD BR\n"
			+ "ON\n"
			+ "  BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\n"
			+ "WHERE\n"
			+ "  BR.BATTLE_RECORD_CREW_NUM = ? AND\n"
			+ "  B.BATTLE_GAME_DATE > NOW()";

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
	private final String ONE_SEARCH_BATTLE = "SELECT\n"
			+ "  BATTLE_NUM,\n"
			+ "  BATTLE_GYM_NUM,\n"
			+ "  BATTLE_GAME_DATE\n"
			+ "FROM\n"
			+ "  COMA.BATTLE\n"
			+ "WHERE\n"
			+ "  BATTLE_NUM = ? AND\n"
			+ "  (BATTLE_GAME_DATE > NOW() OR\n"
			+ "  BATTLE_GAME_DATE IS NULL)";

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
	private final String ALL_TOP4 = "SELECT \n" +
			"    B.BATTLE_NUM,\n" +
			"    B.BATTLE_GYM_NUM,\n" +
			"    B.BATTLE_REGISTRATION_DATE,\n" +
			"    B.BATTLE_GAME_DATE\n" +
			"FROM (\n" +
			"    SELECT \n" +
			"        B.BATTLE_NUM,\n" +
			"        B.BATTLE_GYM_NUM,\n" +
			"        B.BATTLE_REGISTRATION_DATE,\n" +
			"        B.BATTLE_GAME_DATE\n" +
			"    FROM \n" +
			"        BATTLE B\n" +
			"    ORDER BY \n" +
			"        B.BATTLE_NUM DESC\n" +
			"    LIMIT 4\n" +
			") AS B";

	//게임날짜 업데이트 BATTLE_GAME_DATE, BATTLE_NUM
	private final String UPDATE = "UPDATE BATTLE SET BATTLE_GAME_DATE = ? WHERE BATTLE_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(BattleDTO battleDTO) {
		System.out.println("    [로그] com.coma.app.biz.battle INSERT 시작");
		int result= jdbcTemplate.update(INSERT, battleDTO.getBattle_gym_num(), battleDTO.getBattle_game_date());
		if(result<=0){
			return false;
		}
		return true;
	}
	public boolean update(BattleDTO battleDTO) {
		System.out.println("    [로그] com.coma.app.biz.battle UPDATE 시작");
		int result= jdbcTemplate.update(UPDATE, battleDTO.getBattle_game_date(), battleDTO.getBattle_num());
		if(result<=0){
			return false;
		}
		return true;
	}
	//delete 미사용 코드
	public boolean delete(BattleDTO battleDTO) {
		return false;
	}

	//특정 사용자가 참여한 크루전 찾기
	public BattleDTO selectOneSearchMemberBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneSearchMemberBattle 시작");
		Object[] args = new Object[]{battleDTO.getBattle_crew_num()};
		return jdbcTemplate.queryForObject(SEARCH_MEMBER_BATTLE, args, new BattleRowMapperOneSearchMemberBattle());
	}

	//활성화 되있는 크루전 총 개수
	public BattleDTO selectOneSearchBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneSearchBattle 시작");
		Object[] args = new Object[]{battleDTO.getBattle_num()};
		return  jdbcTemplate.queryForObject(ONE_SEARCH_BATTLE, args, new BattleRowMapperOneSearchBattle());
	}

	//활성화 되있는 크루전 총 개수
	public BattleDTO selectOneCountActive(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneCountActive 시작");
		return jdbcTemplate.queryForObject(ONE_COUNT_ACTIVE, new BattleRowMapperOneCountActive());
	}

	//(페이지네이션)활성화 되있는 크루전 전체 출력 내림차순
	public List<BattleDTO> selectAllActive(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllActive 시작");
		Object[] args = new Object[]{battleDTO.getBattle_min_num(),10};
		return  jdbcTemplate.query(ALL_ACTIVE,args,new BattleRowMapperAllActive());
	}

	//해당 암벽장에서 실행된 크루전 전부 출력
	public List<BattleDTO> selectAllGymBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllGymBattle 시작");
		Object[] args = new Object[]{battleDTO.getBattle_gym_num()};
		return jdbcTemplate.query(ALL_GYM_BATTLE,args,new BattleRowMapperAllGymBattle());
	}

	//최신 크루전 4개만 출력
	public List<BattleDTO> selectAllBattleAllTop4(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllBattleAllTop4 시작");
		return jdbcTemplate.query(ALL_TOP4, new BattleRowMapperAllBattleAllTop4());
	}
}

class BattleRowMapperOneSearchMemberBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.BattleRowMapperOneSearchMemberBattle 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try{
			battleDTO.setBattle_gym_name(rs.getString("GYM_NAME"));
		}catch(Exception e){
			System.err.println("Battle_gym_name = null");
			battleDTO.setBattle_gym_name(null);
		}
		try {
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try {
			battleDTO.setBattle_gym_location(rs.getString("GYM_LOCATION"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_location = null");
			battleDTO.setBattle_gym_location(null);
		}
		try {
			battleDTO.setBattle_gym_profile(rs.getString("GYM_PROFILE"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_profile = null");
			battleDTO.setBattle_gym_profile(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperOneSearchBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.BattleRowMapperOneSearchBattle 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try {
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try {
			battleDTO.setBattle_gym_num(rs.getInt("BATTLE_GYM_NUM"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_num = 0");
			battleDTO.setBattle_gym_num(0);
		}
		return battleDTO;
	}
}
class BattleRowMapperOneCountActive implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.BattleRowMapperOneCountActive 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_TOTAL"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllActive implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.BattleRowMapperAllActive 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try {
			battleDTO.setBattle_gym_num(rs.getInt("BATTLE_GYM_NUM"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_num = 0");
			battleDTO.setBattle_gym_num(0);
		}
		try {
			battleDTO.setBattle_registration_date(rs.getString("BATTLE_REGISTRATION_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_registration_date(null);
		}
		try {
			battleDTO.setBattle_gym_location(rs.getString("GYM_LOCATION"));
		} catch (SQLException e) {
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_gym_location(null);
		}
		try {
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try {
			battleDTO.setBattle_gym_profile(rs.getString("GYM_PROFILE"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_profile = null");
			battleDTO.setBattle_gym_profile(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllGymBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.BattleRowMapperAllGymBattle 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try {
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try {
			battleDTO.setBattle_gym_num(rs.getInt("BATTLE_GYM_NUM"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_num = 0");
			battleDTO.setBattle_gym_num(0);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllBattleAllTop4 implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.sBattleRowMapperAllBattleAllTop4 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch(Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try {
			battleDTO.setBattle_registration_date(rs.getString("BATTLE_REGISTRATION_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_registration_date(null);
		}
		try {
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try {
			battleDTO.setBattle_gym_num(rs.getInt("BATTLE_GYM_NUM"));
		} catch (SQLException e) {
			System.err.println("Battle_gym_num = 0");
			battleDTO.setBattle_gym_num(0);
		}
		return battleDTO;
	}
}