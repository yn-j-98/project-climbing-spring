package com.coma.app.view.asycnServlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.coma.app.biz.favorite.FavoriteDAO;
import com.coma.app.biz.favorite.FavoriteDTO;
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GymFavorite")
public class GymFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GymFavorite() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String login[] = LoginCheck.Success(request, response);
		String member_id = login[0];
//		int crewcheck = Integer.parseInt(login[1]);
		int view_favorite_gym_num = Integer.parseInt(request.getParameter("view_favorite_gym_num"));
		System.out.println("좋아요 암벽장 : "+view_favorite_gym_num);
		System.out.println("로그인한 사용자 : "+member_id);		
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		FavoriteDTO favoriteDTO = new FavoriteDTO();
		
		//------------------------------------------------------------
		//좋아요 여부를 확인하는 로직 시작
		//TODO (암벽장 번호 / 사용자 아이디) Favorite DTO에 값을 추가해줍니다.
		favoriteDTO.setFavorite_gym_num(view_favorite_gym_num);
		favoriteDTO.setFavorite_member_id(member_id);
		
		//TODO Favorite selectOne 으로 여부를 확인해줍니다.
		FavoriteDTO data_favorite = favoriteDAO.selectOne(favoriteDTO);
		
		boolean flag = false;
		if(data_favorite == null) {
			//null 이면 Favorite insert 를 진행 해줍니다.
			flag = favoriteDAO.insert(favoriteDTO);
			//True : out 암벽장이 찜 목록에 저장된 것 으로 Insert 로 값을 전달합니다.
			if(flag) {
				out.print("insert");
				return;
			}
		}
		else if(data_favorite != null){
			//not null 이면 Favorite delete 를 진행 해줍니다.
			flag = favoriteDAO.delete(favoriteDTO);
			//True : out 암벽장이 찜 목록에 제거된 것 으로 Delete 로 값을 전달합니다.
			if(flag) {
				out.print("delete");
				return;
			}
		}
		//False : out False 는 서버이상
		System.out.println("찜 목록 오류 " + data_favorite + " : " +flag);
		out.print(flag);
		//좋아요 여부를 확인하는 로직 종료
		//------------------------------------------------------------
	}

}
