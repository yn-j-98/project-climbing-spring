package com.coma.app.biz.grade;

public class GradeDTO {
	private int grade_num;		     // 등급별 PK
	private String grade_profile;   // 등급 이미지 URL
	private String grade_name;     // 등급에 대한 이름
	private int grade_min_point;   // 최소 등급
	private int grade_max_point;   // 최대 등급
	
	//DTO에만 존재하는 데이터
	private String grade_condition; // 개발자 데이터 

	public int getGrade_num() {
		return grade_num;
	}

	public void setGrade_num(int grade_num) {
		this.grade_num = grade_num;
	}

	public String getGrade_profile() {
		return grade_profile;
	}

	public void setGrade_profile(String grade_profile) {
		this.grade_profile = grade_profile;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public int getGrade_min_point() {
		return grade_min_point;
	}

	public void setGrade_min_point(int grade_min_point) {
		this.grade_min_point = grade_min_point;
	}

	public int getGrade_max_point() {
		return grade_max_point;
	}

	public void setGrade_max_point(int grade_max_point) {
		this.grade_max_point = grade_max_point;
	}

	public String getGrade_condition() {
		return grade_condition;
	}

	public void setGrade_condition(String grade_condition) {
		this.grade_condition = grade_condition;
	}

	@Override
	public String toString() {
		return "GradeDTO{" +
				"grade_num=" + grade_num +
				", grade_profile='" + grade_profile + '\'' +
				", grade_name='" + grade_name + '\'' +
				", grade_min_point=" + grade_min_point +
				", grade_max_point=" + grade_max_point +
				", grade_condition='" + grade_condition + '\'' +
				'}';
	}
}
