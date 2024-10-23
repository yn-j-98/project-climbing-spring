package com.coma.app.biz.grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gradeService")
public class GradeServiceImpl implements GradeService{

	@Autowired
	GradeDAO gradeDAO;
	
	@Override
	public List<GradeDTO> selectAll(GradeDTO gradeDTO) {
		return this.gradeDAO.selectAll(gradeDTO);
	}

	@Override
	public GradeDTO selectOne(GradeDTO gradeDTO) {
		return this.gradeDAO.selectOne(gradeDTO);
	}

	@Override
	public boolean insert(GradeDTO gradeDTO) {
		return this.gradeDAO.insert(gradeDTO);
	}

	@Override
	public boolean update(GradeDTO gradeDTO) {
		return this.gradeDAO.update(gradeDTO);
	}

	@Override
	public boolean delete(GradeDTO gradeDTO) {
		return this.gradeDAO.delete(gradeDTO);
	}
}
