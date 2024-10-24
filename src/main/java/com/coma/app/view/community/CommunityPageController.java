package com.coma.app.view.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reply.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reply.ReplyDAO;
import com.coma.app.biz.reply.ReplyDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("communityPageController")
public class CommunityPageController{

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReplyService replyService;

    @RequestMapping("/MainCommunityPage.do")
    public String mainCommunityPage(Model model,BoardDTO boardDTO) {
        String path = "communityRegions"; // 전체 글 페이지로 이동

        // 글정보와 키워드를 요청에서 가져옴
        String condition = boardDTO.getBoard_condition(); // 검색 키워드
        System.out.println("(CommunityPageAction.java 로그) 검색 키워드 : "+condition);
        String keyword = boardDTO.getBoard_searchKeyword();// 검색 내용
        System.out.println("(CommunityPageAction.java 로그) 검색 내용 : "+keyword);
        
        int pageNum = boardDTO.getPage(); // 페이지 번호 초기화
        // 페이지네이션 부분
        System.out.println("(CommunityPageAction.java 로그) 현재 페이지 번호 : "+pageNum);
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화
        int maxBoard = 1; // 최대 게시글 수 초기화
        
        // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
        if(pageNum <= 1) {
            // 페이지 번호가 1 이하일 경우
            minBoard = 1; // 최소 게시글 번호를 1로 설정
            maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
        }
        else {
            // 페이지 번호가 2 이상일 경우
            minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
            maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
        }
        
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화
        List<BoardDTO> boardList = null;
        // 검색 조건과 키워드가 있는지 확인
        if(condition != null) {    
            // 글 검색 부분
            if(condition.equals("SEARCH_ID")) {
                // 아이디로 검색했을 때
            	boardDTO.setBoard_condition("BOARD_ONE_SEARCH_ID_COUNT");
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardDTO);//로그
                
                listNum = boardService.selectOne(boardDTO).getBoard_total();//게시글의 전체 개수
                System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (아이디 검색) : "+listNum);//로그
                
                //boardDTO.setBoard_condition("BOARD_ALL_SEARCH_PATTERN_ID"); // 아이디검색 컨디션
                // 게시글 목록 조회
                boardList = boardService.selectAllSearchPatternId(boardDTO); // 설정된 조건으로 게시글 목록 조회

                // selectOne 메소드를 호출하여 검색 조건에 맞는 게시글 수를 가져옴
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그
            }
            else if(condition.equals("SEARCH_WRITER")) {
                // 작성자로 검색했을 때
                
            	boardDTO.setBoard_condition("BOARD_ONE_SEARCH_NAME_COUNT");
                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardDTO);//로그

                listNum = boardService.selectOne(boardDTO).getBoard_total();
                System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (작성자 검색)"+listNum);//로그
                
                //boardDTO.setBoard_condition("BOARD_ALL_SEARCH_NAME"); // 작성자 검색 컨디션
                // 게시글 목록 조회
                boardList = boardService.selectAllSearchName(boardDTO); // 설정된 조건으로 게시글 목록 조회

                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그
            }
            else if(condition.equals("SEARCH_TITLE")) {
                // 글 제목으로 검색했을 때
            	boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");
            	System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardCount : "+boardDTO);//로그
            	
            	listNum = boardService.selectOne(boardDTO).getBoard_total();
            	System.out.println("(CommunityPageAction.java 로그) 전체 페이지 개수 (제목 검색)"+listNum);//로그

                //boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE"); // 제목 검색 컨디션
                // 게시글 목록 조회
                boardList = boardService.selectAllSearchTitle(boardDTO); // 설정된 조건으로 게시글 목록 조회

                System.out.println("(CommunityPageAction.java 로그) SEARCH_ID model로 전달항 boardDTO : "+boardDTO);//로그
            }
        }
        //검색어가 없다면 BOARD_ALL
        else {
            // 검색 조건이 없는 경우 전체 검색
        	boardDTO.setBoard_condition("BOARD_ONE_COUNT");
        	listNum = boardService.selectOneCount(boardDTO).getBoard_total();
        	System.out.println("전체 페이지 개수 (전체 검색)"+listNum);
            
            //boardDTO.setBoard_condition("BOARD_ALL"); // 전체 게시글 조회 컨디션
            // 게시글 목록 조회
            boardList = boardService.selectAll(boardDTO); // 설정된 조건으로 게시글 목록 조회
        }
        
        // 게시글을 페이지 단위로 잘라서 조회해야 함
        // boardDTO에 minPage와 maxPage 값을 설정하여 조회 범위를 지정해야 함
        
        boardDTO.setBoard_min_num(minBoard);
        boardDTO.setBoard_max_num(maxBoard);

        model.addAttribute("BOARD", boardList); // 조회된 게시글 목록을 요청 객체에 저장
        model.addAttribute("totalCount", listNum); // 전체 글 개수
        model.addAttribute("currentPage", pageNum); // 현재 페이지 번호

        return path; // 설정된 페이지로 이동
    }// 메인 페이지에서는 게시글 목록을 보여주게 됩니다 (BOARD)
    
    @RequestMapping("/LocationPage.do")
    public String locationPage(Model model, BoardDTO boardDTO) {
		String path = "localCommunity"; // 지역글 검색 페이지로 이동

        // 뷰에서 전달받는 지역 값 (SEOUL, GYEONGGI, INCHEON, CHUNGNAM)
        String view_Location = boardDTO.getBoard_condition();
        System.err.println("(LocationPageAction.java 로그) View에서 보내준 지역 : "+view_Location);
        
        // 검색 키워드 (제목 검색)
        String keyword = boardDTO.getBoard_searchKeyword();
        if(keyword == null) {
        	keyword = "";
        }
        System.err.println("(LocationPageAction.java 로그) 검색어 : "+keyword);

        // 지역명 맵핑
        String location = Location(view_Location);
        System.err.println("(LocationPageAction.java 로그) 검색 지역 : "+location);
        
        int pageNum = boardDTO.getPage(); // 페이지 번호 초기화
        
        System.err.println("(LocationPageAction.java 로그) 현재 페이지 : " + pageNum);
        
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 1; // 최소 게시글 수 초기화
        int maxBoard = 1; // 최대 게시글 수 초기화
        
        // 페이지 번호에 따라 최소 및 최대 게시글 수 설정
        if(pageNum <= 1) {
            // 페이지 번호가 1 이하일 경우
            minBoard = 1; // 최소 게시글 번호를 1로 설정
            maxBoard = minBoard * boardSize; // 최대 게시글 번호 계산
        }
        else {
            // 페이지 번호가 2 이상일 경우
            minBoard = ((pageNum - 1) * boardSize) + 1; // 최소 게시글 번호 계산
            maxBoard = pageNum * boardSize; // 최대 게시글 번호 계산
        }
        System.err.println("(LocationPageAction.java 로그) 시작 글 번호 : " + minBoard);
        System.err.println("(LocationPageAction.java 로그) 끝 글 번호 : " + maxBoard);
        
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

        boardDTO.setBoard_min_num(minBoard);
        boardDTO.setBoard_max_num(maxBoard);
        
        //boardDTO.setBoard_condition("BOARD_ALL_SEARCH_TITLE");
        System.err.println("(LocationPageAction.java 로그) Model로 넘어가는 BoardDTO : " + boardDTO);
        List<BoardDTO> datas = boardService.selectAllSearchTitle(boardDTO);
        System.err.println("(LocationPageAction.java 로그) Model에서 넘어온 Location_Board_datas : " + datas);
        
        boardDTO.setBoard_condition("BOARD_ONE_SEARCH_TITLE_COUNT");
        System.err.println("(LocationPageAction.java 로그) Model에서 넘어가는 boardCount : " + boardDTO);
        listNum = boardService.selectOne(boardDTO).getBoard_total();
        
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalCount", listNum);
        model.addAttribute("BOARD",datas);
        
    	return path;
	}
    
    @RequestMapping("/BOARDONEPAGEACTION.do")
	public String boardOnePage(ServletContext servletContext, Model model, BoardDTO boardDTO,  MemberDTO memberDTO, ReplyDTO replyDTO) {
		// 글 하나 선택 페이지
		String path = "post"; // 선택한 글 하나 보는 페이지

		// pk로 selectOne하기
		boardDTO.setBoard_condition("BOARD_ONE"); // 글 selectOne 컨디션
		boardDTO = boardService.selectOne(boardDTO); // pk로 글 selectOne

		System.out.println("게시글 정보 조회: " + boardDTO);

		// MemberDAO에서 프로필 정보를 가져옴
		memberDTO.setMember_id(boardDTO.getBoard_writer_id()); // 세션에 있는 사용자의 아이디
		//memberDTO.setMember_condition("MEMBER_SEARCH_ID"); // member selectOne 컨디션
		memberDTO = memberService.selectOneSearchId(memberDTO); // 프로필 사진을 보여주기 위해 member selectOne
		System.out.println("회원 정보 조회: " + memberDTO);

		String filename = memberDTO.getMember_profile();

		model.addAttribute("member_profile", servletContext.getContextPath() + "/profile_img/" + filename);

		int board_cnt = boardDTO.getBoard_cnt() + 1; // 조회수 증가
		boardDTO.setBoard_cnt(board_cnt);

		boardDTO.setBoard_cnt(board_cnt); // 글의 조회수
		//boardDTO.setBoard_condition("BOARD_UPDATE_CNT"); // 글 조회수 업데이트 컨디션
		boardService.updateCnt(boardDTO); // 글 조회수 업데이트

		System.out.println("게시글 조회수 업데이트: " + board_cnt);

		replyDTO.setReply_board_num(boardDTO.getBoard_num()); // boardDTO 안에 있는 것들만 보내는 것들로
		List<ReplyDTO> replyList = replyService.selectAll(replyDTO);

		//System.out.println("댓글 리스트 조회: " + replyList);

		model.addAttribute("BOARD", boardDTO); // 글 정보 넘겨주기
		model.addAttribute("REPLY", replyList); // 댓글 리스트 넘겨주기

		return path;
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
