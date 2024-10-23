package com.coma.app.biz.crew_board;

import java.util.List;

public interface Crew_boardService {
	List<Crew_boardDTO> selectAllCrewBoard(Crew_boardDTO crew_boardDTO);
	Crew_boardDTO selectOne(Crew_boardDTO crew_boardDTO);
	Crew_boardDTO selectOneCount(Crew_boardDTO crew_boardDTO);
	boolean insert(Crew_boardDTO crew_boardDTO);
	boolean update(Crew_boardDTO crew_boardDTO);
	boolean delete(Crew_boardDTO crew_boardDTO);
}
