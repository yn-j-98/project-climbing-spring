package com.coma.app.biz.grade;

public class GradeDTO {
	private int model_grade_num;		     // 등급별 PK
	private String model_grade_profile;   // 등급 이미지 URL
	private String model_grade_name;     // 등급에 대한 이름
	private int model_grade_min_point;   // 최소 등급
	private int model_grade_max_point;   // 최대 등급
	
	//DTO에만 존재하는 데이터
	private String model_grade_condition; // 개발자 데이터 
	
	public int getModel_grade_num() {
		return model_grade_num;
	}
	public void setModel_grade_num(int model_grade_num) {
		this.model_grade_num = model_grade_num;
	}
	public String getModel_grade_profile() {
		return model_grade_profile;
	}
	public void setModel_grade_profile(String model_grade_profile) {
		this.model_grade_profile = model_grade_profile;
	}
	public String getModel_grade_name() {
		return model_grade_name;
	}
	public void setModel_grade_name(String model_grade_name) {
		this.model_grade_name = model_grade_name;
	}
	public int getModel_grade_min_point() {
		return model_grade_min_point;
	}
	public void setModel_grade_min_point(int model_grade_min_point) {
		this.model_grade_min_point = model_grade_min_point;
	}
	public int getModel_grade_max_point() {
		return model_grade_max_point;
	}
	public void setModel_grade_max_point(int model_grade_max_point) {
		this.model_grade_max_point = model_grade_max_point;
	}
	public String getModel_grade_conditon() {
		return model_grade_condition;
	}
	public void setModel_grade_conditon(String model_grade_condition) {
		this.model_grade_condition = model_grade_condition;
	}
	@Override
	public String toString() {
		return "GradeDTO [model_grade_num=" + model_grade_num + ", model_grade_profile=" + model_grade_profile
				+ ", model_grade_name=" + model_grade_name + ", model_grade_min_point=" + model_grade_min_point
				+ ", model_grade_max_point=" + model_grade_max_point + ", model_grade_condition=" + model_grade_condition
				+ "]";
	}
	
	
}
