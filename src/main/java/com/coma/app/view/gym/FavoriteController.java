package com.coma.app.view.gym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coma.app.biz.favorite.FavoriteDTO;
import com.coma.app.biz.favorite.FavoriteService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.HttpSession;

@Slf4j
@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private HttpSession session;

	@LoginCheck
	@PostMapping("/gymFavorite.do")
	public @ResponseBody String gymFavorite(FavoriteDTO favoriteDTO) {

		String member_id = (String) session.getAttribute("MEMBER_ID");

		log.info("com.coma.app.view.gym.gymFavorite 비동기 로그");
		log.info("좋아요 암벽장 : [{}]",favoriteDTO.getFavorite_gym_num());
		log.info("로그인한 사용자 : [{}]",member_id);
		//------------------------------------------------------------
		//좋아요 여부를 확인하는 로직 시작
		favoriteDTO.setFavorite_member_id(member_id);

		FavoriteDTO data_favorite = this.favoriteService.selectOne(favoriteDTO);
		log.info("data_favorite = [{}]",data_favorite);

		if(data_favorite == null) {
			//null 이면 Favorite insert 를 진행 해줍니다.

			//True : out 암벽장이 찜 목록에 저장된 것 으로 Insert 로 값을 전달합니다.
			if(this.favoriteService.insert(favoriteDTO)) {
				log.info("insert");
				return "insert";
			}
		}
		else if(data_favorite != null){
			//not null 이면 Favorite delete 를 진행 해줍니다.

			//True : out 암벽장이 찜 목록에 제거된 것 으로 Delete 로 값을 전달합니다.
			if(this.favoriteService.delete(favoriteDTO)) {
				log.info("delete");
				return "delete";
			}
		}
		//False : out False 는 서버이상
		return "false";
		//좋아요 여부를 확인하는 로직 종료
		//------------------------------------------------------------
	}
}


