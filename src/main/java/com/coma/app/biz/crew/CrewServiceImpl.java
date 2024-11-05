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
	public List<CrewDTO> selectAllAdmin(CrewDTO crewDTO) { return this.crewDAO.selectAllAdmin(crewDTO); }

	@Override
	public CrewDTO selectOne(CrewDTO crewDTO) {
		return this.crewDAO.selectOne(crewDTO);
	}

	@Override
	public CrewDTO selectOneCount(CrewDTO crewDTO) {
		return this.crewDAO.selectOneCount(crewDTO);
	}

	@Override
	public CrewDTO selectOneCountCurretMemberSize(CrewDTO crewDTO) {return this.crewDAO.selectOneCountCurretMemberSize(crewDTO);}

	@Override
	public CrewDTO selectOneBattleStatus(CrewDTO crewDTO) {
		return this.crewDAO.selectOneBattleStatus(crewDTO);
	}

	@Override
	public boolean insert(CrewDTO crewDTO) {
		return false;
	}

	@Override
	public boolean updateBattleTrue(CrewDTO crewDTO) {
		return this.crewDAO.updateBattleTrue(crewDTO);
	}

	@Override
	public boolean updateBattleFalse(CrewDTO crewDTO) {
		return this.crewDAO.updateBattleFalse(crewDTO);
	}

	@Override
	public boolean delete(CrewDTO crewDTO) {
		return false;
	}
}
