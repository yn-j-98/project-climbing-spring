package com.coma.app.biz.crew;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CrewDAO {
	//크루 PK로 크루 검색 CREW_NUM
	private final String ONE = "SELECT CREW_NUM,CREW_NAME,CREW_DESCRIPTION,CREW_MAX_MEMBER_SIZE,CREW_LEADER,CREW_BATTLE_STATUS,CREW_PROFILE FROM CREW WHERE CREW_NUM = ?";

	//(페이지네이션) 크루 전체 목록 Crew_min_num, Crew_max_num
	private final String ALL = "SELECT \n" +
			"    CREW_NUM,\n" +
			"    CREW_NAME,\n" +
			"    CREW_DESCRIPTION,\n" +
			"    CREW_MAX_MEMBER_SIZE,\n" +
			"    CREW_LEADER,\n" +
			"    CREW_BATTLE_STATUS,\n" +
			"    CREW_PROFILE\n" +
			"FROM \n" +
			"    CREW\n" +
			"ORDER BY \n" +
			"    CREW_NUM DESC\n" +
			"LIMIT ?, ?";

	//크루 총 개수
	private final String ONE_COUNT = "SELECT COUNT(*) AS CREW_TOTAL FROM CREW";

	// 크루의 참여상태 확인 // TODO 크루전 신청 페이지 추가(2024.11.01)
	private final String ONE_BATTLE_STATUS = "SELECT\n" +
			"	COUNT(*) AS CREW_TOTAL\n" +
			"FROM\n" +
			"	CREW\n" +
			"WHERE\n" +
			"	CREW_BATTLE_STATUS = 'T'\n" +
			"AND CREW_NUM = ?";

	// 크루 참여상태 참여로 변경 // TODO 크루전 신청 페이지 추가(2024.11.01)
	private final String UPDATE_BATTLE_STATUS_TRUE = "UPDATE\n" +
			"	CREW\n" +
			"SET\n" +
			"	CREW_BATTLE_STATUS = 'T'\n" +
			"WHERE CREW_NUM = ?";

	// 크루 참여상태 비참여로 변경 // TODO 관리자 페이지 추가(2024.11.01)
	private final String UPDATE_BATTLE_STATUS_FALSE = "UPDATE\n" +
			"	CREW\n" +
			"SET\n" +
			"	CREW_BATTLE_STATUS = 'F'\n" +
			"WHERE CREW_NUM = ?";

	//특정 크루 현재 인원수 CREW_NUM
	private final String ONE_COUNT_CURRENT_MEMBER_SIZE = "SELECT \n" +
			"    CREW_NUM,\n" +
			"    CREW_NAME,\n" +
			"    CREW_DESCRIPTION,\n" +
			"    CREW_MAX_MEMBER_SIZE,\n" +
			"    CREW_LEADER,\n" +
			"    CREW_BATTLE_STATUS,\n" +
			"    CREW_PROFILE,\n" +
			"    (SELECT COUNT(*) FROM MEMBER WHERE MEMBER_CREW_NUM = ?) AS CREW_CURRENT_MEMBER_SIZE\n" +
			"FROM \n" +
			"    CREW \n" +
			"WHERE \n" +
			"    CREW_NUM = ?";

	// 크루 이름으로 크루 검색 CREW_NAME
	private final String ONE_SEARCH_CREW_NAME = "SELECT CREW_NUM, CREW_NAME, CREW_DESCRIPTION, CREW_MAX_MEMBER_SIZE, CREW_LEADER, CREW_BATTLE_STATUS, CREW_PROFILE FROM CREW WHERE CREW_NAME = ? LIMIT 1";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private boolean insert(CrewDTO crewDTO) {
		return false;
	}

	public boolean updateBattleTrue(CrewDTO crewDTO) {
		// 크루 참여상태 참여로 변경 // TODO 크루전 신청 페이지 추가(2024.11.01)
		int result = jdbcTemplate.update(UPDATE_BATTLE_STATUS_TRUE, crewDTO.getCrew_num());
			if(result <= 0) {
				return false;
			}
		return true;
	}

	public boolean updateBattleFalse(CrewDTO crewDTO) {
		// 크루 참여상태 비참여로 변경 // TODO 관리자 페이지 추가(2024.11.01)
		int result = jdbcTemplate.update(UPDATE_BATTLE_STATUS_FALSE, crewDTO.getCrew_num());
			if(result <= 0) {
				return false;
			}
		return true;
	}

	private boolean delete(CrewDTO crewDTO) {
		return false;
	}
	//크루 PK로 크루 검색 CREW_NUM
	public CrewDTO selectOne(CrewDTO crewDTO){
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE, args, new CrewRowMapperOne());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public CrewDTO selectOneName(CrewDTO crewDTO){
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_name()};
		try {
			result = jdbcTemplate.queryForObject(ONE_SEARCH_CREW_NAME, args, new CrewRowMapperOne());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//크루 총 개수
	public CrewDTO selectOneCount(CrewDTO crewDTO){
		CrewDTO result = null;
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT, new CrewRowMapperOneCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//해당 크루 현재 인원수 CREW_NUM
	public CrewDTO selectOneCountCurretMemberSize(CrewDTO crewDTO) {
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_num(), crewDTO.getCrew_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT_CURRENT_MEMBER_SIZE, args, new CrewRowMapperOneCountCurrentMemberSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 크루의 참여상태 확인
	public CrewDTO selectOneBattleStatus(CrewDTO crewDTO) {
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE_BATTLE_STATUS, args, new CrewRowMapperOneCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//(페이지네이션) 크루 전체 목록 Crew_min_num, Crew_max_num
	public List<CrewDTO> selectAll(CrewDTO crewDTO){
		List<CrewDTO> result = null;
		int offset = crewDTO.getCrew_min_num(); // 페이지에서 시작 인덱스
		Object[] args = {offset,10};
		try {
			result = jdbcTemplate.query(ALL, args, new CrewRowMapperAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

@Slf4j
class CrewRowMapperAll implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		log.info("검색 성공");
		CrewDTO crewDTO = new CrewDTO();
		try{
			crewDTO.setCrew_num(resultSet.getInt("CREW_NUM"));
		}catch(Exception e){
			System.err.println("Crew_num = 0");
			crewDTO.setCrew_num(0);
		}
		try{
			crewDTO.setCrew_name(resultSet.getString("CREW_NAME"));
		}catch(Exception e){
			System.err.println("setCrew_name = null");
			crewDTO.setCrew_name(null);
		}
		try{
			crewDTO.setCrew_description(resultSet.getString("CREW_DESCRIPTION"));
		}catch(Exception e){
			System.err.println("setCrew_description = null");
			crewDTO.setCrew_description(null);
		}
		try{
			crewDTO.setCrew_max_member_size(resultSet.getInt("CREW_MAX_MEMBER_SIZE"));
		}catch(Exception e){
			System.err.println("setCrew_max_member_size = 0");
			crewDTO.setCrew_max_member_size(0);
		}
		try{
			crewDTO.setCrew_leader(resultSet.getString("CREW_LEADER"));
		}catch(Exception e){
			System.err.println("setCrew_leader = null");
			crewDTO.setCrew_leader(null);
		}
		try{
			crewDTO.setCrew_battle_status(resultSet.getString("CREW_BATTLE_STATUS"));
		}catch(Exception e){
			System.err.println("setCrew_battle_status = null");
			crewDTO.setCrew_battle_status(null);
		}
		try{
			crewDTO.setCrew_profile(resultSet.getString("CREW_PROFILE"));
		}catch (Exception e){
			System.err.println("setCrew_profile = null");
			crewDTO.setCrew_profile(null);
		}
		return crewDTO;
	}
}

@Slf4j
class CrewRowMapperOne implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		log.info("검색 성공");
		CrewDTO crewDTO = new CrewDTO();
		try{
			crewDTO.setCrew_num(resultSet.getInt("CREW_NUM"));
		}catch(Exception e){
			System.err.println("Crew_num = 0");
			crewDTO.setCrew_num(0);
		}
		try{
			crewDTO.setCrew_name(resultSet.getString("CREW_NAME"));
		}catch(Exception e){
			System.err.println("setCrew_name = null");
			crewDTO.setCrew_name(null);
		}
		try{
			crewDTO.setCrew_description(resultSet.getString("CREW_DESCRIPTION"));
		}catch(Exception e){
			System.err.println("setCrew_description = null");
			crewDTO.setCrew_description(null);
		}
		try{
			crewDTO.setCrew_max_member_size(resultSet.getInt("CREW_MAX_MEMBER_SIZE"));
		}catch(Exception e){
			System.err.println("setCrew_max_member_size = 0");
			crewDTO.setCrew_max_member_size(0);
		}
		try{
			crewDTO.setCrew_leader(resultSet.getString("CREW_LEADER"));
		}catch(Exception e){
			System.err.println("setCrew_leader = null");
			crewDTO.setCrew_leader(null);
		}
		try{
			crewDTO.setCrew_battle_status(resultSet.getString("CREW_BATTLE_STATUS"));
		}catch(Exception e){
			System.err.println("setCrew_battle_status = null");
			crewDTO.setCrew_battle_status(null);
		}
		try{
			crewDTO.setCrew_profile(resultSet.getString("CREW_PROFILE"));
		}catch (Exception e){
			System.err.println("setCrew_profile = null");
			crewDTO.setCrew_profile(null);
		}
		return crewDTO;
	}
}

@Slf4j
class CrewRowMapperOneCount implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		log.info("검색 성공");
		CrewDTO crewDTO = new CrewDTO();
		try{
			crewDTO.setTotal(resultSet.getInt("CREW_TOTAL"));
		}catch(Exception e){
			System.err.println("Crew_total = 0");
			crewDTO.setTotal(0);
		}
		return crewDTO;
	}
}

@Slf4j
class CrewRowMapperOneCountCurrentMemberSize implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		log.info("검색 성공");
		CrewDTO crewDTO = new CrewDTO();
		try{
			crewDTO.setCrew_num(resultSet.getInt("CREW_NUM"));
		}catch(Exception e){
			System.err.println("Crew_num = 0");
			crewDTO.setCrew_num(0);
		}
		try{
			crewDTO.setCrew_name(resultSet.getString("CREW_NAME"));
		}catch(Exception e){
			System.err.println("setCrew_name = null");
			crewDTO.setCrew_name(null);
		}
		try{
			crewDTO.setCrew_max_member_size(resultSet.getInt("CREW_MAX_MEMBER_SIZE"));
		}catch(Exception e){
			System.err.println("setCrew_max_member_size = 0");
			crewDTO.setCrew_max_member_size(0);
		}
		try{
			crewDTO.setCrew_leader(resultSet.getString("CREW_LEADER"));
		}catch(Exception e){
			System.err.println("setCrew_leader = null");
			crewDTO.setCrew_leader(null);
		}
		try{
			crewDTO.setCrew_battle_status(resultSet.getString("CREW_BATTLE_STATUS"));
		}catch(Exception e){
			System.err.println("setCrew_battle_status = null");
			crewDTO.setCrew_battle_status(null);
		}
		try{
			crewDTO.setCrew_profile(resultSet.getString("CREW_PROFILE"));
		}catch (Exception e){
			System.err.println("setCrew_profile = null");
			crewDTO.setCrew_profile(null);
		}
		try{
			crewDTO.setCrew_current_member_size(resultSet.getInt("CREW_CURRENT_MEMBER_SIZE"));
		}catch (Exception e){
			System.err.println("Crew_current_member_size = 0");
			crewDTO.setCrew_current_member_size(0);
		}
		return crewDTO;
	}
}