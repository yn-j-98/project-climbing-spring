package com.coma.app.biz.favorite;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class FavoriteDAO3 {
    @Autowired
    private SqlSessionTemplate mybatis;

    public List<FavoriteDTO> selectAll(FavoriteDTO favoriteDTO) {
       return null;
    }
    public FavoriteDTO selectOne(FavoriteDTO favoriteDTO) {
        System.out.println("favoriteDTO = " + favoriteDTO);
        return mybatis.selectOne("FavoriteDAO.selectOne", favoriteDTO);
    }
    public boolean insert(FavoriteDTO favoriteDTO) {
        int result = mybatis.insert("FavoriteDAO.insert", favoriteDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }
    public boolean update(FavoriteDTO favoriteDTO) {
        return false;
    }
    public boolean delete(FavoriteDTO favoriteDTO) {
        int result = mybatis.delete("FavoriteDAO.delete", favoriteDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }
}
