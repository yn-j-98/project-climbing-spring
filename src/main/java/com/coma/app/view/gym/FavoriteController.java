package com.coma.app.view.gym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coma.app.biz.favorite.FavoriteDTO;
import com.coma.app.biz.favorite.FavoriteService;
import com.coma.app.view.annotation.LoginCheck;

import jakarta.servlet.http.HttpSession;

@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private HttpSession session;

	@LoginCheck
	@PostMapping("/gymFavorite.do")
	public @ResponseBody String gymFavorite(FavoriteDTO favoriteDTO, Model model) {

		String member_id = (String) session.getAttribute("MEMBER_ID");


		System.out.println("com.coma.app.view.gym.gymFavorite 비동기 로그");
		int favorite_gym_num = favoriteDTO.getFavorite_gym_num();
		System.out.println("좋아요 암벽장 : "+favorite_gym_num);
		System.out.println("로그인한 사용자 : "+member_id);		
		//------------------------------------------------------------
		//좋아요 여부를 확인하는 로직 시작
		//TODO (암벽장 번호 / 사용자 아이디) Favorite DTO에 값을 추가해줍니다.
		favoriteDTO.setFavorite_condition("ONE");
		favoriteDTO.setFavorite_gym_num(favorite_gym_num);
		favoriteDTO.setFavorite_member_id(member_id);

		//TODO Favorite selectOne 으로 여부를 확인해줍니다.
		FavoriteDTO data_favorite = this.favoriteService.selectOne(favoriteDTO);
		boolean flag = false;
		if(data_favorite == null) {
			//null 이면 Favorite insert 를 진행 해줍니다.
			flag = this.favoriteService.insert(favoriteDTO);
			//True : out 암벽장이 찜 목록에 저장된 것 으로 Insert 로 값을 전달합니다.
			if(flag) {
				System.out.println("insert");
				return "true";
			}
		}
		else if(data_favorite != null){
			//not null 이면 Favorite delete 를 진행 해줍니다.
			flag = this.favoriteService.delete(favoriteDTO);
			//True : out 암벽장이 찜 목록에 제거된 것 으로 Delete 로 값을 전달합니다.
			if(flag) {
				System.out.println("delete");
				return "true";
			}
		}
		//False : out False 는 서버이상
		System.out.println("찜 목록 오류 " + data_favorite + " : " +flag);
		return "false";
		//좋아요 여부를 확인하는 로직 종료
		//------------------------------------------------------------
	}
}


