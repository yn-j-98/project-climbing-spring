package com.coma.app.biz.favorite;

public class FavoriteDTO {
	private int model_favorite_num; 	        // 좋아요 PK
	private String model_favorite_member_id;  // 좋아요 누른 사용자 FK
	private int model_favorite_gym_num;       // 좋아요 누른 암벽장 FK
	
	//DTO에만 있는 데이터
	private String model_favorite_condition;  // 개발자 데이터 검색

	public int getModel_favorite_num() {
		return model_favorite_num;
	}

	public void setModel_favorite_num(int model_favorite_num) {
		this.model_favorite_num = model_favorite_num;
	}

	public String getModel_favorite_member_id() {
		return model_favorite_member_id;
	}

	public void setModel_favorite_member_id(String model_favorite_member_id) {
		this.model_favorite_member_id = model_favorite_member_id;
	}

	public int getModel_favorite_gym_num() {
		return model_favorite_gym_num;
	}

	public void setModel_favorite_gym_num(int model_favorite_gym_num) {
		this.model_favorite_gym_num = model_favorite_gym_num;
	}

	public String getModel_favorite_condition() {
		return model_favorite_condition;
	}

	public void setModel_favorite_condition(String model_favorite_condition) {
		this.model_favorite_condition = model_favorite_condition;
	}

	@Override
	public String toString() {
		return "FavoriteDTO [model_favorite_num=" + model_favorite_num + ", model_favorite_member_id="
				+ model_favorite_member_id + ", model_favorite_gym_num=" + model_favorite_gym_num
				+ ", model_favorite_condition=" + model_favorite_condition + "]";
	}

	
}
