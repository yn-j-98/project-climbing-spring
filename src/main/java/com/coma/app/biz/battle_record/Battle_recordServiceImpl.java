package com.coma.app.biz.battle_record;//package com.coma.app.biz.battle_record;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("battle_record")
public class Battle_recordServiceImpl implements Battle_recordService {

	@Autowired
	private Battle_recordDAO battle_recordDAO;

	@Override
	public ArrayList<Battle_recordDTO> selectAll(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.selectAll(battle_recordDTO);
	}

	@Override
	public Battle_recordDTO selectOne(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.selectOne(battle_recordDTO);
	}

	@Override
	public boolean update(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.update(battle_recordDTO);
	}

	@Override
	public boolean insert(Battle_recordDTO battle_recordDTO) {
		return this.battle_recordDAO.insert(battle_recordDTO);
	}

	@Override
	public boolean delete(Battle_recordDTO battle_recordDTO) {
		return false;
	}

}
