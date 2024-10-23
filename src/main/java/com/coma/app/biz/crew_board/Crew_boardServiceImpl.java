package com.coma.app.biz.crew_board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("crew_boardService")
public class Crew_boardServiceImpl implements Crew_boardService{

	@Autowired
	Crew_boardDAO crew_boardDAO;


	@Override
	public List<Crew_boardDTO> selectAllCrewBoard(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.selectAllCrewBoard(crew_boardDTO);
	}

	@Override
	public Crew_boardDTO selectOne(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.selectOne(crew_boardDTO);
	}

	@Override
	public Crew_boardDTO selectOneCount(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.selectOneCount(crew_boardDTO);
	}

	@Override
	public boolean insert(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.insert(crew_boardDTO);
	}

	@Override
	public boolean update(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.update(crew_boardDTO);
	}

	@Override
	public boolean delete(Crew_boardDTO crew_boardDTO) {
		return this.crew_boardDAO.delete(crew_boardDTO);
	}
}
