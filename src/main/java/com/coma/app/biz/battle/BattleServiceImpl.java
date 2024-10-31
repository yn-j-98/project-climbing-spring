package com.coma.app.biz.battle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("battleService")
public class BattleServiceImpl implements BattleService{

	@Autowired
	BattleDAO battleDAO;

	@Override
	public List<BattleDTO> selectAllActive(BattleDTO battleDTO) {return this.battleDAO.selectAllActive(battleDTO);}

	@Override
	public List<BattleDTO> selectAllGymBattle(BattleDTO battleDTO) {return this.battleDAO.selectAllGymBattle(battleDTO);}

	@Override
	public List<BattleDTO> selectAllBattleAllTop4(BattleDTO battleDTO) {return this.battleDAO.selectAllBattleAllTop4(battleDTO);}

	@Override
	public List<BattleDTO> selectAllSearchBattleNum(BattleDTO battleDTO) {return this.battleDAO.selectAllSearchBattleNum(battleDTO);}

	@Override
	public List<BattleDTO> selectAllSearchBattleName(BattleDTO battleDTO) {return this.battleDAO.selectAllSearchBattleName(battleDTO);}

	@Override
	public List<BattleDTO> selectAllBattle(BattleDTO battleDTO) {return this.battleDAO.selectAllBattle(battleDTO);}

	@Override
	public List<BattleDTO> selectAllWinBattle(BattleDTO battleDTO) {return this.battleDAO.selectAllWinBattle(battleDTO);}

	@Override
	public List<BattleDTO> selectAllCrewMemberName(BattleDTO battleDTO) {return this.battleDAO.selectAllCrewMemberName(battleDTO);}

	@Override
	public BattleDTO selectOneSearchMemberBattle(BattleDTO battleDTO) {return this.battleDAO.selectOneSearchMemberBattle(battleDTO);}

	@Override
	public BattleDTO selectOneSearchBattle(BattleDTO battleDTO) {return this.battleDAO.selectOneSearchBattle(battleDTO);}

	@Override
	public BattleDTO selectOneCountActive(BattleDTO battleDTO) {return this.battleDAO.selectOneCountActive(battleDTO);}

	@Override
	public BattleDTO selectOneCountActiveBattle(BattleDTO battleDTO) {return this.battleDAO.selectOneCountActiveBattle(battleDTO);}

	@Override
	public boolean insert(BattleDTO battleDTO) {
		return this.battleDAO.insert(battleDTO);
	}

	@Override
	public boolean InsertBattleModal(BattleDTO battleDTO) {return this.battleDAO.InsertBattleModal(battleDTO);}

	@Override
	public boolean update(BattleDTO battleDTO) {return this.battleDAO.update(battleDTO);}

	@Override
	public boolean delete(BattleDTO battleDTO) {
		return this.battleDAO.delete(battleDTO);
	}

	@Override
	public BattleDTO selectOneSearchCountActive(BattleDTO battleDTO) {return this.battleDAO.selectOneSearchCountActive(battleDTO);}
}
