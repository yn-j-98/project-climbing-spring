package com.coma.app.biz.battle_record;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class Battle_recordDAO{
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
	//FIXME 정보 여러개 나와서 1개만 출력하게 쿼리문 수정
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
			+ " B.BATTLE_NUM = ?\r\n"
			+ "LIMIT 1";
	
	//크루전 등록 확인여부 BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String ONE_BATTLE_RECORD = "SELECT\n" +
			"    BATTLE_NUM,\n" +
			"    BATTLE_GYM_NUM,\n" +
			"    BATTLE_GAME_DATE\n" +
			"FROM\n" +
			"    BATTLE B\n" +
			"JOIN\n" +
			"    BATTLE_RECORD BR\n" +
			"ON\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\n" +
			"WHERE\n" +
			"    BR.BATTLE_RECORD_CREW_NUM = ? AND\n" +
			"    (BATTLE_GAME_DATE > NOW() OR\n" +
			"    BATTLE_GAME_DATE IS NULL)";
	
	//해당 크루전 참가한 크루 개수 BATTLE_RECORD_BATTLE_NUM
	private final String ONE_COUNT_CREW = "SELECT\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM,\n" +
			"    COUNT_BATTLE.BATTLE_CREW_TOTAL\n" +
			"FROM\n" +
			"    BATTLE_RECORD BR\n" +
			"JOIN\n" +
			"    BATTLE B \n" +
			"ON \n" +
			"    B.BATTLE_NUM = BR.BATTLE_RECORD_BATTLE_NUM\n" +
			"JOIN\n" +
			"    (\n" +
			"    SELECT\n" +
			"        BATTLE_RECORD_BATTLE_NUM,\n" +
			"        COUNT(DISTINCT BATTLE_RECORD_CREW_NUM) AS BATTLE_CREW_TOTAL\n" +
			"    FROM\n" +
			"        BATTLE_RECORD\n" +
			"    GROUP BY BATTLE_RECORD_BATTLE_NUM\n" +
			"    ) COUNT_BATTLE\n" +
			"ON\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM = COUNT_BATTLE.BATTLE_RECORD_BATTLE_NUM\n" +
			"WHERE\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM = ?";
	
	//해당 크루전 참가한 크루 전부 출력 BATTLE_RECORD_BATTLE_NUM
	private final String ALL_PARTICIPANT_CREW = "SELECT\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM,\n" +
			"    BR.BATTLE_RECORD_IS_WINNER,\n" +
			"    BR.BATTLE_RECORD_MVP_ID,\n" +
			"    BR.BATTLE_RECORD_CREW_NUM,\n" +
			"    C.CREW_NAME,\n" +
			"    C.CREW_LEADER,\n" +
			"    C.CREW_PROFILE\n" +
			"FROM\n" +
			"    BATTLE_RECORD BR\n" +
			"JOIN\n" +
			"    CREW C\n" +
			"ON\n" +
			"    BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\n" +
			"WHERE\n" +
			"    BR.BATTLE_RECORD_BATTLE_NUM = ?";
	
	//크루전 등록 BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String INSERT = "INSERT INTO BATTLE_RECORD(BATTLE_RECORD_BATTLE_NUM,BATTLE_RECORD_CREW_NUM) VALUES (?,?)";
	
	//크루전 승리크루 업데이트 BATTLE_RECORD_IS_WINNER, BATTLE_RECORD_MVP_ID, BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String UPDATE = "UPDATE \r\n"
			+ "	BATTLE_RECORD \r\n"
			+ "SET \r\n"
			+ "	BATTLE_RECORD_IS_WINNER = ?,\r\n"
			+ "	BATTLE_RECORD_MVP_ID = ?\r\n"
			+ "WHERE \r\n"
			+ "	BATTLE_RECORD_BATTLE_NUM = ? \r\n"
			+ "	AND BATTLE_RECORD_CREW_NUM = ?";
	//크루전 승리크루 업데이트 BATTLE_RECORD_IS_WINNER, BATTLE_RECORD_MVP_ID, BATTLE_RECORD_BATTLE_NUM, BATTLE_RECORD_CREW_NUM
	private final String UPDATE_MVP = """
			UPDATE
			BATTLE_RECORD
			SET
			BATTLE_RECORD_MVP_ID = ?
			WHERE
			BATTLE_RECORD_BATTLE_NUM = ?""";

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
				+ "    BATTLE B \r\n"
				+ "ON \r\n"
				+ "	BR.BATTLE_RECORD_BATTLE_NUM = B.BATTLE_NUM\r\n"
				+ "JOIN \r\n"
				+ "    CREW C \r\n"
				+ "ON \r\n"
				+ "	BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\r\n"
				+ "WHERE \r\n"
				+ "    B.BATTLE_GYM_NUM = ? \r\n"
				+ "    AND BR.BATTLE_RECORD_IS_WINNER = 'T'";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(Battle_recordDTO battle_recordDTO) {
		System.out.println("	[로그] com.coma.app.biz.battle_record.insert 시작");
		int result = jdbcTemplate.update(INSERT, battle_recordDTO.getBattle_record_num(), battle_recordDTO.getBattle_record_crew_num());
		if(result<=0){
			System.err.println("	[에러]com.coma.app.biz.battle_record.insert sql 실패 : insert = " + INSERT );
			return false;
		}
		return true;
	}
	public boolean update(Battle_recordDTO battle_recordDTO) {
		System.out.println("	[로그] com.coma.app.biz.battle_record.update 시작");
		int result = jdbcTemplate.update(UPDATE,battle_recordDTO.getBattle_record_is_winner(),battle_recordDTO.getBattle_record_mvp_id(),battle_recordDTO.getBattle_record_battle_num(),battle_recordDTO.getBattle_record_crew_num());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.battle_record.update sql 실패 : UPDATE = " + UPDATE );
			return false;
		}
		return true;
	}
	public boolean UPDATE_MVP(Battle_recordDTO battle_recordDTO) {
		System.out.println("	[로그] com.coma.app.biz.battle_record.UPDATE_MVP 시작");
		int result = jdbcTemplate.update(UPDATE_MVP,battle_recordDTO.getBattle_record_mvp_id(),battle_recordDTO.getBattle_record_battle_num());
		if(result <= 0){
			System.err.println("	[에러]com.coma.app.biz.battle_record.UPDATE_MVP sql 실패 : UPDATE = " + UPDATE );
			return false;
		}
		return true;
	}
	public boolean delete(Battle_recordDTO battle_recordDTO) {
		return false;
	}
	//해당 크루전 내용 BATTLE_RECORD_ONE_BATTLE
	public Battle_recordDTO selectOneBattle(Battle_recordDTO battle_recordDTO) {
		System.out.println("	[로그]com.coma.app.biz.battle_record.selectOneBattle 시작");
		Battle_recordDTO result = null;
		Object[] args = {battle_recordDTO.getBattle_record_battle_num()};
		try {
			result =  jdbcTemplate.queryForObject(ONE_BATTLE, args, new BattleRecordRowMapperOneBattle());

		}catch(Exception e) {
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectOneBattle 실패 : ONE_BATTLE = " + ONE_BATTLE);
			e.printStackTrace();
		}
		return result;
	}
	//크루전 등록 확인여부 BATTLE_RECORD_ONE_BATTLE_RECORD
	public Battle_recordDTO selectOneBattleRecord(Battle_recordDTO battle_recordDTO){
		System.out.println("	[로그]com.coma.app.biz.battle_record.selectOneBattleRecord 시작");
		Battle_recordDTO result = null;
		Object[] args = {battle_recordDTO.getBattle_record_crew_num()};
		try{
			result = jdbcTemplate.queryForObject(ONE_BATTLE_RECORD, args, new BattleRecordRowMapperOneBattleRecord());
		}catch(Exception e){
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectOneBattleRecord 실패 : ONE_BATTLE_RECORD =" + ONE_BATTLE_RECORD);
			e.printStackTrace();
		}
		return result;
	}

	//해당 크루전 참가한 크루 개수 BATTLE_RECORD_ONE_COUNT_CREW
	public Battle_recordDTO selectOneCountCrew(Battle_recordDTO battle_recordDTO){
		System.out.println("	[로그]com.coma.app.biz.battle_record.selectOneCountCrew 시작");
		Battle_recordDTO result = null;
		Object[] args = {battle_recordDTO.getBattle_record_battle_num()};
		try{
			result = jdbcTemplate.queryForObject(ONE_COUNT_CREW, args, new BattleRecordRowMapperOneCountCrew());
		}catch(Exception e){
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectOneCountCrew 실패 : ONE_COUNT_CREW =" + ONE_COUNT_CREW);
			e.printStackTrace();
		}
		return result;
	}

	//내 크루 승리목록 전부 출력 BATTLE_RECORD_ALL_WINNER
	public List<Battle_recordDTO> selectAllWinner(Battle_recordDTO battle_recordDTO){
		System.out.println("com.coma.app.biz.battle_record.selectAllWinner 시작");
		List<Battle_recordDTO> result = null;
		Object[] args = {battle_recordDTO.getBattle_record_crew_num()};
		try{
			result = jdbcTemplate.query(ALL_WINNER, args, new BattleRecordRowMapperAllWinner());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectAllWinner 실패 : ALL_WINNER = " + ALL_WINNER);
			e.printStackTrace();
		}
		return result;
	}

	//해당 크루전 참가한 크루 전부 출력 BATTLE_RECORD_ALL_PARTICIPANT_CREW
	public List<Battle_recordDTO> selectAllParticipantCrew(Battle_recordDTO battle_recordDTO){
		System.out.println("com.coma.app.biz.battle_record.selectAllParticipantCrew 시작");
		List<Battle_recordDTO> result = null;
		Object[] args = {battle_recordDTO.getBattle_record_battle_num()};
		try{
			result = jdbcTemplate.query(ALL_PARTICIPANT_CREW, args, new BattleRecordRowMapperAllParticipantCrew());
		}catch(Exception e){
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectAllParticipantCrew 실패 : ALL_PARTICIPANT_CREW = " + ALL_PARTICIPANT_CREW);
			e.printStackTrace();
		}
		return result;
	}

	//해당 암벽장에서 실행된 크루전 전부 출력 BATTLE_RECORD_ALL_PARTICIPANT_BATTLE
	public List<Battle_recordDTO> selectAllParticipantBattle(Battle_recordDTO battle_recordDTO){
		System.out.println("com.coma.app.biz.battle_record.selectAllParticipantBattle 시작");
		List<Battle_recordDTO> result = null;
		Object[] args = {battle_recordDTO.getBattle_record_gym_num()};
		try{
			result = jdbcTemplate.query(ALL_PARTICIPANT_BATTLE, args, new BattleRecordRowMapperAllParticipantBattle());
		}catch(Exception e){
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectAllParticipantBattle 실패 : ALL_PARTICIPANT_BATTLE = " + ALL_PARTICIPANT_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	//해당 암벽장에서 승리한 크루전 내용 전부 출력 BATTLE_RECORD_ALL_WINNER_PARTICIPANT_GYM
	public List<Battle_recordDTO> selectAllWinnerParticipantGym(Battle_recordDTO battle_recordDTO){
		System.out.println("com.coma.app.biz.battle_record.selectAllWinnerParticipantGym 시작");
		List<Battle_recordDTO> result = null;
		Object[] args = {battle_recordDTO.getBattle_record_gym_num()};
		try {
			result = jdbcTemplate.query(ALL_WINNER_PARTICIPANT_GYM, args, new BattleRecordRowMapperAllWinnerParticipantGym());
		}catch(Exception e){
			System.err.println("	[에러]com.coma.app.biz.battle_record.selectAllWinnerParticipantGym 실패 : ALL_WINNER_PARTICIPANT_GYM = " + ALL_WINNER_PARTICIPANT_GYM);
			e.printStackTrace();
		}
		return result;
	}
}

class BattleRecordRowMapperOneBattle implements RowMapper<Battle_recordDTO>{
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectOneBattle 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_num(rs.getInt("BATTLE_RECORD_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_num = null");
			data.setBattle_record_num(0);
		}
		try {
			data.setBattle_record_gym_name(rs.getString("GYM_NAME"));
		} catch (SQLException e) {
			System.err.println("battle_record_gym_name = null");
			data.setBattle_record_gym_name(null);
		}
		try {
			data.setBattle_record_gym_location(rs.getString("GYM_LOCATION"));
		} catch (SQLException e) {
			System.err.println("battle_record_gym_location = null");
			data.setBattle_record_gym_location(null);
		}
		try {
			data.setBattle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_game_date = null");
			data.setBattle_record_game_date(null);
		}
		return data;
	}
}
class BattleRecordRowMapperOneBattleRecord implements RowMapper<Battle_recordDTO>{
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectOneBattleRecord 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_battle_num(rs.getInt("BATTLE_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_num = null");
			data.setBattle_record_battle_num(0);
		}
		try{
			data.setBattle_record_gym_num(rs.getInt("BATTLE_GYM_NUM"));
		} catch (SQLException e) {
			System.err.println("Battle_record_gym_num = null");
			data.setBattle_record_gym_num(0);
		}
		try {
			data.setBattle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_battle_game_date = null");
			data.setBattle_record_game_date(null);
		}
		return data;
	}
}

class BattleRecordRowMapperOneCountCrew implements RowMapper<Battle_recordDTO>{
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectOneCountCrew 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_num = null");
			data.setBattle_record_battle_num(0);
		}
		try {
			data.setTotal(rs.getInt("BATTLE_CREW_TOTAL"));
		} catch (SQLException e) {
			System.err.println("attle_record_tota = 0");
			data.setTotal(0);
		}
		return data;
	}
}

class BattleRecordRowMapperAllWinner implements RowMapper<Battle_recordDTO> {
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectAllWinner 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_num(rs.getInt("BATTLE_RECORD_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_num = null");
			data.setBattle_record_num(0);
		}
		try {
			data.setBattle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_num = null");
			data.setBattle_record_battle_num(0);
		}
		try {
			data.setBattle_record_crew_num(rs.getInt("BATTLE_RECORD_CREW_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_crew_num = null");
			data.setBattle_record_crew_num(0);
		}
		try {
			data.setBattle_record_is_winner(rs.getString("BATTLE_RECORD_IS_WINNER"));
		} catch (SQLException e) {
			System.err.println("battle_record_is_winner = null");
			data.setBattle_record_is_winner(null);
		}
		try {
			data.setBattle_record_gym_name(rs.getString("GYM_NAME"));
		} catch (SQLException e) {
			System.err.println("Battle_record_gym_name = null");
			data.setBattle_record_gym_name(null);
		}
		try {
			data.setBattle_record_gym_location(rs.getString("GYM_LOCATION"));
		} catch (SQLException e) {
			System.err.println("Battle_record_gym_location = null");
			data.setBattle_record_gym_location(null);
		}
		try {
			data.setBattle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_battle_game_date = null");
			data.setBattle_record_game_date(null);
		}
		try {
			data.setBattle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
		} catch (SQLException e) {
			System.err.println("Battle_record_mvp_id = null");
			data.setBattle_record_mvp_id(null);
		}
		return data;
	}
}

class BattleRecordRowMapperAllParticipantCrew implements RowMapper<Battle_recordDTO> {
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectAllParticipantCrew 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_num = null");
			data.setBattle_record_battle_num(0);
		}
		try {
			data.setBattle_record_crew_num(rs.getInt("BATTLE_RECORD_CREW_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_crew_num = null");
			data.setBattle_record_crew_num(0);
		}
		try {
			data.setBattle_record_is_winner(rs.getString("BATTLE_RECORD_IS_WINNER"));
		} catch (SQLException e) {
			System.err.println("battle_record_is_winner = null");
			data.setBattle_record_is_winner(null);
		}
		try {
			data.setBattle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
		} catch (SQLException e) {
			System.err.println("Battle_record_mvp_id = null");
			data.setBattle_record_mvp_id(null);
		}
		try {
			data.setBattle_record_crew_leader(rs.getString("C.CREW_LEADER"));
		} catch (SQLException e) {
			System.err.println("Battle_record_crew_leader = null");
			data.setBattle_record_crew_leader(null);
		}
		try {
			data.setBattle_record_crew_name(rs.getString("C.CREW_NAME"));
		} catch (SQLException e) {
			System.err.println("Battle_record_crew_name = null");
			data.setBattle_record_crew_name(null);
		}
		try {
			data.setBattle_record_crew_profile(rs.getString("C.CREW_PROFILE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_crew_profile = null");
			data.setBattle_record_crew_name(null);
		}
		return data;
	}
}

class BattleRecordRowMapperAllParticipantBattle implements RowMapper<Battle_recordDTO> {
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectAllParticipantBattle 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_num(rs.getInt("BATTLE_RECORD_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_num = null");
			data.setBattle_record_num(0);
		}
		try {
			data.setBattle_record_battle_num(rs.getInt("BATTLE_RECORD_BATTLE_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_battle_num = null");
			data.setBattle_record_battle_num(0);
		}
		try {
			data.setBattle_record_crew_num(rs.getInt("BATTLE_RECORD_CREW_NUM"));
		} catch (SQLException e) {
			System.err.println("battle_record_crew_num = null");
			data.setBattle_record_crew_num(0);
		}
		try {
			data.setBattle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_battle_game_date = null");
			data.setBattle_record_game_date(null);
		}
		try {
			data.setBattle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
		} catch (SQLException e) {
			System.err.println("Battle_record_mvp_id = null");
			data.setBattle_record_mvp_id(null);
		}
		return data;
	}
}

class BattleRecordRowMapperAllWinnerParticipantGym implements RowMapper<Battle_recordDTO> {
	@Override
	public Battle_recordDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle_record.selectAllWinnerParticipantGym 검색 성공");
		Battle_recordDTO data = new Battle_recordDTO();
		try {
			data.setBattle_record_crew_name(rs.getString("CREW_NAME"));
		} catch (SQLException e) {
			System.err.println("Battle_record_crew_name = null");
			data.setBattle_record_crew_name(null);
		}
		try {
			data.setBattle_record_game_date(rs.getString("BATTLE_GAME_DATE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_battle_game_date = null");
			data.setBattle_record_game_date(null);
		}
		try {
			data.setBattle_record_crew_profile(rs.getString("C.CREW_PROFILE"));
		} catch (SQLException e) {
			System.err.println("Battle_record_crew_profile = null");
			data.setBattle_record_crew_name(null);
		}
		try {
			data.setBattle_record_mvp_id(rs.getString("BATTLE_RECORD_MVP_ID"));
		} catch (SQLException e) {
			System.err.println("Battle_record_mvp_id = null");
			data.setBattle_record_mvp_id(null);
		}
		return data;
	}
}