//package com.coma.app.view.asycnServlet;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.coma.app.biz.crew_board.Crew_boardDAO;
//import com.coma.app.biz.crew_board.Crew_boardDTO;
//
//import com.coma.app.biz.crew_board.Crew_boardService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@WebServlet("/crewBoardInsert")
//public class CrewBoardInsertAction extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    @Autowired
//    private Crew_boardService crewBoardService;
//
//    public CrewBoardInsertAction() {
//        super();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response); // GET 요청을 POST로 처리
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 세션에서 로그인된 사용자 ID 가져오기
//        HttpSession session = request.getSession(false); // false: 세션이 없으면 새로 만들지 않음
//        String crewBoardMemberId = (session != null) ? (String) session.getAttribute("MEMBER_ID") : null;
//
//        // 사용자가 로그인하지 않은 경우 처리 (비정상적인 접근 막기)
//        if (crewBoardMemberId == null) {
//            response.sendRedirect("LOGINPAGEACTION.do");
//            return; // 로그인하지 않은 사용자는 더 이상의 처리를 하지 않음
//        }
//
//        System.out.println("CrewBoardInsertAction 비동기 처리 시작");
//        // V에서 title, content값을 받아오기
//        String crew_board_title = request.getParameter("VIEW_TITLE");
//        String crew_board_content = request.getParameter("VIEW_CONTENT");
//        System.err.println("줄바꿈 적용 전 내용 = "+crew_board_content);
//        crew_board_content = crew_board_content.replace("\n", "<br>");
//        System.out.println("줄바꿈 적용 후 내용 = "+crew_board_content);
//
//        Crew_boardDAO crew_boardDAO = new Crew_boardDAO();
//        Crew_boardDTO crew_boardDTO = new Crew_boardDTO();
//
//        // DB에 저장할 데이터 삽입
//        crew_boardDTO.setCrew_board_title(crew_board_title);
//        crew_boardDTO.setCrew_board_content(crew_board_content);
//        crew_boardDTO.setCrew_board_writer_id(crewBoardMemberId);
//
//        // 로그
//        System.out.println("제목 = " + crew_boardDTO.getCrew_board_title());
//        System.out.println("내용 = " + crew_boardDTO.getCrew_board_content());
//        System.out.println("작성자 ID = " + crew_boardDTO.getCrew_board_writer_id());
//
//        System.err.println("CrewBoardInsertAction 글 작성 시작");
//        // 글 삽입
//        boolean flag = crew_boardDAO.insert(crew_boardDTO);
//
//        System.err.println("CrewBoardInsertAction 글 작성 성공");
//
//        // 성공 응답 설정
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        if (flag) {
//        	int pageNum = 1; // 페이지 번호 초기화
//			if (request.getParameter("page") != null) {
//				// 페이지 번호가 주어지면 변환하여 저장
//				pageNum = Integer.parseInt(request.getParameter("page"));
//				System.out.println("67 (CrewBoardInsertAction) pageNum = "+pageNum);
//			}
//			// 게시글 삽입이 성공하면 최신 게시글 목록을 가져온다
//
//			int minBoard = 1; // 무조건 첫페이지로 이동
//			int maxBoard = 10; // 무조건 첫페이지로 이동
//
//			crew_boardDTO.setCrew_board_min_num(minBoard);
//			crew_boardDTO.setCrew_board_max_num(maxBoard);
//			crew_boardDTO.setCrew_board_condition("CREW_BOARD_ALL_CREW_BOARD");
//        	System.out.println("64 "+crew_boardDTO);
//        	List<Crew_boardDTO> crew_board_datas = crewBoardService.selectAllCrewBoard(crew_boardDTO);
//
//			// 프로필 이미지 URL 설정
//			for (Crew_boardDTO data : crew_board_datas) {
//				String filename = data.getCrew_board_member_profile();
//				data.setCrew_board_member_profile(request.getServletContext().getContextPath() + "/profile_img/" + filename);
//			}
//
//            // JSON 형식으로 응답 생성
//            StringBuilder responseBuilder = new StringBuilder();
//            responseBuilder.append("{");
//            responseBuilder.append("\"status\":\"success\",");
//            responseBuilder.append("\"message\":\"게시글이 작성되었습니다\",");
//            responseBuilder.append("\"crew_board_datas\":[");
//
//            // 각 게시글 정보를 JSON 형식으로 추가
//            for (int i = 0; i < crew_board_datas.size(); i++) {
//                Crew_boardDTO post = crew_board_datas.get(i);
//                responseBuilder.append("{");
//                responseBuilder.append("\"crew_board_writer_id\":\"").append(post.getCrew_board_writer_id()).append("\",");
//                responseBuilder.append("\"crew_board_title\":\"").append(post.getCrew_board_title()).append("\",");
//                responseBuilder.append("\"crew_board_content\":\"").append(
//                	    post.getCrew_board_content()
//                	        .replace("\"", "\\\"")  // 쌍따옴표 이스케이프
//                	        .replace("\n", "\\n")    // 줄바꿈 이스케이프
//                	        .replace("\r", "\\r")    // 캐리지 리턴 이스케이프
//                	        .replace("\t", "\\t")    // 탭 이스케이프
//                	        .replace("<", "&lt;")	//	HTML 태그를 이스케이프
//                	        .replace(">", "&gt;")
//                	).append("\",");
//                responseBuilder.append("\"crew_board_member_profile\":\"").append(post.getCrew_board_member_profile()).append("\""); // 프로필 URL 추가
//                responseBuilder.append("}");
//                System.out.println(i+"번째 post = "+post);
//                if (i < crew_board_datas.size() - 1) {
//                    responseBuilder.append(","); // 마지막 요소가 아닐 경우 쉼표 추가
//                }
//            }
//            responseBuilder.append("]");
//            responseBuilder.append("}");
//
//            // 로그 찍기 위해 최종 응답 문자열변환한 변수 생성
//            String json_response = responseBuilder.toString();
//
//            // 로그 출력
//            System.out.println("109 json_response = "+ json_response);
//
//            // 최종 응답 전송
//            response.getWriter().write(json_response);
//            System.out.println("CrewBoardInsertAction 응답 성공");
//            //response.getWriter().write(responseBuilder.toString());
//        } else {
//            // 삽입 실패 시 오류 메시지 전송
//        	System.err.println("123 에러");
//            response.getWriter().write("{\"status\":\"error\", \"message\":\"게시글 작성에 실패했습니다.\"}");
//        }
//    }
//}
