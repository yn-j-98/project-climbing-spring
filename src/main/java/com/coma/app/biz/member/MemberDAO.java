package com.coma.app.biz.member;

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

	//랭킹높은순으로 정렬 관리자
	private final String SEARCH_RANK = "SELECT MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_PHONE, MEMBER_PROFILE, MEMBER_REGISTRATION_DATE, MEMBER_CURRENT_POINT, MEMBER_TOTAL_POINT, MEMBER_CREW_NUM, MEMBER_CREW_JOIN_DATE, MEMBER_LOCATION, MEMBER_ROLE \n" +
			"FROM MEMBER \n" +
			"WHERE MEMBER_ROLE = 'F' \n" +
			"ORDER BY MEMBER_TOTAL_POINT DESC \n" +
			"LIMIT ?, ?";

	//회원가입
	private final String INSERT = "INSERT INTO MEMBER(MEMBER_ID,MEMBER_NAME,MEMBER_PASSWORD,MEMBER_PHONE,MEMBER_LOCATION) \r\n"
			+ "VALUES(?,?,?,?,?)";

	//회원탈퇴 // TODO 회원 관리 페이지
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
			+ "	MEMBER_CREW_NUM = ?,\r\n"
			+ "	MEMBER_CREW_JOIN_DATE = NOW()\r\n"
			+ "WHERE MEMBER_ID = ?";

	//관리자 권한 변경 MEMBER_ROLE, MEMBER_ID // TODO 회원 관리 페이지
	private final String UPDATE_ADMIN = """
	UPDATE MEMBER SET MEMBER_PASSWORD = ?, 
                  MEMBER_NAME = ?,
                  MEMBER_CURRENT_POINT = ?,
                  MEMBER_LOCATION = ?,
                  MEMBER_CREW_NUM = ?,
                  MEMBER_PHONE = ?,
                  MEMBER_ROLE = ?
              WHERE MEMBER_ID = ?
	""";

	//관리자가 아닌 신규회원 출력 (기간 7일)
	private final String ALL_NEW = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_REGISTRATION_DATE >= DATE_ADD(SYSDATE(), INTERVAL - 7 DAY) AND MEMBER_ROLE='F'";

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

	//관리자를 제외한 사용자 전체 수 출력 // TODO 관리자 메인 페이지
	private final String ONE_COUNT_ADMIN = "SELECT COUNT(*) AS MEMBER_TOTAL\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_ROLE = 'F'";

	//월별 가입자 수 출력 // TODO 관리자 메인 페이지
	private final String ALL_MONTH_COUNT_ADMIN = "SELECT\n"
			+ "DATE_FORMAT(MEMBER_REGISTRATION_DATE, '%Y-%m') AS MEMBER_RESERVATION_MONTH,\n"
			+ "    COUNT(*) AS MEMBER_TOTAL\n"
			+ "FROM\n"
			+ "    MEMBER\n"
			+ "GROUP BY\n"
			+ "    MEMBER_RESERVATION_MONTH\n"
			+ "ORDER BY\n"
			+ "    MEMBER_RESERVATION_MONTH";

	// 회원 검색(페이지네이션) // TODO 회원 관리 페이지
	private final String ALL_SEARCH_ADMIN = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_REGISTRATION_DATE\n" +
			"FROM MEMBER\n" +
			"LIMIT ?,?";

	// 회원 아이디로 검색(페이지네이션) // TODO 회원 관리 페이지
	private final String ALL_SEARCH_ID_ADMIN = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_REGISTRATION_DATE\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_ID = ?\n"
			+ "ORDER BY MEMBER_ID\n"
			+ "LIMIT ?,?";

	// 회원 가입날짜로 검색(페이지네이션) // TODO 회원 관리 페이지
	private final String ALL_SEARCH_DATE_ADMIN = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_REGISTRATION_DATE\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_REGISTRATION_DATE LIKE CONCAT('%', ?, '%')\n"
			+ "ORDER BY MEMBER_ID\n"
			+ "LIMIT ?,?";

	//회원 검색 카운트 // TODO 관리자 메인 페이지
	private final String ALL_SEARCH_ADMIN_COUNT = "SELECT COUNT(*) AS MEMBER_TOTAL\n"
			+ "FROM MEMBER";

	//회원 아이디로 검색 카운트 // TODO 관리자 메인 페이지
	private final String ALL_SEARCH_ID_ADMIN_COUNT = "SELECT COUNT(*) AS MEMBER_TOTAL\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_ID = ?";

	//회원 가입날짜로 검색 카운트 // TODO 관리자 메인 페이지
	private final String ALL_SEARCH_DATE_ADMIN_COUNT = "SELECT COUNT(*) AS MEMBER_TOTAL\n"
			+ "FROM MEMBER\n"
			+ "WHERE MEMBER_REGISTRATION_DATE LIKE CONCAT('%', ?, '%')";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(MemberDTO memberDTO) {
		//회원가입 MEMBER_ID ,MEMBER_NAME, MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION
		int result = jdbcTemplate.update(INSERT, memberDTO.getMember_id(), memberDTO.getMember_name(), memberDTO.getMember_password(), memberDTO.getMember_phone(), memberDTO.getMember_location());
        return result > 0;
    }

	public boolean updateAll(MemberDTO memberDTO) {
		//회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_ALL, memberDTO.getMember_password(), memberDTO.getMember_profile(), memberDTO.getMember_phone(), memberDTO.getMember_location(), memberDTO.getMember_id());
        return result > 0;
    }

	public boolean updateWithoutProfile(MemberDTO memberDTO) {
		//회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_WITHOUT_PROFILE, memberDTO.getMember_password(), memberDTO.getMember_phone(), memberDTO.getMember_location(), memberDTO.getMember_id());
        return result > 0;
    }

	public boolean updateCrew(MemberDTO memberDTO) {
		//크루가입 (크루가입시 가입날짜입력때문에 분리) MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_CREW, memberDTO.getMember_crew_num(), memberDTO.getMember_id());
        return result > 0;
    }

	public boolean updateAdmin(MemberDTO memberDTO) {
		//관리자 권한 변경 MEMBER_NAME = ?,
		//                  MEMBER_CURRENT_POINT = ?,
		//                  MEMBER_LOCATION = ?,
		//                  MEMBER_CREW_NUM = ?,
		//                  MEMBER_PHONE = ?,
		//                  MEMBER_ROLE = ?
		int result = jdbcTemplate.update(UPDATE_ADMIN, memberDTO.getMember_password(), memberDTO.getMember_name(), memberDTO.getMember_current_point(), memberDTO.getMember_location(), memberDTO.getMember_crew_num(), memberDTO.getMember_phone(), memberDTO.getMember_role(), memberDTO.getMember_id());
        return result > 0;
    }

	public boolean updateCurrentPoint(MemberDTO memberDTO) {
		//사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
		int result = jdbcTemplate.update(UPDATE_CURRENT_POINT, memberDTO.getMember_current_point(), memberDTO.getMember_id());
        return result > 0;
    }

	public boolean delete(MemberDTO memberDTO) {
		//회원탈퇴 MEMBER_ID
		int result = jdbcTemplate.update(DELETE, memberDTO.getMember_id());
        return result > 0;
    }

	public MemberDTO selectOneSearchId(MemberDTO memberDTO) {
		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id()};
		try {
			//아이디로 찾기 MEMBER_ID FIXME 관리자 권한 추가(char값 'T','F')
			data = jdbcTemplate.queryForObject(SEARCH_ID, args, new MemberSelectRowMapperOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO) {
		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id(), memberDTO.getMember_password()};
		try {
			//아이디 비밀번호로 찾기 MEMBER_ID, MEMBER_PASSWORD
			data = jdbcTemplate.queryForObject(SEARCH_ID_PASSWORD, args, new MemberSelectRowMapperOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO) {
		MemberDTO data = null;
		Object[] args = {memberDTO.getMember_id()};
		try {
			//특정 사용자가 속한 크루 찾기 MEMBER_ID
			data = jdbcTemplate.queryForObject(SEARCH_MY_CREW, args, new MemberSearchCrewRowMapperOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneCountAdmin(MemberDTO memberDTO) {
		MemberDTO data = null;
		try {
			//관리자를 제외한 사용자 전체 수 출력 // TODO 관리자 메인 페이지
			data = jdbcTemplate.queryForObject(ONE_COUNT_ADMIN, new MemberSearchCountOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneSearchCountAdmin(MemberDTO memberDTO) {
		MemberDTO data = null;
		try {
			//회원 검색 카운트 // TODO 관리자 메인 페이지
			data = jdbcTemplate.queryForObject(ALL_SEARCH_ADMIN_COUNT, new MemberSearchCountOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneSearchIdCountAdmin(MemberDTO memberDTO) {
		MemberDTO data = null;
		Object[] args = {memberDTO.getSearch_content()};
		try {
			//회원 아이디로 검색 카운트 // TODO 관리자 메인 페이지
			data = jdbcTemplate.queryForObject(ALL_SEARCH_ID_ADMIN_COUNT, args, new MemberSearchCountOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public MemberDTO selectOneSearchDateCountAdmin(MemberDTO memberDTO) {
		MemberDTO data = null;
		Object[] args = {memberDTO.getSearch_content()};
		try {
			//회원 가입날짜로 검색 카운트 // TODO 관리자 메인 페이지
			data = jdbcTemplate.queryForObject(ALL_SEARCH_DATE_ADMIN_COUNT, args, new MemberSearchCountOne());
		}
		catch (Exception e) {
		}
		return data;
	}

	public List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = { memberDTO.getMember_min_num() , memberDTO.getPage() };
		try {
			//랭킹높은순으로 정렬 관리자 (관리자 권한이 아닌사람들만)
			datas = jdbcTemplate.query( SEARCH_RANK, args, new MemberSelectRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		try {
			//크루 랭킹 높은순으로 전체 출력
			datas = jdbcTemplate.query(ALL_CREW_RANK, new MemberCrewRankRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllNew(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		try {
			//관리자가 아닌 신규회원 출력 (기간 7일)
			datas = jdbcTemplate.query(ALL_NEW, new MemberSelectRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		try {
			//크루 랭킹 상위 10개
			datas = jdbcTemplate.query(ALL_TOP10_CREW_RANK, new MemberTop10CrewRankRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		try {
			//개인 랭킹 상위  10개
			datas = jdbcTemplate.query(ALL_TOP10_RANK, new MemberTop10RankRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllSearchCrew(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getMember_crew_num()};
		try {
			//크루에 속한 회원목록 조회 MEMBER_CREW_NUM
			datas = jdbcTemplate.query(SEARCH_CREW, args, new MemberSelectRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getMember_crew_num()};
		try {
			//특정 크루에 속한 사용자 이름 전부 조회 CREW_NUM
			datas = jdbcTemplate.query(ALL_SEARCH_CREW_MEMBER_NAME, args, new MemberSearchCrewMemberNameRowMapperAll());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllMonthCountAdmin(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		try {
			//월별 가입자 수 출력 // TODO 관리자 메인 페이지
			datas = jdbcTemplate.query(ALL_MONTH_COUNT_ADMIN, new MemberSelectAllMonthCountAdmin());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllSearchAdmin(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getMember_min_num(),10};
		try {
			// 회원 검색(페이지네이션) // TODO 회원 관리 페이지
			datas = jdbcTemplate.query(ALL_SEARCH_ADMIN, args, new MemberSelectAllSearchAdmin());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllSearchIdAdmin(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getSearch_content(),memberDTO.getMember_min_num(),10};
		try {
			// 회원 아이디로 검색(페이지네이션) // TODO 회원 관리 페이지
			datas = jdbcTemplate.query(ALL_SEARCH_ID_ADMIN, args, new MemberSelectAllSearchAdmin());
		}
		catch (Exception e) {
		}
		return datas;
	}

	public List<MemberDTO> selectAllSearchDateAdmin(MemberDTO memberDTO) {
		List<MemberDTO> datas = null;
		Object[] args = {memberDTO.getSearch_content(),memberDTO.getMember_min_num(),10};
		try {
			// 회원 가입날짜로 검색(페이지네이션) // TODO 회원 관리 페이지
			datas = jdbcTemplate.query(ALL_SEARCH_DATE_ADMIN, args, new MemberSelectAllSearchAdmin());
		}
		catch (Exception e) {
		}
		return datas;
	}
}
@Slf4j
class MemberSelectRowMapperOne implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberSelectRowMapperOne DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		log.info("member_id = [{}]",memberDTO.getMember_id());
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		log.info("member_password = [{}]",memberDTO.getMember_password());
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		log.info("member_name = [{}]",memberDTO.getMember_name());
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		log.info("member_phone = [{}]",memberDTO.getMember_phone());
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		log.info("member_profile = [{}]",memberDTO.getMember_profile());
		memberDTO.setMember_registration_date(rs.getString("MEMBER_REGISTRATION_DATE"));
		log.info("member_registration_date = [{}]",memberDTO.getMember_registration_date());
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		log.info("member_current_point = [{}]",memberDTO.getMember_current_point());
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		log.info("member_total_point = [{}]",memberDTO.getMember_total_point());
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		log.info("member_crew_num = [{}]",memberDTO.getMember_crew_num());
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		log.info("member_crew_join_date = [{}]",memberDTO.getMember_crew_join_date());
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		log.info("member_location = [{}]",memberDTO.getMember_location());
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		log.info("member_role = [{}]",memberDTO.getMember_role());
		log.info("MemberSelectRowMapperOne DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberSearchCrewMemberNameRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberSearchCrewMemberNameRowMapperAll DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		log.info("member_name = [{}]",memberDTO.getMember_name());
		log.info("MemberSearchCrewMemberNameRowMapperAll DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberTop10RankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberTop10RankRowMapperAll DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		log.info("member_name = [{}]",memberDTO.getMember_name());
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		log.info("member_profile = [{}]",memberDTO.getMember_profile());
		log.info("MemberTop10RankRowMapperAll DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberTop10CrewRankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberTop10CrewRankRowMapperAll DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_crew_profile(rs.getString("CREW_PROFILE"));
		log.info("member_crew_profile = [{}]",memberDTO.getMember_crew_profile());
		memberDTO.setMember_crew_name(rs.getString("CREW_NAME"));
		log.info("member_crew_name = [{}]",memberDTO.getMember_crew_name());
		log.info("MemberTop10CrewRankRowMapperAll DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberCrewRankRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberCrewRankRowMapperAll DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_crew_num(rs.getInt("CREW_NUM"));
		log.info("member_crew_num = [{}]",memberDTO.getMember_crew_num());
		memberDTO.setMember_crew_name(rs.getString("CREW_NAME"));
		log.info("member_crew_name = [{}]",memberDTO.getMember_crew_name());
		memberDTO.setMember_crew_leader(rs.getString("CREW_LEADER"));
		log.info("member_crew_leader = [{}]",memberDTO.getMember_crew_leader());
		memberDTO.setMember_crew_current_size(rs.getInt("CREW_CURRENT_SIZE"));
		log.info("member_crew_current_size = [{}]",memberDTO.getMember_crew_current_size());
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		log.info("member_total_point = [{}]",memberDTO.getMember_total_point());
		log.info("MemberCrewRankRowMapperAll DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberSelectRowMapperAll implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		log.info("MemberSelectRowMapperAll DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		log.info("member_id = [{}]",memberDTO.getMember_id());
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		log.info("member_password = [{}]",memberDTO.getMember_password());
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		log.info("member_name = [{}]",memberDTO.getMember_name());
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		log.info("member_phone = [{}]",memberDTO.getMember_phone());
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		log.info("member_profile = [{}]",memberDTO.getMember_profile());
		memberDTO.setMember_registration_date(rs.getString("MEMBER_REGISTRATION_DATE"));
		log.info("member_registration_date = [{}]",memberDTO.getMember_registration_date());
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		log.info("member_current_point = [{}]",memberDTO.getMember_current_point());
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		log.info("member_total_point = [{}]",memberDTO.getMember_total_point());
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		log.info("member_crew_num = [{}]",memberDTO.getMember_crew_num());
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		log.info("member_crew_join_date = [{}]",memberDTO.getMember_crew_join_date());
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		log.info("member_location = [{}]",memberDTO.getMember_location());
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		log.info("member_role = [{}]",memberDTO.getMember_role());
		log.info("MemberSelectRowMapperAll DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberSearchCrewRowMapperOne implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		log.info("MemberSearchCrewRowMapperOne DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		log.info("member_crew_num = [{}]",memberDTO.getMember_crew_num());
		log.info("MemberSearchCrewRowMapperOne DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberSearchCountOne implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		log.info("MemberSearchCountOne DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setTotal(rs.getInt("MEMBER_TOTAL"));
		log.info("total = [{}]",memberDTO.getTotal());
		log.info("MemberSearchCountOne DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}

@Slf4j
class MemberSelectAllMonthCountAdmin implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		log.info("MemberSelectAllMonthCountAdmin DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_reservation_month(rs.getString("MEMBER_RESERVATION_MONTH"));
		log.info("member_reservation_month = [{}]",memberDTO.getMember_reservation_month());
		memberDTO.setTotal(rs.getInt("MEMBER_TOTAL"));
		log.info("total = [{}]",memberDTO.getTotal());
		log.info("MemberSelectAllMonthCountAdmin DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}
@Slf4j
class MemberSelectAllSearchAdmin implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		log.info("MemberSelectAllSearchAdmin DB에서 가져온 데이터 ↓↓↓↓↓");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		log.info("member_id = [{}]",memberDTO.getMember_id());
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		log.info("member_name = [{}]",memberDTO.getMember_name());
		memberDTO.setMember_registration_date(rs.getString("MEMBER_REGISTRATION_DATE"));
		log.info("member_registration_date = [{}]",memberDTO.getMember_registration_date());
		log.info("MemberSelectAllSearchAdmin DB에서 가져온 데이터 ↑↑↑↑↑");
		return memberDTO;
	};
}

