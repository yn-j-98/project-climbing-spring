package com.coma.app.biz.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.product.ProductDTO;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SampleListener implements ServletContextListener {
	// 전체 글 개수 TOTAL
	private final String ONE_COUNT = "SELECT COUNT(*) AS BOARD_TOTAL FROM BOARD";
	// 게시글 작성 BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID
	private final String INSERT ="INSERT INTO BOARD (BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID) \r\n"
			+ "VALUES (?,?,?,?)";
	
	// 전체 암벽장 개수 GYM_TOTAL
	private final String GYM_ONE_COUNT = "SELECT COUNT(*) AS GYM_TOTAL FROM GYM";

	//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
	private final String GYM_INSERT = "INSERT INTO GYM(GYM_NAME,GYM_PROFILE,GYM_DESCRIPTION,GYM_LOCATION)\r\n"
			+ "VALUES (?,?,?,?)";
	
	public void contextInitialized(ServletContextEvent sce)  { 
		// 웹 서버 구동(실행)시 한번 수행될 코드 부분
		System.out.println("model.SampleListener 시작");
//		Crawling crawling = new Crawling(); //크롤링 객체 선언 FIXME 크롤링 2가지 버전이있습니다.
		Crawling2 crawling = new Crawling2(); //크롤링 객체 선언 FIXME 크롤링 2가지 버전이있습니다.
		ArrayList<ProductDTO> product_datas=crawling.makeSampleProduct();//FIXME TODO
		// 샘플 데이터 DB에 저장하는 코드
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 전체 게시판 크롤링 부분
			// 게시판 글 개수 조회 BOARD_TOTAL
			pstmt = conn.prepareStatement(ONE_COUNT);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt("BOARD_TOTAL")<= 0){//글 개수가 0개 이하라면 실행
					System.out.println("model.SampleListener.makeSampleBoard 크롤링 시작");
					ArrayList<BoardDTO> crawling_board_datas=crawling.makeSampleBoard();
					for(BoardDTO board_data : crawling_board_datas) {
						System.out.println("for(BoardDTO board_data : crawling_board_datas) 로그 : "+board_data);
						// 게시글 작성 BOARD_NUM,BOARD_TITLE,BOARD_CONTENT,BOARD_LOCATION,BOARD_WRITER_ID
						try {
							pstmt = conn.prepareStatement(INSERT);
							System.out.println(board_data.getBoard_title().replace("'", "\'"));
							pstmt.setString(1,board_data.getBoard_title().replace("'", "\'"));
							System.out.println(board_data.getBoard_content().replace("'", "\'"));
							pstmt.setString(2,board_data.getBoard_content().replace("'", "\'"));
							pstmt.setString(3,"서울특별시");
							System.out.println(board_data.getBoard_writer_id());
							pstmt.setString(4, board_data.getBoard_writer_id());
							int result = pstmt.executeUpdate();
							if(result<=0) {
								System.err.println("board.BoardDAO.makeSampleBoard 데이터 없음");
							}
						}catch(SQLException e) {
							System.err.println("board.BoardDAO.makeSampleBoard.INSERT SQL문 실패");
							e.printStackTrace();
						}
					}
					System.out.println("model.SampleListener.makeSampleBoard 크롤링 완료");
				}
				else {
					System.out.println("model.SampleListener.makeSampleBoard 크롤링 필요없음");
				}
			}

			// 상품 크롤링 부분
			System.out.println("model.SampleListener.makeSampleProduct 크롤링 시작");
			int PK=0;//자동부여하는 PK 
			for(ProductDTO product_data : product_datas) {//FIXME TODO
				product_data.setProduct_num(PK);
				PK++;
			}
			System.out.println("model.SampleListener.makeSampleProduct 크롤링 완료");
			// 암벽장 크롤링 부분
			// 전체 암벽장 개수 GYM_TOTAL
			
			pstmt = conn.prepareStatement(GYM_ONE_COUNT);
			System.out.println("conn.prepareStatement(GYM_ONE_COUNT) 실행 완료");
			ResultSet gym_rs = pstmt.executeQuery();
			System.out.println("pstmt.executeQuery() 실행 완료");
			if(gym_rs.next()) {
				System.out.println("if(gym_rs.next()) 진입");
				System.out.println("암벽장 개수 : "+gym_rs.getInt("GYM_TOTAL"));
				if(gym_rs.getInt("GYM_TOTAL") <= 0){//암벽장 개수가 0개 이하라면 실행
					System.out.println("if(gym_rs.getInt(\"GYM_TOTAL\")<= 0) 진입");
					System.out.println("model.SampleListener.makeSampleGym 크롤링 시작");
					ArrayList<GymDTO> crawling_gym_datas = crawling.makeSampleGym();
					for(GymDTO gym_data : crawling_gym_datas) {
						//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
						try {
							pstmt = conn.prepareStatement(GYM_INSERT);
							pstmt.setString(1,gym_data.getGym_name());
							pstmt.setString(2,gym_data.getGym_profile());
							pstmt.setString(3,gym_data.getGym_description());
							pstmt.setString(4, gym_data.getGym_location());
							int result = pstmt.executeUpdate();
							if(result<=0) {
								System.err.println("model.SampleListener.makeSampleGym 데이터 없음");
							}
						}catch(SQLException e) {
							System.err.println("model.SampleListener.makeSampleGym.INSERT SQL문 실패");
						}
					}
					System.out.println("model.SampleListener.makeSampleGym 크롤링 완료");
				}
				else {
					System.out.println("model.SampleListener.makeSampleGym 크롤링 필요없음");
				}
			}
		}catch(SQLException e) {
			System.err.println("model.SampleListener SQL문 실패");
			e.printStackTrace();
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
			// 서버단위로 저장되는 application에 담아서 전송
			sce.getServletContext().setAttribute("product_datas", product_datas);//FIXME TODO
			crawling.close_driver();
		}
		System.out.println("model.SampleListener 끝");
	}

	public void contextDestroyed(ServletContextEvent sce)  { 
		// 웹 서버 종료시 한번 수행될 코드 부분

		// ex) DB 연결 해제
	}

}
