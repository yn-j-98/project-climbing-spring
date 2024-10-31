package com.coma.app.biz.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		return this.boardDAO.selectAll(boardDTO);
	}

	@Override
	public List<BoardDTO> selectAllSearchMatchId(BoardDTO boardDTO) {return this.boardDAO.selectAllSearchMatchId(boardDTO);}

	@Override
	public List<BoardDTO> selectAllSearchPatternId(BoardDTO boardDTO) {return this.boardDAO.selectAllSearchPatternId(boardDTO);}

	@Override
	public List<BoardDTO> selectAllSearchTitle(BoardDTO boardDTO) {return this.boardDAO.selectAllSearchTitle(boardDTO);}

	@Override
	public List<BoardDTO> selectAllSearchName(BoardDTO boardDTO) {
		return this.boardDAO.selectAllSearchName(boardDTO);
	}

	@Override
	public List<BoardDTO> selectAllRowNum(BoardDTO boardDTO) {return this.boardDAO.selectAllRowNum(boardDTO);}

	@Override
	public List<BoardDTO> selectAllRecentBoard5(BoardDTO boardDTO) {return this.boardDAO.selectAllRecentBoard5(boardDTO);}

	@Override
	public List<BoardDTO> selectAllSearchBoard(BoardDTO boardDTO) {return this.boardDAO.selectAllSearchBoard(boardDTO);}



	@Override
	public List<BoardDTO> selectAllSearchTitleAll(BoardDTO boardDTO) {return this.boardDAO.selectAllSearchTitleAll(boardDTO);
	}
	@Override
	public BoardDTO selectOne(BoardDTO boardDTO) {return this.boardDAO.selectOne(boardDTO);}

	@Override
	public BoardDTO selectOneWriterId(BoardDTO boardDTO) {
		return this.boardDAO.selectOneWriterId(boardDTO);
	}

	@Override
	public BoardDTO selectOneCount(BoardDTO boardDTO) {
		return this.boardDAO.selectOneCount(boardDTO);
	}

	@Override
	public BoardDTO selectOneSearchIdCount(BoardDTO boardDTO) {
		return this.boardDAO.selectOneSearchIdCount(boardDTO);
	}

	@Override
	public BoardDTO selectOneSearchTitleCount(BoardDTO boardDTO) {return this.boardDAO.selectOneSearchTitleCount(boardDTO);}

	@Override
	public BoardDTO selectOneSearchNameCount(BoardDTO boardDTO) {return this.boardDAO.selectOneSearchNameCount(boardDTO);}

	@Override
	public BoardDTO selectOneSearchTitleCountAll(BoardDTO boardDTO) {
		return this.boardDAO.selectOneSearchTitleCountAll(boardDTO);
	}

	@Override
	public BoardDTO selectOneBoardTotal(BoardDTO boardDTO) {return this.boardDAO.selectOneBoardTotal(boardDTO);}

	@Override
	public boolean insert(BoardDTO boardDTO) {
		return this.boardDAO.insert(boardDTO);
	}

	@Override
	public boolean updateContentTitle(BoardDTO boardDTO) {return this.boardDAO.updateContentTitle(boardDTO);}

	@Override
	public boolean updateCnt(BoardDTO boardDTO) {
		return this.boardDAO.updateCnt(boardDTO);
	}



	@Override
	public boolean delete(BoardDTO boardDTO) {
		return this.boardDAO.delete(boardDTO);
	}

	@Override
	public boolean deleteSelectedBoard(BoardDTO boardDTO) {return this.boardDAO.deleteSelectedBoard(boardDTO);}
}
