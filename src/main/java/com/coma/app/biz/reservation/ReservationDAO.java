package com.coma.app.biz.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAO {
	//예약 등록 RESERVATION_GYM_NUM, RESERVATION_DATE, RESERVATION_MEMBER_ID, RESERVATION_PRICE
	private final String INSERT = "INSERT INTO RESERVATION (RESERVATION_GYM_NUM, RESERVATION_DATE, RESERVATION_MEMBER_ID, RESERVATION_PRICE) " 
			+ "VALUES (?, ?, ?, ?);";
	
	//PK로 예약 정보 찾기 RESERVATION_NUM
	private final String ONE = "SELECT " 
			+ "    RESERVATION_NUM, " 
			+ "    RESERVATION_DATE, " 
			+ "    RESERVATION_GYM_NUM, " 
			+ "    RESERVATION_MEMBER_ID, " 
			+ "    RESERVATION_PRICE " 
			+ "FROM " 
			+ "    RESERVATION " 
			+ "WHERE " 
			+ "    RESERVATION_NUM = ?;";

	
	//해당 암벽장 예약 가능 개수 RESERVATION_GYM_NUM
	private final String ONE_COUNT = "SELECT G.GYM_RESERVATION_CNT - COUNT(*) AS RESERVATION_TOTAL "
			+ "FROM GYM G " 
			+ "LEFT JOIN RESERVATION R ON R.RESERVATION_GYM_NUM = G.GYM_NUM AND R.RESERVATION_DATE = ? " 
			+ "WHERE G.GYM_NUM = ? " 
			+ "GROUP BY G.GYM_RESERVATION_CNT;";

	
	//예약 취소 RESERVATION_NUM
	private final String DELETE = "DELETE FROM RESERVATION WHERE RESERVATION_NUM = ?;";

	
	//사용자 아이디로 예약한 내역 전부 출력 RESERVATION_MEMBER_ID
	private final String ALL = "SELECT\r\n"
			+ "	R.RESERVATION_NUM,\r\n"
			+ "	R.RESERVATION_DATE,\r\n"
			+ "	R.RESERVATION_GYM_NUM,\r\n"
			+ "	R.RESERVATION_MEMBER_ID,\r\n"
			+ "	R.RESERVATION_PRICE,\r\n"
			+ "	G.GYM_NAME\r\n"
			+ "FROM\r\n"
			+ "	RESERVATION R\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON \r\n"
			+ "	R.RESERVATION_MEMBER_ID = M.MEMBER_ID\r\n"
			+ "JOIN \r\n"
			+ "    GYM G\r\n"
			+ "ON \r\n"
			+ "    R.RESERVATION_GYM_NUM = G.GYM_NUM\r\n"
			+ "WHERE\r\n"
			+ "	R.RESERVATION_MEMBER_ID = ?";
	
	//사용자 아이디로 해당 암벽장에 예약한 날짜 중복 확인 RESERVATION_MEMBER_ID, RESERVATION_GYM_NUM, RESERVATION_DATE
	private final String ONE_RESERVATION = "SELECT\r\n"
			+ "	R.RESERVATION_NUM,\r\n"
			+ "	R.RESERVATION_DATE,\r\n"
			+ "	R.RESERVATION_GYM_NUM,\r\n"
			+ "	R.RESERVATION_MEMBER_ID,\r\n"
			+ "	R.RESERVATION_PRICE\r\n"
			+ "FROM\r\n"
			+ "	RESERVATION R\r\n"
			+ "JOIN\r\n"
			+ "	MEMBER M\r\n"
			+ "ON \r\n"
			+ "	R.RESERVATION_MEMBER_ID = M.MEMBER_ID\r\n"
			+ "WHERE\r\n"
			+ "	R.RESERVATION_MEMBER_ID = ? AND R.RESERVATION_GYM_NUM = ? AND R.RESERVATION_DATE = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(ReservationDTO reservationDTO) {
		System.out.println("com.coma.app.biz.reservation.insert 시작");

		//예약 등록 RESERVATION_GYM_NUM, RESERVATION_DATE, RESERVATION_MEMBER_ID, RESERVATION_PRICE
		int result=jdbcTemplate.update(INSERT,reservationDTO.getReservation_gym_num(), reservationDTO.getReservation_date(), reservationDTO.getReservation_member_id(), reservationDTO.getReservation_price());
		if(result<=0) {
			System.out.println("com.coma.app.biz.reservation.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.reservation.insert 성공");
		return true;
	}

	public boolean update(ReservationDTO reservationDTO) {
		System.out.println("com.coma.app.biz.reservation.update 시작"); // TODO 여기없는 CRUD
		return false;
	}

	public boolean delete(ReservationDTO reservationDTO) {
		System.out.println("com.coma.app.biz.reservation.delete 시작");

		//예약 취소 RESERVATION_NUM
		int result=jdbcTemplate.update(DELETE, reservationDTO.getReservation_num());
		if(result<=0) {
			System.out.println("com.coma.app.biz.reservation.delete SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.reservation.delete 성공");
		return true;
	}

	public ReservationDTO selectOne(ReservationDTO reservationDTO){
		System.out.println("com.coma.app.biz.reservation.selectOne 시작");

		ReservationDTO data=null;
		Object[] args={reservationDTO.getReservation_num()};

		try {
			//PK로 예약 정보 찾기 RESERVATION_NUM
			data=jdbcTemplate.queryForObject(ONE, args, new ReservationSelectRowMapperOne());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.reservation.selectOne 실패");
		}
		System.out.println("com.coma.app.biz.reservation.selectOne 성공");
		return data;
	}

	public ReservationDTO selectOneCount(ReservationDTO reservationDTO){
		System.out.println("com.coma.app.biz.reservation.selectOneCount 시작");

		ReservationDTO data=null;
		Object[] args={reservationDTO.getReservation_gym_num(), reservationDTO.getReservation_date()};
		try {
			//해당 암벽장 예약 가능 개수 RESERVATION_GYM_NUM
			data= jdbcTemplate.queryForObject(ONE_COUNT, args, new ReservationCountRowMapperOne());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.reservation.selectOneCount SQL문 실패");
		}
		System.out.println("com.coma.app.biz.reservation.selectOneCount 성공");
		return data;
	}

	public ReservationDTO selectOneReservation(ReservationDTO reservationDTO){
		System.out.println("com.coma.app.biz.reservation.selectOneReservation 시작");

		ReservationDTO data=null;
		Object[] args={reservationDTO.getReservation_member_id(), reservationDTO.getReservation_gym_num(), reservationDTO.getReservation_date()};
		try {
			//사용자 아이디로 해당 암벽장에 예약한 날짜 중복 확인 RESERVATION_MEMBER_ID, RESERVATION_GYM_NUM, RESERVATION_DATE
			data= jdbcTemplate.queryForObject(ONE_RESERVATION, args, new ReservationSelectRowMapperOne());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.reservation.selectOneReservation SQL문 실패");
		}
		System.out.println("com.coma.app.biz.reservation.selectOneReservation 성공");
		return data;
	}

	public List<ReservationDTO> selectAll(ReservationDTO reservationDTO){
		System.out.println("com.coma.app.biz.reservation.selectAll 시작");

		List<ReservationDTO> datas=null;
		Object[] args={reservationDTO.getReservation_member_id()};
		try {
			//사용자 아이디로 예약한 내역 전부 출력 RESERVATION_MEMBER_ID
			datas= jdbcTemplate.query(ALL, args, new ReservationRowMapperAll());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.reservation.selectAll SQL문 실패");
		}
		System.out.println("com.coma.app.biz.reservation.selectAll 성공");
		return datas;
	}
}

class ReservationCountRowMapperOne implements RowMapper<ReservationDTO> {

	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReservationDTO reservationDTO=new ReservationDTO();
		System.out.print("DB에서 가져온 데이터 {");
		reservationDTO.setReservation_total(rs.getInt("RESERVATION_TOTAL"));
		System.err.print("reservation_total = ["+reservationDTO.getReservation_total()+"]");
		System.out.println("}");
		return reservationDTO;
	};
}

class ReservationSelectRowMapperOne implements RowMapper<ReservationDTO> {

	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReservationDTO reservationDTO=new ReservationDTO();
		System.out.print("DB에서 가져온 데이터 {");
		reservationDTO.setReservation_num(rs.getInt("RESERVATION_NUM"));
		System.err.println("reservation_num = ["+reservationDTO.getReservation_num()+"]");
		reservationDTO.setReservation_date(rs.getString("RESERVATION_DATE"));
		System.err.println("reservation_date = ["+reservationDTO.getReservation_date()+"]");
		reservationDTO.setReservation_gym_num(rs.getInt("RESERVATION_GYM_NUM"));
		System.err.println("reservation_gym_num = ["+reservationDTO.getReservation_gym_num()+"]");
		reservationDTO.setReservation_member_id(rs.getString("RESERVATION_MEMBER_ID"));
		System.err.println("reservation_member_id = ["+reservationDTO.getReservation_member_id()+"]");
		reservationDTO.setReservation_price(rs.getInt("RESERVATION_PRICE"));
		System.err.print("reservation_price = ["+reservationDTO.getReservation_price()+"]");
		System.out.println("}");
		return reservationDTO;
	};
}

class ReservationRowMapperAll implements RowMapper<ReservationDTO> {

	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReservationDTO reservationDTO=new ReservationDTO();
		System.out.print("DB에서 가져온 데이터 {");
		reservationDTO.setReservation_num(rs.getInt("RESERVATION_NUM"));
		System.err.println("reservation_num = ["+reservationDTO.getReservation_num()+"]");
		reservationDTO.setReservation_date(rs.getString("RESERVATION_DATE"));
		System.err.println("reservation_date = ["+reservationDTO.getReservation_date()+"]");
		reservationDTO.setReservation_gym_num(rs.getInt("RESERVATION_GYM_NUM"));
		System.err.println("reservation_gym_num = ["+reservationDTO.getReservation_gym_num()+"]");
		reservationDTO.setReservation_member_id(rs.getString("RESERVATION_MEMBER_ID"));
		System.err.println("reservation_member_id = ["+reservationDTO.getReservation_member_id()+"]");
		reservationDTO.setReservation_price(rs.getInt("RESERVATION_PRICE"));
		System.err.println("reservation_price = ["+reservationDTO.getReservation_price()+"]");
		reservationDTO.setReservation_gym_name(rs.getString("GYM_NAME"));
		System.err.println("reservation_name = ["+reservationDTO.getReservation_gym_name()+"]");
		System.out.println("}");
		return reservationDTO;
	};
}
