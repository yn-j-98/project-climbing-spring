package com.coma.app.biz.crew;

import java.util.List;

public interface CrewService {
	List<CrewDTO> selectAll(CrewDTO crewDTO);
	CrewDTO selectOne(CrewDTO crewDTO);
	CrewDTO selectOneCount(CrewDTO crewDTO);
	CrewDTO selectOneCountCurretMemberSize(CrewDTO crewDTO);
	boolean insert(CrewDTO crewDTO);
	boolean update(CrewDTO crewDTO);
	boolean delete(CrewDTO crewDTO);
}
