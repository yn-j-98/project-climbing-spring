package com.coma.app.biz.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDAO {
	//아이디로 찾기 FIXME 관리자 권한 추가(char값 'T','F')
	private final String SEARCH_ID = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ID = ?";

	//아이디 비밀번호로 찾기 MEMBER_ID, MEMBER_PASSWORD
	private final String SEARCH_ID_PASSWORD = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ID = ? AND MEMBER_PASSWORD= ? ";

	//크루에 속한 회원목록 조회 MEMBER_CREW_NUM
	private final String SEARCH_CREW = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_CREW_NUM = ?";

	//랭킹높은순으로 정렬 관리자 FIXME 관리자 권한이 아닌사람들만
	private final String SEARCH_RANK = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ROLE='F'\r\n"
			+ "ORDER BY MEMBER_TOTAL_POINT DESC";

	//회원가입
	private final String INSERT = "INSERT INTO MEMBER(MEMBER_ID,MEMBER_NAME,MEMBER_PASSWORD,MEMBER_PHONE,MEMBER_LOCATION) \r\n"
			+ "VALUES(?,?,?,?,?)";

	//회원탈퇴
	private final String DELETE = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";

	//회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
	private final String UPDATE_ALL = "UPDATE MEMBER\r\n"
			+ "SET\r\n"
			+ "	MEMBER_PASSWORD = ?,\r\n"
			+ "	MEMBER_PROFILE = ?,\r\n"
			+ "	MEMBER_PHONE = ?,\r\n"
			+ "	MEMBER_LOCATION = ?\r\n"
			+ "WHERE MEMBER_ID = ?";

	//회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
	private final String UPDATE_WITHOUT_PROFILE = "UPDATE MEMBER\r\n"
			+ "SET\r\n"
			+ "	MEMBER_PASSWORD = ?,\r\n"
			+ "	MEMBER_PHONE = ?,\r\n"
			+ "	MEMBER_LOCATION = ?\r\n"
			+ "WHERE MEMBER_ID = ?";

	//크루가입 (크루가입시 가입날짜입력때문에 분리) MEMBER_ID
	private final String UPDATE_CREW = "UPDATE MEMBER\r\n" //FIXME 관리자 권한이 아닌 사람들만
			+ "SET\r\n"
			+ "	MEMBER_CREW_NUM = 1,\r\n"
			+ "	MEMBER_CREW_JOIN_DATE = SYSDATE()\r\n"
			+ "WHERE MEMBER_ID = ?";

	//관리자 권한 변경 MEMBER_ROLE, MEMBER_ID
	private final String UPDATE_ADMIN = "UPDATE MEMBER SET MEMBER_ROLE = '?' WHERE MEMBER_ID = '?'";

	//관리자가 아닌 신규회원 출력 (기간 7일)
	private final String ALL_NEW = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_REGISTRATION_DATE >= DATE_ADD(SYSDATE(), INTERVAL - (INTERVAL '7' DAY) * 86400 SECOND) AND ROLE='F'";

	//크루 랭킹 상위 10개 조회
	private final String ALL_TOP10_CREW_RANK = "SELECT \n" +
			"    C.CREW_PROFILE,\n" +
			"    C.CREW_NAME,\n" +
			"    SUM(M.MEMBER_TOTAL_POINT) AS MEMBER_CREW_RANK\n" +
			"FROM \n" +
			"    MEMBER M\n" +
			"JOIN \n" +
			"    CREW C ON M.MEMBER_CREW_NUM = C.CREW_NUM\n" +
			"GROUP BY \n" +
			"    C.CREW_PROFILE,\n" +
			"    C.CREW_NAME\n" +
			"ORDER BY \n" +
			"    MEMBER_CREW_RANK DESC\n" +
			"LIMIT 10";

	//상위 개인 랭킹 10개
	private final String ALL_TOP10_RANK = "SELECT\n" +
			"    MEMBER_NAME,\n" +
			"    MEMBER_PROFILE\n" +
			"FROM\n" +
			"    MEMBER\n" +
			"ORDER BY\n" +
			"    MEMBER_TOTAL_POINT DESC\n" +
			"LIMIT 10";

	//특정 크루에 속한 사용자 이름 전부 조회 CREW_NUM
	private final String ALL_SEARCH_CREW_MEMBER_NAME = "SELECT MEMBER_NAME FROM MEMBER M JOIN CREW C ON C.CREW_NUM = M.MEMBER_CREW_NUM WHERE CREW_NUM = ?";

	//특정 사용자가 속한 크루 찾기 MEMBER_ID
	private final String SEARCH_MY_CREW = "SELECT\r\n"
			+ "	M.MEMBER_CREW_NUM\r\n"
			+ "FROM\r\n"
			+ "	MEMBER M\r\n"
			+ "JOIN\r\n"
			+ "	CREW C\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_CREW_NUM = C.CREW_NUM\r\n"
			+ "WHERE\r\n"
			+ "	MEMBER_ID = ?";

	//크루 랭킹 전체 출력
	private final String ALL_CREW_RANK = "SELECT\r\n"
			+ "	C.CREW_NUM,\r\n"
			+ "    C.CREW_NAME,\r\n"
			+ "    C.CREW_LEADER,\r\n"
			+ "    C.CREW_MAX_MEMBER_SIZE,\r\n"
			+ "    COUNT(M.MEMBER_ID) AS CREW_CURRENT_SIZE,\r\n"
			+ "    SUM(M.MEMBER_TOTAL_POINT) AS MEMBER_TOTAL_POINT\r\n"
			+ "FROM\r\n"
			+ "    CREW C\r\n"
			+ "JOIN\r\n"
			+ "    MEMBER M \r\n"
			+ "ON \r\n"
			+ "    M.MEMBER_CREW_NUM = C.CREW_NUM\r\n"
			+ "GROUP BY\r\n"
			+ "	C.CREW_NUM,\r\n"
			+ "    C.CREW_NAME,\r\n"
			+ "    C.CREW_LEADER,\r\n"
			+ "    C.CREW_MAX_MEMBER_SIZE\r\n"
			+ "ORDER BY\r\n"
			+ "    MEMBER_TOTAL_POINT DESC";

	//사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
	private final String UPDATE_CURRENT_POINT = "UPDATE MEMBER SET MEMBER_CURRENT_POINT = ? WHERE MEMBER_ID = ?";


	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.insert 시작");

		//회원가입 MEMBER_ID ,MEMBER_NAME, MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION
		int result = jdbcTemplate.update(INSERT, memberDTO.getMember_id(), memberDTO.getMember_name(), memberDTO.getMember_password(), memberDTO.getMember_phone(), memberDTO.getMember_location());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.insert 성공");
		return true;
	}

	public boolean updateAll(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.updateAll 시작");
		//회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_ALL, memberDTO.getMember_password(), memberDTO.getMember_profile(), memberDTO.getMember_phone(), memberDTO.getMember_location(), memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.updateAll SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.updateAll 성공");
		return true;
	}

	public boolean updateWithoutProfile(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.updateWithoutProfile 시작");
		//회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_WITHOUT_PROFILE, memberDTO.getMember_password(), memberDTO.getMember_phone(), memberDTO.getMember_location(), memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.updateWithoutProfile SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.updateWithoutProfile 성공");
		return true;
	}

	public boolean updateCrew(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.updateCrew 시작");
		//크루가입 (크루가입시 가입날짜입력때문에 분리) MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_CREW, memberDTO.getMember_crew_num(), memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.updateCrew SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.updateCrew 성공");
		return true;
	}

	public boolean updateAdmin(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.updateAdmin 시작");
		//관리자 권한 변경 MEMBER_ROLE, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_ADMIN, memberDTO.getMember_role(), memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.updateAdmin SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.updateAdmin 성공");
		return true;
	}

	public boolean updateCurrentPoint(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.updateCurrentPoint 시작");
		//사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_CURRENT_POINT, memberDTO.getMember_current_point(), memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.updateCurrentPoint SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.updateCurrentPoint 성공");
		return true;
	}

	public boolean delete(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.delete 시작");

		//회원탈퇴 MEMBER_ID
		int result = jdbcTemplate.update(DELETE, memberDTO.getMember_id());
		if (result <= 0) {
			System.out.println("com.coma.app.biz.member.delete SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.delete 성공");
		return true;
	}

	public MemberDTO selectOneSearchId(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectOneSearchId 시작");

		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id()};
		try {
			//아이디로 찾기 MEMBER_ID FIXME 관리자 권한 추가(char값 'T','F')
			data = jdbcTemplate.queryForObject(SEARCH_ID, args, new MemberSelectRowMapperOne());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectOneSearchId SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectOneSearchId 성공");
		return data;
	}

	public MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectOneSearchIdPassword 시작");

		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id(), memberDTO.getMember_password()};
		try {
			//아이디 비밀번호로 찾기 MEMBER_ID, MEMBER_PASSWORD
			data = jdbcTemplate.queryForObject(SEARCH_ID_PASSWORD, args, new MemberSelectRowMapperOne());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectOneSearchIdPassword SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectOneSearchIdPassword 성공");
		return data;
	}

	public MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectOneSearchMyCrew 시작");

		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id()};
		try {
			//특정 사용자가 속한 크루 찾기 MEMBER_ID
			data = jdbcTemplate.queryForObject(SEARCH_MY_CREW, args, new MemberSearchCrewRowMapperOne());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectOneSearchMyCrew SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectOneSearchMyCrew 성공");
		return data;
	}

	public List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllSearchRank 시작");

		List<MemberDTO> datas = null;
		try {
			//랭킹높은순으로 정렬 관리자 FIXME 관리자 권한이 아닌사람들만
			datas = jdbcTemplate.query(SEARCH_RANK, new MemberSelectRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllSearchRank SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllSearchRank 성공");
		return datas;
	}

	public List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllCrewRank 시작");

		List<MemberDTO> datas = null;
		try {
			//크루 랭킹 높은순으로 전체 출력
			datas = jdbcTemplate.query(ALL_CREW_RANK, new MemberCrewRankRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllCrewRank SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllCrewRank 성공");
		return datas;
	}

	public List<MemberDTO> selectAllNew(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllNew 시작");

		List<MemberDTO> datas = null;
		try {
			//관리자가 아닌 신규회원 출력 (기간 7일)
			datas = jdbcTemplate.query(ALL_NEW, new MemberSelectRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllNew SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllNew 성공");
		return datas;
	}

	public List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllTop10CrewRank 시작");

		List<MemberDTO> datas = null;
		try {
			//크루 랭킹 상위 10개
			datas = jdbcTemplate.query(ALL_TOP10_CREW_RANK, new MemberTop10CrewRankRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllTop10CrewRank SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllTop10CrewRank 성공");
		return datas;
	}

	public List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllTop10Rank 시작");

		List<MemberDTO> datas = null;
		try {
			//개인 랭킹 상위  10개
			datas = jdbcTemplate.query(ALL_TOP10_RANK, new MemberTop10RankRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllTop10Rank SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllTop10Rank 성공");
		return datas;
	}

	public List<MemberDTO> selectAllSearchCrew(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllSearchCrew 시작");

		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getMember_crew_num()};
		try {
			//크루에 속한 회원목록 조회 MEMBER_CREW_NUM
			datas = jdbcTemplate.query(SEARCH_CREW, args, new MemberSelectRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllSearchCrew SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllSearchCrew 성공");
		return datas;
	}

	public List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAllSearchCrewMemberName 시작");

		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getMember_crew_num()};
		try {
			//특정 크루에 속한 사용자 이름 전부 조회 CREW_NUM
			datas = jdbcTemplate.query(ALL_SEARCH_CREW_MEMBER_NAME, args, new MemberSearchCrewMemberNameRowMapperAll());
		} catch (Exception e) {
			System.out.println("com.coma.app.biz.member.selectAllSearchCrewMemberName SQL문 실패");
		}
		System.out.println("com.coma.app.biz.member.selectAllSearchCrewMemberName 성공");
		return datas;
	}
}

class MemberSelectRowMapperOne implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		System.err.println("member_id = ["+memberDTO.getMember_id()+"]");
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		System.err.println("member_password = ["+memberDTO.getMember_password()+"]");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.println("member_name = ["+memberDTO.getMember_name()+"]");
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		System.err.println("member_phone = ["+memberDTO.getMember_phone()+"]");
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		System.err.println("member_profile = ["+memberDTO.getMember_profile()+"]");
		memberDTO.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE"));
		System.err.println("member_registration_date = ["+memberDTO.getMember_registration_date()+"]");
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		System.err.println("member_current_point = ["+memberDTO.getMember_current_point()+"]");
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		System.err.println("member_total_point = ["+memberDTO.getMember_total_point()+"]");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.println("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		System.err.println("member_crew_join_date = ["+memberDTO.getMember_crew_join_date()+"]");
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		System.err.println("member_location = ["+memberDTO.getMember_location()+"]");
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		System.err.print("member_role = ["+memberDTO.getMember_role()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberSearchCrewMemberNameRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.print("member_name = ["+memberDTO.getMember_name()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberTop10RankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.println("member_name = ["+memberDTO.getMember_name()+"]");
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		System.err.print("member_profile = ["+memberDTO.getMember_profile()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberTop10CrewRankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_crew_profile(rs.getString("CREW_PROFILE"));
		System.err.println("member_crew_profile = ["+memberDTO.getMember_crew_profile()+"]");
		memberDTO.setMember_crew_name(rs.getString("CREW_NAME"));
		System.err.print("member_crew_name = ["+memberDTO.getMember_crew_name()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberCrewRankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_crew_num(rs.getInt("CREW_NUM"));
		System.err.println("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		memberDTO.setMember_crew_name(rs.getString("CREW_NAME"));
		System.err.println("member_crew_name = ["+memberDTO.getMember_crew_name()+"]");
		memberDTO.setMember_crew_leader(rs.getString("CREW_LEADER"));
		System.err.println("member_crew_leader = ["+memberDTO.getMember_crew_leader()+"]");
		memberDTO.setMember_crew_current_size(rs.getInt("CREW_CURRENT_SIZE"));
		System.err.println("member_crew_current_size = ["+memberDTO.getMember_crew_current_size()+"]");
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		System.err.print("member_total_point = ["+memberDTO.getMember_total_point()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberSelectRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		System.err.println("member_id = ["+memberDTO.getMember_id()+"]");
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		System.err.println("member_password = ["+memberDTO.getMember_password()+"]");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.println("member_name = ["+memberDTO.getMember_name()+"]");
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		System.err.println("member_phone = ["+memberDTO.getMember_phone()+"]");
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		System.err.println("member_profile = ["+memberDTO.getMember_profile()+"]");
		memberDTO.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE"));
		System.err.println("member_registration_date = ["+memberDTO.getMember_registration_date()+"]");
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		System.err.println("member_current_point = ["+memberDTO.getMember_current_point()+"]");
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		System.err.println("member_total_point = ["+memberDTO.getMember_total_point()+"]");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.println("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		System.err.println("member_crew_join_date = ["+memberDTO.getMember_crew_join_date()+"]");
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		System.err.println("member_location = ["+memberDTO.getMember_location()+"]");
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		System.err.print("member_role = ["+memberDTO.getMember_role()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberSearchCrewRowMapperOne implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.print("member_crew_num = [" + memberDTO.getMember_crew_num() + "]");
		System.out.println("}");
		return memberDTO;
	};
}

