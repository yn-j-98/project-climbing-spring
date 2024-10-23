package com.coma.app.biz.favorite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.coma.app.biz.common.JDBCUtil;

@Repository
public class FavoriteDAO implements FavoriteService{
	//좋아요 추가 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String INSERT = "INSERT INTO FAVORITE(FAVORITE_NUM,FAVORITE_MEMBER_ID,FAVORITE_GYM_NUM)\r\n"
			+ "VALUES ((SELECT NVL(MAX(FAVORITE_NUM),0)+1 FROM FAVORITE),?,?)";
	
	//좋아요 삭제 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String DELETE = "DELETE FROM FAVORITE WHERE FAVORITE_MEMBER_ID = ? AND FAVORITE_GYM_NUM =?";
	
	//좋아요 찾기 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String ONE = "SELECT FAVORITE_NUM,FAVORITE_MEMBER_ID,FAVORITE_GYM_NUM FROM FAVORITE\r\n"
			+ "WHERE FAVORITE_MEMBER_ID = ? AND FAVORITE_GYM_NUM =?";
	public boolean insert(FavoriteDTO favoriteDTO) {
		System.out.println("favorite.FavoriteDAO.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//좋아요 추가 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, favoriteDTO.getFavorite_member_id());
			pstmt.setInt(2, favoriteDTO.getFavorite_gym_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("favorite.FavoriteDAO.insert 실패");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("favorite.FavoriteDAO.insert SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("favorite.FavoriteDAO.insert 성공");
		return true;
	}
	public boolean update(FavoriteDTO favoriteDTO) {
		System.out.println("favorite.FavoriteDAO.update 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement("");
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("favorite.FavoriteDAO.update 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("favorite.FavoriteDAO.update SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("favorite.FavoriteDAO.update 성공");
		return true;
	}
	public boolean delete(FavoriteDTO favoriteDTO) {
		System.err.println("favorite.FavoriteDAO.delete 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//좋아요 삭제 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setString(1, favoriteDTO.getFavorite_member_id());
			pstmt.setInt(2, favoriteDTO.getFavorite_gym_num());
			int rs = pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("favorite.FavoriteDAO.delete 실패");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("favorite.FavoriteDAO.delete SQL문 실패");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("favorite.FavoriteDAO.delete 성공");
		return true;
	}

	public FavoriteDTO selectOne(FavoriteDTO favoriteDTO){
		System.out.println("favorite.FavoriteDAO.selectOne 시작");
		FavoriteDTO data = null;
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			//좋아요 찾기 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
			pstmt=conn.prepareStatement(ONE);
			pstmt.setString(1, favoriteDTO.getFavorite_member_id());
			pstmt.setInt(2, favoriteDTO.getFavorite_gym_num());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("favorite.FavoriteDAO.selectOne 검색 성공");
				data = new FavoriteDTO();
				data.setFavorite_num(rs.getInt("FAVORITE_NUM"));
				data.setFavorite_member_id(rs.getString("FAVORITE_MEMBER_ID"));
				data.setFavorite_gym_num(rs.getInt("FAVORITE_GYM_NUM"));
			}
		} catch (SQLException e) {
			System.err.println("favorite.FavoriteDAO.selectOne SQL문 실패");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("favorite.FavoriteDAO.selectOne 성공");
		return data;
	}

	public ArrayList<FavoriteDTO> selectAll(FavoriteDTO favoriteDTO){
		System.out.println("favorite.FavoriteDAO.selectAll 시작");
		ArrayList<FavoriteDTO> datas = new ArrayList<FavoriteDTO>();
		int rsCnt=1;//로그용
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt=conn.prepareStatement("");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rsCnt+"번행 출력중...");
				FavoriteDTO data = new FavoriteDTO();
				data.setFavorite_num(rs.getInt("FAVORITE_NUM"));
				data.setFavorite_member_id(rs.getString("FAVORITE_MEMBER_ID"));
				data.setFavorite_gym_num(rs.getInt("FAVORITE_GYM_NUM"));
				datas.add(data);
				rsCnt++;
			}

		}catch(SQLException e) {
			System.err.println("favorite.FavoriteDAO.selectAll SQL문 실패");
			return datas;
		}finally {
			JDBCUtil.disconnect(pstmt,conn);
		}
		System.out.println("favorite.FavoriteDAO.selectAll 성공");
		return datas;

	}
}
