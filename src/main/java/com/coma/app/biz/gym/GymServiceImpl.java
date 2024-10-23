package com.coma.app.biz.gym;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gymService")
public class GymServiceImpl implements GymService{

	@Autowired
	GymDAO gymDAO;
	
	@Override
	public List<GymDTO> selectAll(GymDTO gymDTO) {
		return this.gymDAO.selectAll(gymDTO);
	}

	@Override
	public GymDTO selectOne(GymDTO gymDTO) {
		return this.gymDAO.selectOne(gymDTO);
	}

	@Override
	public boolean insert(GymDTO gymDTO) {
		return this.gymDAO.insert(gymDTO);
	}

	@Override
	public boolean update(GymDTO gymDTO) {
		return this.gymDAO.update(gymDTO);
	}

	@Override
	public boolean delete(GymDTO gymDTO) {
		return this.gymDAO.delete(gymDTO);
	}
}
