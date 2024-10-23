package com.coma.app.biz.favorite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteDAO {
	//좋아요 추가 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String INSERT = "INSERT INTO FAVORITE(FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM) VALUES(?, ?)";
	//좋아요 삭제 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String DELETE = "DELETE FROM FAVORITE WHERE FAVORITE_MEMBER_ID = ? AND FAVORITE_GYM_NUM =?";
	//좋아요 불러오기 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
	private final String ONE = "SELECT FAVORITE_NUM, FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM FROM FAVORITE WHERE FAVORITE_MEMBER_ID = ? AND FAVORITE_GYM_NUM =?";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(FavoriteDTO favoriteDTO) {
		System.out.println("com.coma.app.biz.favorite.insert 시작");
		//좋아요 추가 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
		int result=jdbcTemplate.update(INSERT, favoriteDTO.getModel_favorite_member_id(), favoriteDTO.getModel_favorite_gym_num());
		if(result<=0) {
			System.out.println("com.coma.app.biz.favorite.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.favorite.insert 성공");
		return true;
	}

	private boolean update(FavoriteDTO favoriteDTO) { // TODO 없는 CRUD
		System.out.println("com.coma.app.biz.favorite.update 시작");
		return false;
	}

	public boolean delete(FavoriteDTO favoriteDTO) {
		System.err.println("com.coma.app.biz.favorite.delete 시작");
		//좋아요 삭제 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
		int result=jdbcTemplate.update(DELETE, favoriteDTO.getModel_favorite_member_id(), favoriteDTO.getModel_favorite_gym_num());
		if(result<=0) {
			System.err.println("com.coma.app.biz.favorite.delete SQL문 실패");
			return false;
		}
		System.err.println("com.coma.app.biz.favorite.delete 성공");
		return true;
	}

	public FavoriteDTO selectOne(FavoriteDTO favoriteDTO){
		System.out.println("com.coma.app.biz.favorite.selectOne 시작");
		//좋아요 불러오기 FAVORITE_MEMBER_ID, FAVORITE_GYM_NUM
		FavoriteDTO data = null;
		Object[] args = { favoriteDTO.getModel_favorite_member_id(), favoriteDTO.getModel_favorite_gym_num() };
		try {
			data= jdbcTemplate.queryForObject(ONE,args,new FavoriteRowMapper());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.favorite.selectOne SQL문 실패");
		}
		System.out.println("com.coma.app.biz.favorite.selectOne 성공");
		return data;
	}

	public List<FavoriteDTO> selectAll(FavoriteDTO favoriteDTO){ // TODO 없는 CRUD
		System.out.println("favorite.FavoriteDAO.selectAll 시작");
		List<FavoriteDTO> datas = null;

		return datas;
	}
}

class FavoriteRowMapper implements RowMapper<FavoriteDTO> {

	public FavoriteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FavoriteDTO favoriteDTO=new FavoriteDTO();
		System.out.print("FavoriteRowMapper DB에서 가져온 데이터 {");
		favoriteDTO.setModel_favorite_num(rs.getInt("FAVORITE_NUM"));
		System.err.println("gym_num = ["+favoriteDTO.getModel_favorite_num()+"]");
		favoriteDTO.setModel_favorite_member_id(rs.getString("FAVORITE_MEMBER_ID"));
		System.err.println("member_id = ["+favoriteDTO.getModel_favorite_member_id()+"]");
		favoriteDTO.setModel_favorite_gym_num(rs.getInt("FAVORITE_GYM_NUM"));
		System.err.print("gym_num = ["+favoriteDTO.getModel_favorite_gym_num()+"]");
		System.out.println("}");
		return favoriteDTO;
	};
}
