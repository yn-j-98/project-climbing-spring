package com.coma.app.biz.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GradeDTO {
	private int grade_num;		     // 등급별 PK
	private String grade_profile;   // 등급 이미지 URL
	private String grade_name;     // 등급에 대한 이름
	private int grade_min_point;   // 최소 등급
	private int grade_max_point;   // 최대 등급

}
