package com.coma.app.biz.crew_board;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("crew_boardService")
public class Crew_boardService {

	@Autowired
	Crew_boardDAO crew_boardDAO;

	@Autowired
	HttpSession session;


	public List<Crew_boardDTO> selectAll(Crew_boardDTO crew_boardDTO) {

		return crew_boardDAO.selectAll(crew_boardDTO);
	}

	public void insert(Crew_boardDTO crew_boardDTO) {
		crew_boardDAO.insert(crew_boardDTO);
	}
}
