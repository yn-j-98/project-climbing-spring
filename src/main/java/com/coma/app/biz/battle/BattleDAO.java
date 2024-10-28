package com.coma.app.biz.battle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BattleDAO {

	/* 관리자 페이지 쿼리문 */

	//todo
	// 메인페이지 - selectOne
	// 활성화되어 있는 크루전 count(*)
	private final String ONE_COUNT_ACTIVE_BATTLE = "SELECT COUNT(*) AS BATTLE_TOTAL\n" +
			"FROM battle\n" +
			"WHERE BATTLE_STATUS = 'T'";

	//todo
	// 크루전 관리 페이지 - selectAll
	// selectBox 크루전 번호로 검색
	private final String All_SEARCH_BATTLE_NUM = "SELECT\n" +
			"  BATTLE_NUM,\n" +
			"  BATTLE_GYM_NUM,\n" +
			"  BATTLE_GAME_DATE\n" +
			"  BATTLE_REGISTRATION_DATE\n" +
			"  BATTLE_STATUS\n" +
			"FROM\n" +
			"  BATTLE\n" +
			"WHERE\n" +
			"  BATTLE_NUM = ?";

	//todo
	// 크루전 관리 페이지 - selectAll
	// selectBox 암벽장 이름으로 검색
	private final String All_SEARCH_BATTLE_NAME = "SELECT\n" +
			"  BATTLE.BATTLE_NUM,\n" +
			"  BATTLE.BATTLE_GYM_NUM,\n" +
			"  BATTLE.BATTLE_GAME_DATE,\n" +
			"  BATTLE.BATTLE_REGISTRATION_DATE,\n" +
			"  BATTLE.BATTLE_STATUS\n" +
			"FROM\n" +
			"  BATTLE\n" +
			"  JOIN GYM ON BATTLE.BATTLE_GYM_NUM = GYM.GYM_NUM\n" +
			"WHERE\n" +
			"  GYM.GYM_NAME LIKE CONCAT('%', ? ,'%')";

	//todo
	// 크루전 관리 페이지 - selectAll
	// selctBox 크루전 진행 날짜

	//todo
	// 크루전 관리 페이지 - selectAll
	// 크루전 전체 목록 출력
	private final String All_BATTLE = "SELECT\n" +
			"  BATTLE_NUM,\n" +
			"  BATTLE_GYM_NUM,\n" +
			"  BATTLE_REGISTRATION_DATE,\n" +
			"  BATTLE_GAME_DATE,\n" +
			"  BATTLE_STATUS\n" +
			"FROM\n" +
			"  BATTLE";

	//todo
	// 크루전 정보 등록 모달 - selectAll
	// 승리 크루 크루명 전체 출력
	private final String All_WIN_BATTLE = "SELECT\n" +
			"  C.CREW_NAME\n" +
			"FROM\n" +
			"  BATTLE_RECORD BR\n" +
			"  JOIN CREW C ON BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\n" +
			"WHERE\n" +
			"  BR.BATTLE_RECORD_IS_WINNER = 'T'";

	//todo
	// 크루전 정보 등록 모달 - selectAll
	// 선택된 크루명의 크루 멤버 목록 전체 출력
	private final String All_CREW_MEMBER_NAME = "SELECT\n" +
			"  M.MEMBER_NAME\n" +
			"FROM\n" +
			"  BATTLE_RECORD BR\n" +
			"  JOIN CREW C ON BR.BATTLE_RECORD_CREW_NUM = C.CREW_NUM\n" +
			"  JOIN MEMBER M ON M.MEMBER_CREW_NUM = C.CREW_NUM\n" +
			"WHERE\n" +
			"  BR.BATTLE_RECORD_IS_WINNER = 'T'";

	//todo
	// 크루전 정보 등록 모달 - insert
	// 크루전 정보 등록 모달 insert
	private final String INSERT_BATTLE_MODAL = "INSERT INTO `battle` (" +
			"  `BATTLE_GYM_NUM`, " +
			"  `BATTLE_REGISTRATION_DATE`, " +
			"  `BATTLE_GAME_DATE`, " +
			"  `BATTLE_STATUS`)" +
			"VALUES (?, ?, ?, ?)";

	/* 사용자 페이지 쿼리문 */

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
			System.err.println("	[에러] com.coma.app.biz.battle INSERT sql 실패 : insert = " + INSERT);
			return false;
		}
		return true;
	}

	// 크루전 정보 등록 모달 insert
	public boolean InsertBattleModal(BattleDTO battleDTO) {
		System.out.println("    [로그] com.coma.app.biz.battle InsertBattleModal 시작");
		int result= jdbcTemplate.update(INSERT_BATTLE_MODAL, battleDTO.getBattle_gym_num(), battleDTO.getBattle_game_date());
		if(result<=0){
			System.err.println("	[에러] com.coma.app.biz.battle InsertBattleModal sql 실패 : insert = " + INSERT_BATTLE_MODAL);
			return false;
		}
		return true;
	}

	public boolean update(BattleDTO battleDTO) {
		System.out.println("    [로그] com.coma.app.biz.battle UPDATE 시작");
		int result= jdbcTemplate.update(UPDATE, battleDTO.getBattle_game_date(), battleDTO.getBattle_num());
		if(result<=0){
			System.err.println("	[에러] com.coma.app.biz.battle UPDATE sql 실패 : update = " + UPDATE);
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
		BattleDTO result = null;
		Object[] args = new Object[]{battleDTO.getBattle_crew_num()};
		try {
			result =  jdbcTemplate.queryForObject(SEARCH_MEMBER_BATTLE, args, new BattleRowMapperOneSearchMemberBattle());
		}catch(Exception e){
			System.err.println("	[에러] com.coma.app.biz.battle.selectOneSearchMemberBattle Sql문 실패 : SEARCH_MEMBER_BATTLE = " + SEARCH_MEMBER_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	//활성화 되있는 크루전 총 개수
	public BattleDTO selectOneSearchBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneSearchBattle 시작");
		BattleDTO result = null;
		Object[] args = new Object[]{battleDTO.getBattle_num()};
		try {
			result =   jdbcTemplate.queryForObject(ONE_SEARCH_BATTLE, args, new BattleRowMapperOneSearchBattle());
		}catch(Exception e){
			System.err.println("	[에러] com.coma.app.biz.battle.selectOneSearchBattle Sql문 실패 : ONE_SEARCH_BATTLE = " + ONE_SEARCH_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	//활성화 되있는 크루전 총 개수
	public BattleDTO selectOneCountActive(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneCountActive 시작");
		BattleDTO result = null;
		try{
			result = jdbcTemplate.queryForObject(ONE_COUNT_ACTIVE, new BattleRowMapperOneCountActive());
		}catch(Exception e){
			System.err.println("	[에러] com.coma.app.biz.battle.selectOneCountActive Sql문 실패 : ONE_COUNT_ACTIVE = " + ONE_COUNT_ACTIVE);
			e.printStackTrace();
		}
		return result;
	}

	// 활성화되어 있는 크루전 count(*)
	public BattleDTO selectOneCountActiveBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectOneCountActiveBattle 시작");
		BattleDTO result = null;
		try{
			result = jdbcTemplate.queryForObject(ONE_COUNT_ACTIVE_BATTLE, new BattleRowMapperOneCountActiveBattle());
		}catch(Exception e){
			System.err.println("	[에러] com.coma.app.biz.battle.selectOneCountActiveBattle Sql문 실패 : ONE_COUNT_ACTIVE_BATTLE = " + ONE_COUNT_ACTIVE_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	//(페이지네이션)활성화 되있는 크루전 전체 출력 내림차순
	public List<BattleDTO> selectAllActive(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllActive 시작");
		List<BattleDTO> result = null;
		Object[] args = new Object[]{battleDTO.getPage(),10};
		try{
			result = jdbcTemplate.query(ALL_ACTIVE,args,new BattleRowMapperAllActive());
		}catch(Exception e){
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllActive Sql문 실패 : ALL_ACTIVE = " + ALL_ACTIVE);
			e.printStackTrace();
		}
		return  result;
	}

	//해당 암벽장에서 실행된 크루전 전부 출력
	public List<BattleDTO> selectAllGymBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllGymBattle 시작");
		List<BattleDTO> result = null;
		Object[] args = new Object[]{battleDTO.getBattle_gym_num()};
		try{
			result =  jdbcTemplate.query(ALL_GYM_BATTLE,args,new BattleRowMapperAllGymBattle());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllGymBattle Sql문 실패 : ALL_GYM_BATTLE = " + ALL_GYM_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	//최신 크루전 4개만 출력
	public List<BattleDTO> selectAllBattleAllTop4(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllBattleAllTop4 시작");
		List<BattleDTO> result = null;
		try{
			result = jdbcTemplate.query(ALL_TOP4, new BattleRowMapperAllBattleAllTop4());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllBattleAllTop4 Sql문 실패 : ALL_TOP4 = " + ALL_TOP4);
			e.printStackTrace();
		}
		return result;
	}

	// selectBox 크루전 번호로 검색
	public List<BattleDTO> selectAllSearchBattleNum(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllSearchBattleNum 시작");
		List<BattleDTO> result = null;
		Object[] args = new Object[]{battleDTO.getBattle_num()};
		try{
			result = jdbcTemplate.query(All_SEARCH_BATTLE_NUM,args,new BattleRowMapperAllSearchBattleNum());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllSearchBattleNum Sql문 실패 : All_SEARCH_BATTLE_NUM = " + All_SEARCH_BATTLE_NUM);
			e.printStackTrace();
		}
		return result;
	}

	// selectBox 암벽장 이름으로 검색
	public List<BattleDTO> selectAllSearchBattleName(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllSearchBattleName 시작");
		List<BattleDTO> result = null;
		Object[] args = new Object[]{battleDTO.getBattle_gym_name()};
		try{
			result = jdbcTemplate.query(All_SEARCH_BATTLE_NAME,args,new BattleRowMapperAllSearchBattleName());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllSearchBattleName Sql문 실패 : All_SEARCH_BATTLE_NAME = " + All_SEARCH_BATTLE_NAME);
			e.printStackTrace();
		}
		return result;
	}

	// 크루전 전체 목록 출력
	public List<BattleDTO> selectAllBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllBattle 시작");
		List<BattleDTO> result = null;
		try{
			result = jdbcTemplate.query(All_BATTLE,new BattleRowMapperAllBattle());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllBattle Sql문 실패 : All_BATTLE = " + All_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	// 승리 크루 크루명 전체 출력
	public List<BattleDTO> selectAllWinBattle(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllWinBattle 시작");
		List<BattleDTO> result = null;
		try{
			result = jdbcTemplate.query(All_WIN_BATTLE,new BattleRowMapperAllWinBattle());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllWinBattle Sql문 실패 : All_WIN_BATTLE = " + All_WIN_BATTLE);
			e.printStackTrace();
		}
		return result;
	}

	// 선택된 크루명의 크루 멤버 목록 전체 출력
	public List<BattleDTO> selectAllCrewMemberName(BattleDTO battleDTO){
		System.out.println("    [로그] com.coma.app.biz.battle.selectAllCrewMemberName 시작");
		List<BattleDTO> result = null;
		try{
			result = jdbcTemplate.query(All_CREW_MEMBER_NAME,new BattleRowMapperAllCrewMemberName());
		}catch (Exception e) {
			System.err.println("	[에러] com.coma.app.biz.battle.selectAllCrewMemberName Sql문 실패 : All_CREW_MEMBER_NAME = " + All_CREW_MEMBER_NAME);
			e.printStackTrace();
		}
		return result;
	}
}

class BattleRowMapperOneSearchMemberBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperOneSearchMemberBattle 검색 성공");
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
		System.out.println("com.coma.app.biz.battle.BattleRowMapperOneSearchBattle 검색 성공");
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
		System.out.println("com.coma.app.biz.battle.BattleRowMapperOneCountActive 검색 성공");
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
class BattleRowMapperOneCountActiveBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllActive 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try	{
			battleDTO.setTotal(rs.getInt("BATTLE_TOTAL"));
		} catch (Exception e) {
			System.err.println("Battle_total = 0");
			battleDTO.setTotal(0);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllActive implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllActive 검색 성공");
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
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllGymBattle 검색 성공");
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
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllBattleAllTop4 검색 성공");
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
class BattleRowMapperAllSearchBattleNum implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllSearchBattleNum 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch (Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try{
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		}catch (Exception e){
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try{
			battleDTO.setBattle_registration_date(rs.getString("BATTLE_REGISTRATION_DATE"));
		}catch (Exception e){
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_registration_date(null);
		}
		try{
			battleDTO.setBattle_status(rs.getString("BATTLE_STATUS"));
		} catch (SQLException e) {
			System.err.println("Battle_status = null");
			battleDTO.setBattle_status(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllSearchBattleName implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllSearchBattleName 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch (Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try{
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		}catch (Exception e){
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try{
			battleDTO.setBattle_registration_date(rs.getString("BATTLE_REGISTRATION_DATE"));
		}catch (Exception e){
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_registration_date(null);
		}
		try{
			battleDTO.setBattle_status(rs.getString("BATTLE_STATUS"));
		} catch (SQLException e) {
			System.err.println("Battle_status = null");
			battleDTO.setBattle_status(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllBattle 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_num(rs.getInt("BATTLE_NUM"));
		}catch (Exception e){
			System.err.println("Battle_num = 0");
			battleDTO.setBattle_num(0);
		}
		try{
			battleDTO.setBattle_game_date(rs.getString("BATTLE_GAME_DATE"));
		}catch (Exception e){
			System.err.println("Battle_game_date = null");
			battleDTO.setBattle_game_date(null);
		}
		try{
			battleDTO.setBattle_registration_date(rs.getString("BATTLE_REGISTRATION_DATE"));
		}catch (Exception e){
			System.err.println("Battle_registration_date = null");
			battleDTO.setBattle_registration_date(null);
		}
		try{
			battleDTO.setBattle_status(rs.getString("BATTLE_STATUS"));
		} catch (SQLException e) {
			System.err.println("Battle_status = null");
			battleDTO.setBattle_status(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllWinBattle implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllBattle 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_crew_name(rs.getString("CREW_NAME"));
		}catch (Exception e){
			System.err.println("Battle_crew_name = null");
			battleDTO.setBattle_crew_name(null);
		}
		return battleDTO;
	}
}
class BattleRowMapperAllCrewMemberName implements RowMapper<BattleDTO> {
	@Override
	public BattleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("com.coma.app.biz.battle.BattleRowMapperAllCrewMemberName 검색 성공");
		BattleDTO battleDTO = new BattleDTO();
		try{
			battleDTO.setBattle_member_name(rs.getString("MEMBER_NAME"));
		}catch (Exception e){
			System.err.println("Battle_member_name = null");
			battleDTO.setBattle_member_name(null);
		}
		return battleDTO;
	}
}