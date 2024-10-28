package com.coma.app.view.community;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;


@Slf4j
@Controller
public class CommunityController{
    @Autowired
    private BoardService boardService;


    @GetMapping(value="/community.do")
    public String community(BoardDTO boardDTO, Model model) {

        String search_Keyword = boardDTO.getSearch_keyword(); // 검색 키워드

        log.info("community.searchKeyword : ["+search_Keyword+"]");

        int pageNum = boardDTO.getPage();//요거 필요
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화

        minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화
        if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
            pageNum = 1;
        }

        log.info("community.pageNum: ["+pageNum+"]");

        // 검색 조건과 키워드가 있는지 확인


        if(search_Keyword != null) {
            // 글 검색 부분

            if(search_Keyword.equals("SEARCH_ID")) {
                // 아이디로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_PATTERN_ID"); // 아이디검색 컨디션

                System.out.println("CommunityCotroller.community.boardDTO : ["+boardDTO+"]");//로그
                List<BoardDTO> boardList = boardService.selectAllSearchPatternId(boardDTO);
//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_ID_COUNT");

                BoardDTO boardCount = boardService.selectOneSearchIdCount(boardDTO);//
                listNum = boardCount.getTotal();//게시글의 전체 개수
                log.info("community.boardCount : ["+boardCount+"]");//로그

                log.info("CommunityController.community.listNum : ["+listNum+"]");//로그

                model.addAttribute("BOARD", boardList);



            }
            else if(search_Keyword.equals("SEARCH_WRITER")) {
                // 작성자로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_NAME"); // 작성자 검색 컨디션
//                boardDTO.setModel_board_searchKeyword(keyword); // 검색 키워드 설정
                List<BoardDTO> boardList = this.boardService.selectAllSearchName(boardDTO);
                log.info("community.boardDTO : ["+boardDTO+"]");//로그


//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_NAME_COUNT");
//                BoardDTO.setModel_board_searchKeyword(keyword);
                BoardDTO boardCount = this.boardService.selectOneSearchNameCount(boardDTO);
                log.info("community.boardCount : ["+boardCount+"]");//로그


                listNum = boardCount.getTotal();
                log.info("community.listNum : ["+listNum+"]");//로그

                model.addAttribute("BOARD", boardList);

            }
            else if(search_Keyword.equals("SEARCH_TITLE")) {
                // 글 제목으로 검색했을 때
//                boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE"); // 제목 검색 컨디션

                log.info("community.boardDTO : ["+boardDTO+"]");//로그
                List<BoardDTO> boardList = this.boardService.selectAllSearchTitle(boardDTO);


//                boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");

                BoardDTO boardCount = this.boardService.selectOneSearchTitleCount(boardDTO);
                log.info("community.boardCount : ["+boardCount+"]");//로그

                listNum = boardCount.getTotal();
                log.info("community,listNum"+listNum);//로그
                model.addAttribute("BOARD", boardList);


            }
        }
        //검색어가 없다면 BOARD_ALL
        else {
            // 검색 조건이 없는 경우 전체 검색
//            boardDTO.setBoard_condition("BOARD_ALL"); // 전체 게시글 조회 컨디션
            List<BoardDTO>  boardList=this.boardService.selectAll(boardDTO);
//            boardDTO.setBoard_condition("BOARD_ONE_COUNT");
            BoardDTO boardCount = this.boardService.selectOneCount(boardDTO);
            listNum = boardCount.getTotal();
            log.info("community.listNum : ["+listNum+"]");
            model.addAttribute("BOARD", boardList);
        }
        boardDTO.setBoard_min_num(minBoard);


//        model.addAttribute("BOARD", boardList);
        model.addAttribute("total", listNum);
        model.addAttribute("Page", pageNum);



        return "views/communityRegions"; // 설정된 페이지로 이동
    }

    @GetMapping("/local.do")
    public String local(Model model, BoardDTO boardDTO) {

        String Location = boardDTO.getBoard_location(); // 지역검색


        log.info("local.Location : ["+Location+"]");

        // 지역명 맵핑
        String location = Location(Location);

        int pageNum = boardDTO.getPage();//요거 필요
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화
        if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
            pageNum = 1;
        }

        minBoard = ((pageNum - 1) * boardSize); // 최소 게시글 번호 계산
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

        log.info("local.minBoard : [" + minBoard+"]");

        boardDTO.setBoard_min_num(minBoard);


//        boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE");
        log.info("local.boardDTO : [" + boardDTO+"]");
        List<BoardDTO> datas = this.boardService.selectAllSearchTitle(boardDTO);
        log.info("local.datas : [" + datas+"]");

//        boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");
        BoardDTO boardCount = this.boardService.selectOneSearchTitleCount(boardDTO);
        log.info("local.boardCount : [" + boardCount+"]");

        listNum = boardCount.getTotal();


        model.addAttribute("Page", pageNum);
        model.addAttribute("total", listNum);
        model.addAttribute("BOARD",datas);


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
