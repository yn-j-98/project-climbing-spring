package com.coma.app.biz.battle_record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("battle_recordService")
public class Battle_recordServiceImpl implements Battle_recordService{

	@Autowired
	Battle_recordDAO battle_recordDAO;

	@Override
	public List<Battle_recordDTO> selectAllWinner(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectAllWinner(battle_recordDTO);}

	@Override
	public List<Battle_recordDTO> selectAllParticipantCrew(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectAllParticipantCrew(battle_recordDTO);}

	@Override
	public List<Battle_recordDTO> selectAllParticipantBattle(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectAllParticipantBattle(battle_recordDTO);}

	@Override
	public List<Battle_recordDTO> selectAllWinnerParticipantGym(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectAllWinnerParticipantGym(battle_recordDTO);}

	@Override
	public Battle_recordDTO selectOneBattle(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectOneBattle(battle_recordDTO);}

	@Override
	public Battle_recordDTO selectOneBattleRecord(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectOneBattleRecord(battle_recordDTO);}

	@Override
	public Battle_recordDTO selectOneCountCrew(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.selectOneCountCrew(battle_recordDTO);}

	@Override
	public boolean insert(Battle_recordDTO battle_recordDTO) {return this.battle_recordDAO.insert(battle_recordDTO);}

	@Override
	public boolean update(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.update(battle_recordDTO);
	}

	@Override
	public boolean delete(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.delete(battle_recordDTO);
	}
}
