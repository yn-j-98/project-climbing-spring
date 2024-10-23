package com.coma.app.biz.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService{

	@Autowired
	FavoriteDAO favoriteDAO;
	
	@Override
	public List<FavoriteDTO> selectAll(FavoriteDTO favoriteDTO) {
		return this.favoriteDAO.selectAll(favoriteDTO);
	}

	@Override
	public FavoriteDTO selectOne(FavoriteDTO favoriteDTO) {
		return this.favoriteDAO.selectOne(favoriteDTO);
	}

	@Override
	public boolean insert(FavoriteDTO favoriteDTO) {
		return this.favoriteDAO.insert(favoriteDTO);
	}

	@Override
	public boolean update(FavoriteDTO favoriteDTO) {
		return this.favoriteDAO.update(favoriteDTO);
	}

	@Override
	public boolean delete(FavoriteDTO favoriteDTO) {
		return this.favoriteDAO.delete(favoriteDTO);
	}
}
