package com.coma.app.biz.grade;

import java.util.List;

public interface GradeService {
	List<GradeDTO> selectAll(GradeDTO gradeDTO);
	GradeDTO selectOne(GradeDTO gradeDTO);
	boolean insert(GradeDTO gradeDTO);
	boolean update(GradeDTO gradeDTO);
	boolean delete(GradeDTO gradeDTO);
}
