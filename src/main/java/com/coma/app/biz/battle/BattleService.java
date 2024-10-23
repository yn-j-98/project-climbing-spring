package com.coma.app.biz.battle;

import java.util.List;

public interface BattleService {
	List<BattleDTO> selectAllActive(BattleDTO battleDTO);
	List<BattleDTO> selectAllGymBattle(BattleDTO battleDTO);
	List<BattleDTO> selectAllBattleAllTop4(BattleDTO battleDTO);
	BattleDTO selectOneSearchMemeberBattle(BattleDTO battleDTO);
	BattleDTO selectOneSearchBattle(BattleDTO battleDTO);
	BattleDTO selectOneCountActive(BattleDTO battleDTO);
	boolean insert(BattleDTO battleDTO);
	boolean update(BattleDTO battleDTO);
	boolean delete(BattleDTO battleDTO);
}
