package com.coma.app.biz.battle_record;

import java.util.ArrayList;

public interface Battle_recordService {
	
	ArrayList<Battle_recordDTO> selectAll(Battle_recordDTO battle_recordDTO);
	Battle_recordDTO selectOne(Battle_recordDTO battle_recordDTO);
	boolean update(Battle_recordDTO battle_recordDTO);
	boolean insert(Battle_recordDTO battle_recordDTO);
	boolean delete(Battle_recordDTO battle_recordDTO);
}
