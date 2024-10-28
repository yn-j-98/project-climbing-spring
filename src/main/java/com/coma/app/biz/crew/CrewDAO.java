package com.coma.app.biz.crew;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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


	//특정 크루 현재 인원수 CREW_NUM
	private final String ONE_COUNT_CURRENT_MEMBER_SIZE = "SELECT \n" +
			"    CREW_NUM,\n" +
			"    CREW_NAME,\n" +
			"    CREW_DESCRIPTION,\n" +
			"    CREW_MAX_MEMBER_SIZE,\n" +
			"    CREW_LEADER,\n" +
			"    CREW_BATTLE_STATUS,\n" +
			"    CREW_PROFILE,\n" +
			"    (SELECT COUNT(*) FROM member WHERE MEMBER_CREW_NUM = ?) AS CREW_CURRENT_MEMBER_SIZE\n" +
			"FROM \n" +
			"    CREW \n" +
			"WHERE \n" +
			"    CREW_NUM = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean insert(CrewDTO crewDTO) {
		System.out.println("crew.CrewDAO.insert 시작");
		System.out.println("crew.CrewDAO.insert 성공");
		return true;
	}
	public boolean update(CrewDTO crewDTO) {
		System.out.println("crew.CrewDAO.update 시작");
		System.out.println("crew.CrewDAO.update 성공");
		return true;
	}
	public boolean delete(CrewDTO crewDTO) {
		System.err.println("crew.CrewDAO.delete시작");
		System.out.println("crew.CrewDAO.delete 성공");
		return true;
	}

	//크루 PK로 크루 검색 CREW_NUM
	public CrewDTO selectOne(CrewDTO crewDTO){
		System.out.println("crew.CrewDAO.selectOne 시작");
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE, args, new CrewRowMapperOne());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.board.BoardDAO.selectOneSearchIdCount 실패 " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	//크루 총 개수
	public CrewDTO selectOneCount(CrewDTO crewDTO){
		System.out.println("crew.CrewDAO.selectOneCount 시작");
		CrewDTO result = null;
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT, new CrewRowMapperOneCount());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.crew.CrewDAO.selectOneCount 실패 " + ONE_COUNT);
			e.printStackTrace();
		}
		return result;
	}

	//해당 크루 현재 인원수 CREW_NUM
	public CrewDTO selectOneCountCurretMemberSize(CrewDTO crewDTO) {
		System.out.println("crew.CrewDAO.selectOneCountCurretMemberSize 시작");
		CrewDTO result = null;
		Object[] args = {crewDTO.getCrew_num(), crewDTO.getCrew_num()};
		try {
			result = jdbcTemplate.queryForObject(ONE_COUNT_CURRENT_MEMBER_SIZE, args, new CrewRowMapperOneCountCurrentMemberSize());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.crew.CrewDAO.selectOneCountCurretMemberSize 실패 " + ONE_COUNT_CURRENT_MEMBER_SIZE);
			e.printStackTrace();
		}
		return result;
	}

	//(페이지네이션) 크루 전체 목록 Crew_min_num, Crew_max_num
	public List<CrewDTO> selectAll(CrewDTO crewDTO){
		System.out.println("crew.CrewDAO.selectAll 시작");
		List<CrewDTO> result = null;
		int minNum = crewDTO.getCrew_min_num(); // 페이지에서 시작 인덱스
		int offset = minNum;
		Object[] args = {offset,10};
		try {
			result = jdbcTemplate.query(ALL, args, new CrewRowMapperAll());
		} catch (Exception e) {
			System.err.println("	[에러]com.coma.app.biz.crew_board.selectAllCrewBoard 실패 " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}

class CrewRowMapperAll implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew.selectAll 검색 성공");
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


class CrewRowMapperOne implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew.selectOne 검색 성공");
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

class CrewRowMapperOneCount implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew.selectOneCount 검색 성공");
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

class CrewRowMapperOneCountCurrentMemberSize implements RowMapper<CrewDTO> {
	@Override
	public CrewDTO mapRow(ResultSet resultSet, int i) throws SQLException {
		System.out.println("com.coma.app.biz.crew.selectOneCountCurrentMemberSize 검색 성공");
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