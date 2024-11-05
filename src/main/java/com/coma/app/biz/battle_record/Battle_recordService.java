package com.coma.app.biz.battle_record;

import java.util.List;

public interface Battle_recordService {
	List<Battle_recordDTO> selectAllWinner(Battle_recordDTO battle_recordDTO);
	List<Battle_recordDTO> selectAllParticipantCrew(Battle_recordDTO battle_recordDTO);
	List<Battle_recordDTO> selectAllParticipantBattle(Battle_recordDTO battle_recordDTO);
	List<Battle_recordDTO> selectAllWinnerParticipantGym(Battle_recordDTO battle_recordDTO);
	Battle_recordDTO selectOneBattle(Battle_recordDTO battle_recordDTO);
	Battle_recordDTO selectOneBattleRecord(Battle_recordDTO battle_recordDTO);
	Battle_recordDTO selectOneCountCrew(Battle_recordDTO battle_recordDTO);
	boolean insert(Battle_recordDTO battle_recordDTO);
	boolean update(Battle_recordDTO battle_recordDTO);
	boolean updateMvp(Battle_recordDTO battle_recordDTO);
	boolean delete(Battle_recordDTO battle_recordDTO);
}
