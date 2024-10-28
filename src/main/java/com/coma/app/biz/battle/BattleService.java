package com.coma.app.biz.battle;

import java.util.List;
public interface BattleService {
	List<BattleDTO> selectAllActive(BattleDTO battleDTO);
	List<BattleDTO> selectAllGymBattle(BattleDTO battleDTO);
	List<BattleDTO> selectAllBattleAllTop4(BattleDTO battleDTO);
	List<BattleDTO> selectAllSearchBattleNum(BattleDTO battleDTO);
	List<BattleDTO> selectAllSearchBattleName(BattleDTO battleDTO);
	List<BattleDTO> selectAllBattle(BattleDTO battleDTO);
	List<BattleDTO> selectAllWinBattle(BattleDTO battleDTO);
	List<BattleDTO> selectAllCrewMemberName(BattleDTO battleDTO);
	BattleDTO selectOneSearchMemberBattle(BattleDTO battleDTO);
	BattleDTO selectOneSearchBattle(BattleDTO battleDTO);
	BattleDTO selectOneCountActive(BattleDTO battleDTO);
	BattleDTO selectOneCountActiveBattle(BattleDTO battleDTO);
	boolean insert(BattleDTO battleDTO);
	boolean InsertBattleModal(BattleDTO battleDTO);
	boolean update(BattleDTO battleDTO);
	boolean delete(BattleDTO battleDTO);
}
