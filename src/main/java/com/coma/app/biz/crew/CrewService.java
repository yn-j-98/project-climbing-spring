package com.coma.app.biz.crew;

import java.util.List;

public interface CrewService {
	List<CrewDTO> selectAll(CrewDTO crewDTO);

	CrewDTO selectOne(CrewDTO crewDTO);
	CrewDTO selectOneCount(CrewDTO crewDTO);
	CrewDTO selectOneCountCurretMemberSize(CrewDTO crewDTO);
	CrewDTO selectOneBattleStatus(CrewDTO crewDTO);

	boolean insert(CrewDTO crewDTO);

	boolean updateBattleTrue(CrewDTO crewDTO);
	boolean updateBattleFalse(CrewDTO crewDTO);

	boolean delete(CrewDTO crewDTO);
}
