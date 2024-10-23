package com.coma.app.biz.grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.coma.app.biz.common.JDBCUtil;

@Repository
public class GradeDAO implements GradeService{
	//데이터 추가
	private final String INSERT = "INSERT INTO GRADE(GRADE_PROFILE,GRADE_NAME,GRADE_MIN_POINT,GRADE_MAX_POINT)\r\n"
			+ "VALUES(?,?,?,?)";

	//max_point기준으로 내림차순 정렬해서 출력
	private final String ALL_DESC = "SELECT\r\n"
			+ "	GRADE_NUM,\r\n"
			+ "	GRADE_PROFILE,\r\n"
			+ "	GRADE_NAME,\r\n"
			+ "	GRADE_MIN_POINT,\r\n"
			+ "	GRADE_MAX_POINT\r\n"
			+ "FROM\r\n"
			+ "	GRADE\r\n"
			+ "ORDER BY\r\n"
			+ "	GRADE_MAX_POINT DESC";

	//PK로 등급 찾기 GRADE_NUM
	private final String ONE = "SELECT\r\n"
			+ "	GRADE_NUM,\r\n"
			+ "	GRADE_PROFILE,\r\n"
			+ "	GRADE_NAME,\r\n"
			+ "	GRADE_MIN_POINT,\r\n"
			+ "	GRADE_MAX_POINT\r\n"
			+ "FROM\r\n"
			+ "	GRADE\r\n"
			+ "WHERE\r\n"
			+ "	GRADE_NUM = ?";

	public boolean insert(GradeDTO gradeDTO) {
		System.out.println("grade.GradeDAO.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//데이터 추가
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, gradeDTO.getModel_grade_profile());
			pstmt.setString(2, gradeDTO.getModel_grade_name());
			pstmt.setInt(3, gradeDTO.getModel_grade_min_point());
			pstmt.setInt(4, gradeDTO.getModel_grade_max_point());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("grade.GradeDAO.insert 실패");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("grade.GradeDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("grade.GradeDAO.insert 성공");
		return true;
	}
	public boolean update(GradeDTO gradeDTO) {
		System.out.println("grade.GradeDAO.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("grade.GradeDAO.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("grade.GradeDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("grade.GradeDAO.update 성공");
		return true;
	}
	public boolean delete(GradeDTO gradeDTO) {
		System.err.println("grade.GradeDAO.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("grade.GradeDAO.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("grade.GradeDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("grade.GradeDAO.delete 성공");
		return true;
	}

	public GradeDTO selectOne(GradeDTO gradeDTO){
		System.out.println("grade.GradeDAO.selectOne 시작");
		GradeDTO data = null;
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//PK로 등급 찾기 GRADE_NUM
			pstmt=conn.prepareStatement(ONE);
			pstmt.setInt(1, gradeDTO.getModel_grade_num());

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("grade.GradeDAO.selectOne 검색 성공");
				data = new GradeDTO();
				data.setModel_grade_num(rs.getInt("GRADE_NUM"));
				data.setModel_grade_name(rs.getString("GRADE_NAME"));
				data.setModel_grade_profile(rs.getString("GRADE_PROFILE"));
				data.setModel_grade_min_point(rs.getInt("GRADE_MIN_POINT"));
				data.setModel_grade_max_point(rs.getInt("GRADE_MAX_POINT"));
			}
		} catch (SQLException e) {
			System.err.println("grade.GradeDAO.selectOne SQL문 실패");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("grade.GradeDAO.selectOne 성공");
		return data;
	}

	public ArrayList<GradeDTO> selectAll(GradeDTO gradeDTO){
		System.out.println("grade.GradeDAO.selectAll 시작");
		ArrayList<GradeDTO> datas = new ArrayList<GradeDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//max_point기준으로 내림차순 정렬해서 출력
			pstmt=conn.prepareStatement(ALL_DESC);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				GradeDTO data = new GradeDTO();
				data.setModel_grade_num(rs.getInt("GRADE_NUM"));
				data.setModel_grade_name(rs.getString("GRADE_NAME"));
				data.setModel_grade_profile(rs.getString("GRADE_PROFILE"));
				data.setModel_grade_min_point(rs.getInt("GRADE_MIN_POINT"));
				data.setModel_grade_max_point(rs.getInt("GRADE_MAX_POINT"));
				datas.add(data);
				rsCnt++;
			}

		}catch(SQLException e) {
			System.err.println("grade.GradeDAO.selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("grade.GradeDAO.selectAll 성공");
		return datas;

	}
}
