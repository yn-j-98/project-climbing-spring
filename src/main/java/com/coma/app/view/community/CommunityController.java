package com.coma.app.view.community;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CommunityController{
    @Autowired
    private BoardService boardService;


    @RequestMapping(value="/community.do", method=RequestMethod.GET)
    public String community(HttpServletRequest request, HttpServletResponse response, BoardDTO boardDTO, Model model) {
//        ActionForward forward = new ActionForward();
//        String path = "communityRegions.jsp"; // 전체 글 페이지로 이동
//        boolean flagRedirect = false; // 포워드 방식 사용 여부 설정 (false = forward 방식)

        String search_Keyword = boardDTO.getBoard_searchKeyword(); // 검색 키워드

//        System.out.println("(CommunityPageAction.java 로그) 검색 키워드 : "+condition);

//        String keyword = request.getParameter("VIEW_BOARD_KEYWORD"); // 검색 내용
//        System.out.println("(CommunityPageAction.java 로그) 검색 내용 : "+keyword);
//
//        int pageNum = 1; // 페이지 번호 초기화
        // 페이지네이션 부분
//        if(request.getParameter("page") != null) {
//            pageNum = Integer.parseInt(request.getParameter("page")); // 페이지 번호가 있을 경우 변환하여 저장
//        }
        int pageNum = boardDTO.getPage();//요거 필요
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화

        minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화


        System.out.println("(CommunityPageAction.java 로그) 현재 페이지 번호 : "+pageNum);
//        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
//        int minBoard = 1; // 최소 게시글 수 초기화
//        int maxBoard = 1; // 최대 게시글 수 초기화
//
//        // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
//        if(pageNum <= 1) {
//            // 페이지 번호가 1 이하일 경우
//            minBoard = 1; // 최소 게시글 번호를 1로 설정
//            maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
//        }
//        else {
//            // 페이지 번호가 2 이상일 경우
//            minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
//            maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
//        }
//
//        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화
//        BoardDTO boardDTO = new BoardDTO(); // 게시글 DTO 객체 생성
//        BoardDAO boardDAO = new BoardDAO(); // 게시글 DAO 객체 생성

        // 검색 조건과 키워드가 있는지 확인


        if(search_Keyword != null) {
            // 글 검색 부분

            if(search_Keyword.equals("SEARCH_ID")) {
                // 아이디로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_PATTERN_ID"); // 아이디검색 컨디션

//                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그
                List<BoardDTO> boardList = boardService.selectAllSearchPatternId(boardDTO);
//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_ID_COUNT");
//                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardCount);//로그

                BoardDTO boardCount = boardService.selectOneSearchIdCount(boardDTO);//
                listNum = boardCount.getBoard_total();//게시글의 전체 개수

                System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (아이디 검색) : "+listNum);//로그

                model.addAttribute("BOARD", boardList);



            }
            else if(search_Keyword.equals("SEARCH_WRITER")) {
                // 작성자로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_NAME"); // 작성자 검색 컨디션
//                boardDTO.setModel_board_searchKeyword(keyword); // 검색 키워드 설정
                List<BoardDTO> boardList = boardService.selectAllSearchName(boardDTO);
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그

//                BoardDTO boardCount = new BoardDTO();
//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_NAME_COUNT");
//                BoardDTO.setModel_board_searchKeyword(keyword);
                BoardDTO boardCount = boardService.selectOneSearchNameCount(boardDTO);
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardCount);//로그


                listNum = boardCount.getBoard_total();
                System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (작성자 검색)"+listNum);//로그

                model.addAttribute("BOARD", boardList);

            }
            else if(search_Keyword.equals("SEARCH_TITLE")) {
                // 글 제목으로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE"); // 제목 검색 컨디션
//                boardDTO.setModel_board_searchKeyword(keyword); // 검색 키워드 설정
//                boardDTO.setModel_board_location("");
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그
                List<BoardDTO> boardList = boardService.selectAllSearchTitle(boardDTO);

//                BoardDTO boardCount = new BoardDTO();
//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");
//                boardCount.setModel_board_searchKeyword(keyword);
//                boardCount.setModel_board_location("");
//                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardCount);//로그

                BoardDTO boardCount = boardService.selectOneSearchTitleCount(boardDTO);
                listNum = boardCount.getBoard_total();
                System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (제목 검색)"+listNum);//로그
                model.addAttribute("BOARD", boardList);
//                model.addAttribute("BOARD", boardList);


            }
        }
        //검색어가 없다면 BOARD_ALL
        else {
            // 검색 조건이 없는 경우 전체 검색
//            boardDTO.setBoard_condition("BOARD_ALL"); // 전체 게시글 조회 컨디션
            List<BoardDTO>  boardList=boardService.selectAll(boardDTO);
//            BoardDTO boardCount = new BoardDTO();
//            boardDTO.setBoard_condition("BOARD_ONE_COUNT");
            BoardDTO boardCount = boardService.selectOneCount(boardDTO);
            listNum = boardCount.getBoard_total();
            System.out.println("전체 페이지 개수 (전체 검색)"+listNum);
            model.addAttribute("BOARD", boardList);
        }
        boardDTO.setBoard_min_num(minBoard);


//        model.addAttribute("BOARD", boardList);
        model.addAttribute("totalCount", listNum);
        model.addAttribute("currentPage", pageNum);



        return "views/communityRegions"; // 설정된 페이지로 이동
    }

    @RequestMapping(value="/local.do", method=RequestMethod.GET)
    public String local(Model model, BoardDTO boardDTO) {
//		ActionForward forward = new ActionForward();
//		String path = "localCommunity.jsp"; // 지역글 검색 페이지로 이동
//		boolean flagRedirect = false; // 포워드 방식 사용 여부 설정 (false = forward 방식)

        // 뷰에서 전달받는 지역 값 (SEOUL, GYEONGGI, INCHEON, CHUNGNAM)
//        String Location = request.getParameter("VIEW_BOARD_LIST");
        String Location = boardDTO.getBoard_location(); // 검색 키워드


        System.err.println("(LocationPageAction.java 로그) View에서 보내준 지역 : "+Location);

        // 검색 키워드 (제목 검색)
//        String keyword = request.getParameter("VIEW_BOARD_KEYWORD");
//        if(keyword == null) {
//        	keyword = "";
//        }
//        System.err.println("(LocationPageAction.java 로그) 검색어 : "+keyword);

        // 지역명 맵핑
        String location = Location(Location);
//        System.err.println("(LocationPageAction.java 로그) 검색 지역 : "+location);

//        int pageNum = 1; // 페이지 번호 초기화
//        if (request.getParameter("page") != null) {
//            pageNum = Integer.parseInt(request.getParameter("page")); // 페이지 번호 가져오기
//        }
//
//        System.err.println("(LocationPageAction.java 로그) 현재 페이지 : " + pageNum);

//        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
//        int minBoard = 1; // 최소 게시글 수 초기화
//        int maxBoard = 1; // 최대 게시글 수 초기화
//
//        // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
//        if(pageNum <= 1) {
//            // 페이지 번호가 1 이하일 경우
//            minBoard = 1; // 최소 게시글 번호를 1로 설정
//            maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
//        }
//        else {
//            // 페이지 번호가 2 이상일 경우
//            minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
//            maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
//        }

        int pageNum = boardDTO.getPage();//요거 필요
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화

        minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

        System.err.println("(LocationPageAction.java 로그) 시작 글 번호 : " + minBoard);
//        System.err.println("(LocationPageAction.java 로그) 끝 글 번호 : " + maxBoard);

//        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화
//        BoardDTO boardDTO = new BoardDTO(); // 게시글 DTO 객체 생성
//        BoardDAO boardDAO = new BoardDAO(); // 게시글 DAO 객체 생성

//        boardDTO.setModel_board_location(location);
//        boardDTO.setModel_board_searchKeyword(keyword);
        boardDTO.setBoard_min_num(minBoard);
//        boardDTO.setModel_board_max_num(maxBoard);

//        boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE");
        System.err.println("(LocationPageAction.java 로그) Model로 넘어가는 BoardDTO : " + boardDTO);
        List<BoardDTO> datas = boardService.selectAllSearchTitle(boardDTO);
        System.err.println("(LocationPageAction.java 로그) Model에서 넘어온 Location_Board_datas : " + datas);

//        BoardDTO boardCount = new BoardDTO();
//        boardCount.setModel_board_location(location);
//        boardCount.setModel_board_searchKeyword(keyword);
//        boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");
//        System.err.println("(LocationPageAction.java 로그) Model에서 넘어가는 boardCount : " + boardCount);
        BoardDTO boardCount = boardService.selectOneSearchTitleCount(boardDTO);
        listNum = boardCount.getBoard_total();


//        request.setAttribute("currentPage", pageNum);
//        request.setAttribute("totalCount", listNum);
//        request.setAttribute("BOARD",datas);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalCount", listNum);
        model.addAttribute("BOARD",datas);

//        forward.setPath(path);
//        forward.setRedirect(flagRedirect);
        return "views/communityRegions";
    }
    /* 뷰에서 전달받은 지역 값을 실제 지역명으로 변환하는 함수
     */
    public String Location(String view_Location) {
        Map<String, String> locationMap = new HashMap<String, String>();

        locationMap.put("SEOUL", "서울특별시");
        locationMap.put("GYEONGGI", "경기도");
        locationMap.put("INCHEON", "인천광역시");
        locationMap.put("SEJONG", "세종특별자치도");
        locationMap.put("BUSAN", "부산광역시");
        locationMap.put("DAEGU", "대구광역시");
        locationMap.put("DAEJEON", "대전광역시");
        locationMap.put("GWANGJU", "광주광역시");
        locationMap.put("ULSAN", "울산광역시");
        locationMap.put("CHUNGCHEONGNAMDO", "충청남도");
        locationMap.put("CHUNGCHEONGBUKDO", "충청북도");
        locationMap.put("JEONLANAMDO", "전라남도");
        locationMap.put("JEONLABUKDO", "전라북도");
        locationMap.put("GYEONGSANGNAMDO", "경상남도");
        locationMap.put("GYEONGSANGBUKDO", "경상북도");
        locationMap.put("GANGWONDO", "강원도");
        locationMap.put("CHUNGNAM", "충청남도");

        return locationMap.getOrDefault(view_Location, "서울특별시"); // 기본값은 서울특별시
        //getOrDefault  Map 인터페이스에서 제공하는 메서드로,
        //특정 키에 해당하는 값을 반환하되,
        //만약 그 키가 존재하지 않으면 기본값을 반환하는 역할을 합니다.
    }






}
