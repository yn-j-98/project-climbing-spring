package com.coma.app.biz.favorite;

import java.util.List;

public interface FavoriteService {
	List<FavoriteDTO> selectAll(FavoriteDTO favoriteDTO);
	FavoriteDTO selectOne(FavoriteDTO favoriteDTO);
	boolean insert(FavoriteDTO favoriteDTO);
	boolean update(FavoriteDTO favoriteDTO);
	boolean delete(FavoriteDTO favoriteDTO);
}
