package com.coma.app.biz.crew;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("crewService")
public class CrewServiceImpl implements CrewService{

	@Autowired
	CrewDAO crewDAO;
	
	@Override
	public List<CrewDTO> selectAll(CrewDTO crewDTO) {
		return this.crewDAO.selectAll(crewDTO);
	}

	@Override
	public CrewDTO selectOne(CrewDTO crewDTO) {
		return this.crewDAO.selectOne(crewDTO);
	}

	@Override
	public boolean insert(CrewDTO crewDTO) {
		return this.crewDAO.insert(crewDTO);
	}

	@Override
	public boolean update(CrewDTO crewDTO) {
		return this.crewDAO.update(crewDTO);
	}

	@Override
	public boolean delete(CrewDTO crewDTO) {
		return this.crewDAO.delete(crewDTO);
	}
}
