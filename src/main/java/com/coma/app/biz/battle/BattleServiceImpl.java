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
	public BattleDTO selectOneSearchMemeberBattle(BattleDTO battleDTO) {return this.battleDAO.selectOneSearchMemberBattle(battleDTO);}

	@Override
	public BattleDTO selectOneSearchBattle(BattleDTO battleDTO) {return this.battleDAO.selectOneSearchBattle(battleDTO);}

	@Override
	public BattleDTO selectOneCountActive(BattleDTO battleDTO) {return this.battleDAO.selectOneCountActive(battleDTO);}

	@Override
	public boolean insert(BattleDTO battleDTO) {
		return this.battleDAO.insert(battleDTO);
	}

	@Override
	public boolean update(BattleDTO battleDTO) {
		return this.battleDAO.update(battleDTO);
	}

	@Override
	public boolean delete(BattleDTO battleDTO) {
		return this.battleDAO.delete(battleDTO);
	}

}
