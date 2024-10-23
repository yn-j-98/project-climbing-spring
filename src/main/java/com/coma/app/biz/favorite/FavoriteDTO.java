package com.coma.app.biz.favorite;

public class FavoriteDTO {
	private int favorite_num; 	        // 좋아요 PK
	private String favorite_member_id;  // 좋아요 누른 사용자 FK
	private int favorite_gym_num;       // 좋아요 누른 암벽장 FK
	
	//DTO에만 있는 데이터
	private String favorite_condition;  // 개발자 데이터 검색

	public int getFavorite_num() {
		return favorite_num;
	}

	public void setFavorite_num(int favorite_num) {
		this.favorite_num = favorite_num;
	}

	public String getFavorite_member_id() {
		return favorite_member_id;
	}

	public void setFavorite_member_id(String favorite_member_id) {
		this.favorite_member_id = favorite_member_id;
	}

	public int getFavorite_gym_num() {
		return favorite_gym_num;
	}

	public void setFavorite_gym_num(int favorite_gym_num) {
		this.favorite_gym_num = favorite_gym_num;
	}

	public String getFavorite_condition() {
		return favorite_condition;
	}

	public void setFavorite_condition(String favorite_condition) {
		this.favorite_condition = favorite_condition;
	}

	@Override
	public String toString() {
		return "FavoriteDTO{" +
				"favorite_num=" + favorite_num +
				", favorite_member_id='" + favorite_member_id + '\'' +
				", favorite_gym_num=" + favorite_gym_num +
				", favorite_condition='" + favorite_condition + '\'' +
				'}';
	}
}
