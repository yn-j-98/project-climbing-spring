package com.coma.app.biz.common;

import java.util.ArrayList;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebListener
public class SampleListener implements ServletContextListener {

	@Autowired
	private BoardDAO boardDAO; // TODO 서비스로 바꿔야함
	@Autowired
	private GymDAO gymDAO; // TODO 서비스로 바꿔야함
	@Autowired
	private Crawling2 crawling;

	@Override
	public void contextInitialized(ServletContextEvent sce)  {
		// 웹 서버 구동(실행)시 한번 수행될 코드 부분
		System.out.println("com.coma.app.biz.common.SampleListener 시작");

		ArrayList<ProductDTO> product_datas = crawling.makeSampleProduct();
		// 샘플 데이터 DB에 저장하는 코드

		// 전체 게시판 크롤링 부분
		BoardDTO boardDTO = new BoardDTO();
		BoardDTO data = boardDAO.selectOneCount(boardDTO);
		if(data.getBoard_total() <= 0) {
			System.out.println("com.coma.app.biz.common.SampleListener.makeSampleBoard 크롤링 시작");
			ArrayList<BoardDTO> crawling_board_datas = crawling.makeSampleBoard();
			for (BoardDTO board_data : crawling_board_datas) {
				boardDAO.insert(board_data);
			}
		}

		// 상품 크롤링 부분
		int PK = 0; // 자동부여하는 PK
		for (ProductDTO product_data : product_datas) {
			product_data.setProduct_num(PK);
			PK++;
		}

		// 암벽장 크롤링 부분
		GymDTO gymDTO = new GymDTO();
		GymDTO data2 = gymDAO.selectOneCount(gymDTO);
		if (data2.getGym_total() <= 0) {
			System.out.println("com.coma.app.biz.common.SampleListener.makeSampleGym 크롤링 시작");
			ArrayList<GymDTO> crawling_gym_datas = crawling.makeSampleGym();
			for (GymDTO gym_data : crawling_gym_datas) {
				gymDAO.insert(gym_data);
			}
		}

		// 세션단위로 보내려면 ServletContextEvent 사용해서 전달해야 한다
		sce.getServletContext().setAttribute("product_datas", product_datas); // FIXME TODO
		crawling.close_driver();
		System.out.println("com.coma.app.biz.common.SampleListener 끝");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)  {
		// 웹 서버 종료시 한번 수행될 코드 부분
		// ex) DB 연결 해제
	}
}