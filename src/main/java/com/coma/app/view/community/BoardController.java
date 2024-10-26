package com.coma.app.view.community;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.coma.app.biz.member.MemberDTO;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyService;
import com.coma.app.view.function.CKEditorDeleteFile;
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value="/boardDelete.do", method=RequestMethod.POST)
    public String boardDelete(HttpServletRequest request, HttpServletResponse response, BoardDTO boardDTO) {
        //기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
//		ActionForward forward = new ActionForward();
//		String path = "MYPAGEPAGEACTION.do";
//		boolean flagRedirect = false;

        //로그인 정보가 있는지 확인해주고
        String login[] = LoginCheck.Success(request, response);
        //사용자 아이디
        String member_id = login[0];

        //만약 로그인 정보가 없다면
        if(member_id == null) {
            //LoginPageAction 페이지로 전달해줍니다.
//			path = "LOGINPAGEACTION.do";
            //리다이렉트 방식으로 보내줍니다.
//			flagRedirect = true;
            return "redirect:Login.do";
        }
        else {
            //사용자가 선택한 글번호를 받아서
//			data.setboard_num(Integer.parseInt(request.getParameter("board_num")));
//			data.setboard_writer_id(member_id);
            //model 에 전달해 글을 삭제하고
            boolean flag = boardService.delete(boardDTO);

        }

//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
        return "redirect:myPage.do";
    }

    @RequestMapping(value="/boardInsert.do", method=RequestMethod.POST)
    public String boardInsert(HttpServletRequest request, HttpServletResponse response, Model model, BoardDTO boardDTO) {

        //기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
//		ActionForward forward = new ActionForward();
//		String path = "info.jsp";//메인커뮤니티페이지로 이동
//		boolean flagRedirect = false;//포워드 방식으로

        //로그인 정보가 있는지 확인해주고
        String login[] = LoginCheck.Success(request, response);
        String member_id = login[0];//세션에 있는 사용자의 아이디

        System.out.println("로그인 확인: " + login);//로그인 확인 로그
        //만약 로그인 정보가 없다면
        if(member_id == null) {
            //로그인 페이지로 전달해줍니다.
//			request.setAttribute("msg", "글 작성은 로그인 후 사용 가능합니다.");
//			request.setAttribute("path", "LOGINPAGEACTION.do");
            model.addAttribute("title",  "로그인을 해주세요.");

            model.addAttribute("msg",  "글 작성은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "LoginPage");


        }
        else {
            // 요청에서 게시글 제목과 내용을 가져옴
//			String boardTitle = request.getParameter("VIEW_TITLE");
            String boardContent = request.getParameter("content");
//			String boardLocation = request.getParameter("board_location");

            //------------------------------------------------------------------------
            //CKEditor 제거된 이미지 제거하는 로직
            //UTF-8 형식으로 보내주니 UTF-8 로 인코딩 해주고
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("인코딩 실패");
            }
            //세션을 불러와서
            HttpSession session = request.getSession();
            //세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
            int folder_session = (session.getAttribute("FOLDER_NUM") != null) ? (Integer)session.getAttribute("FOLDER_NUM"):0;

            //View에서 보내준 내용 확인합니다.
//			System.out.println(boardContent);
            //세션에 있는 값이 0보다 크다면 이미지 폴더가 생성된 것으로 if문 실행
            if(folder_session > 0) {
                //폴더를 명시해줍니다.
                String folder = "/board_img/"+member_id+"/"+folder_session;

                //명시된 폴더를 추가해주고 내 서버의 폴더주소를 불러옵니다.
                String uploadPath = request.getServletContext().getRealPath(folder);

                System.out.println("BoardInsertAction.java 확인용 로그 : "+uploadPath);

                //넘어온 이미지와 서버에 저장된 이미지를 체크하여
                //없는 이미지는 삭제해줍니다.
                CKEditorDeleteFile.imgDelete(boardContent, uploadPath);
            }
            //------------------------------------------------------------------------

//			System.out.println("게시글 제목: " + boardTitle);//제목 로그
            System.out.println("게시글 내용: " + boardContent);//내용 로그
            System.out.println("게시글 작성자: " + member_id);//작성자 로그


            // 게시글의 제목, 내용, 지역, 작성자를 DTO에 설정
//			BoardDTO boardDTO = new BoardDTO();
//			boardDTO.setboard_title(boardTitle);
//			boardDTO.setboard_content(boardContent);
//
//			boardDTO.setboard_location(Location(boardLocation));
//			boardDTO.setboard_writer_id(member_id);

            // 게시글을 데이터베이스에 저장
//			BoardDAO boardDAO = new BoardDAO();

//			request.setAttribute("msg", "글이 작성 완료");
//			request.setAttribute("path", "MainCommunityPage.do");
//			session.setAttribute("FOLDER_NUM",null);
            model.addAttribute("title", "성공!");

            model.addAttribute("msg", "글 작성이 완료되었습니다");
            model.addAttribute("path", "Community.do");
            model.addAttribute("FOLDER_NUM",null);

            boolean flag = boardService.insert(boardDTO);//글 insert
            if (!flag) {
                //실패했을때
//				request.setAttribute("msg", "글이 작성 실패");
//				request.setAttribute("path", "INSERTBOARDPAGEACTION.do");
//				session.setAttribute("FOLDER_NUM",folder_session);
                model.addAttribute("title", "실패..");

                model.addAttribute("msg", "글이 작성이 실패했습니다");
                model.addAttribute("path", "insertBoard.do");
                model.addAttribute("FOLDER_NUM", folder_session);

            }
        }
//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
//		return forward;
        return "views/info";

    }


    @RequestMapping(value="/boardInsert.do", method=RequestMethod.GET)
    public String boardInsert(HttpServletRequest request, HttpServletResponse response) {

//		ActionForward forward = new ActionForward();
//		String path = "editing.jsp";//글작성페이지
//		boolean flagRedirect = false;//네비게이션 바 때문에 로그인정보 필요

        //로그인 정보가 있는지 확인해주고
        String login[] = LoginCheck.Success(request, response);
        String member_id = login[0];

        System.out.println("InsertBoard 로그인 정보 로그 : "+member_id);
        //만약 로그인 정보가 없다면
        if(member_id == null) {
            //로그인 페이지로 넘어간다
//			path = "LOGINPAGEACTION.do";
            return "redirect:login.do";
        }

        //로그인이 되어 있다면
//		//글 작성 페이지로 넘어간다
//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);

        return "views/editing";

    }

    @RequestMapping(value="/boardUpdate.do", method=RequestMethod.POST)
    public String boardUpdate(HttpServletRequest request, HttpServletResponse response, Model model, BoardDTO boardDTO ) {
        // 기본으로 넘어가야하는 페이지와 redirect 여부를 설정
//		ActionForward forward = new ActionForward();
//		String path = "MYPAGEPAGEACTION.do"; // 마이페이지로
//		boolean flagRedirect = false; // 포워드 방식

        // 로그인 정보가 있는지 확인
        String login[] = LoginCheck.Success(request, response);
        String member_id = login[0]; // 세션에 있는 사용자의 아이디
        System.out.println("로그인 확인: " + member_id);

        // 만약 로그인 정보가 없다면
        if (member_id == null) {
            // 로그인 페이지로 전달
//			path = "LOGINPAGEACTION.do";
            return "redirect:login.do";

        } else {
            // 로그인이 되어있다면
            // 업데이트 가능
//			String board_title = request.getParameter("VIEW_TITLE"); // 제목 받기
//			String board_location = request.getParameter("VIEW_BOARD_LOCATION"); // 제목 받기
//			String board_content = request.getParameter("VIEW_CONTENT"); // 내용 받기
//			int board_num = Integer.parseInt(request.getParameter("VIEW_BOARD_NUM")); // 번호 받기

//			System.out.println("게시글 제목: " + board_title);
//			System.out.println("게시글 지역: " + board_location);
//			System.out.println("게시글 작성자 ID: " + member_id);
//			System.out.println("게시글 내용: " + board_content);
//			System.out.println("게시글 번호: " + board_num);
            //------------------------------------------------------------------------
            //CKEditor 제거된 이미지 제거하는 로직
            //UTF-8 형식으로 보내주니 UTF-8 로 인코딩 해주고
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("인코딩 실패");
            }
            //세션을 불러와서
            HttpSession session = request.getSession();
            //세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
            int folder_session = (session.getAttribute("UPDATE_FOLDER_NUM") != null) ? (Integer)session.getAttribute("UPDATE_FOLDER_NUM"):0;

            //View에서 보내준 내용 확인합니다.
//			System.out.println(board_content);
            //세션에 있는 값이 0보다 크다면 이미지 폴더가 생성된 것으로 if문 실행
            if(folder_session > 0) {
                //폴더를 명시해줍니다.
                String folder = "/board_img/"+member_id+"/"+folder_session;

                //명시된 폴더를 추가해주고 내 서버의 폴더주소를 불러옵니다.
                String uploadPath = request.getServletContext().getRealPath(folder);

                System.out.println("BoardInsertAction.java 확인용 로그 : "+uploadPath);

                //넘어온 이미지와 서버에 저장된 이미지를 체크하여
                //없는 이미지는 삭제해줍니다.
//				CKEditorDeleteFile.imgDelete(board_content, uploadPath);
                CKEditorDeleteFile.imgDelete(boardDTO.getBoard_content(), uploadPath);
            }
            //------------------------------------------------------------------------

//			BoardDTO boardDTO = new BoardDTO();
//			BoardDAO boardDAO = new BoardDAO();

//			boardDTO.setboard_num(board_num);
//			boardDTO.setboard_content(board_content);
//			boardDTO.setboard_location(Location(board_location));
//			boardDTO.setboard_title(board_title);

//            boardDTO.setBoard_condition("BOARD_UPDATE_CONTENT_TITLE"); // 글 수정 컨디션
            boolean updateFlag = boardService.updateContentTitle(boardDTO); // 업데이트


//            request.setAttribute("MEMBER_ID", login); // 로그인한 사용자 정보
        }

//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
        return "views/MyPage";
    }
    /* 뷰에서 전달받은 지역 값을 실제 지역명으로 변환하는 함수
     */

    @RequestMapping(value="/boardUpdate.do", method=RequestMethod.GET)
    public String boardUpdatePage(HttpServletRequest request, HttpServletResponse response, Model model, BoardDTO boardDTO) {
        //기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
//		ActionForward forward = new ActionForward();
//		String path = "updateEditing.jsp";
//		boolean flagRedirect = false;

        //로그인 정보가 있는지 확인해주고
        String login[] = LoginCheck.Success(request, response);
        //사용자 아이디
        String member_id = login[0];

        //만약 로그인 정보가 없다면
        if(member_id == null) {
            //LoginPageAction 페이지로 전달해줍니다.
//			path = "LOGINPAGEACTION.do";
            return "redirect:login.do";
        }
        else {
//			BoardDAO boardDAO = new BoardDAO();
//			BoardDTO data = new BoardDTO();
            //사용자가 선택한 글번호를 받아서
//			data.setboard_num(Integer.parseInt(request.getParameter("board_num")));
            boardDTO.setBoard_writer_id(member_id);
//            boardDTO.setBoard_condition("BOARD_ONE_WRITER_ID");
            //model 에 전달하여 글 내용을 받아오고
            boardDTO = boardService.selectOneWriterId(boardDTO);

            //만약 데이터가 null 이라면 mypage.do 로 전달
            if(boardDTO == null) {
//				path="info.jsp";
//				request.setAttribute("path", "MYPAGEPAGEACTION.do");
//				request.setAttribute("msg", "없는 게시글입니다.");
                model.addAttribute("title", "죄송합니다");

                model.addAttribute("path", "Mypage");
                model.addAttribute("msg", "없는 게시글입니다.");
                return "info";



            }
            else {
                //해당 글 내용을 view 로 전달해줍니다.
//				request.setAttribute("BOARD_NUM", data.getboard_num());
//				request.setAttribute("BOARD_TITLE", data.getboard_title());
//				System.out.println("지역 로그 1 : "+data.getboard_location());
//				System.out.println("지역 로그 2 : "+location(data.getboard_location()));
//				request.setAttribute("BOARD_LOCATION", location(data.getboard_location()));
                model.addAttribute("BOARD_NUM", boardDTO.getBoard_num());
                model.addAttribute("BOARD_TITLE", boardDTO.getBoard_title());
                model.addAttribute("BOARD_LOCATION", location(boardDTO.getBoard_location()));



                String content = boardDTO.getBoard_content();
                request.setAttribute("BOARD_CONTENT", content);


                HttpSession session = request.getSession();
                try {
                    //글 내용에서 img 태그가 있다면 해당 이미지 폴더의 번호만 가져오는 로직
                    content = content.substring(content.lastIndexOf("img")+3).split("/")[2];
                    System.out.println("BoardUpdatePageAction.java content 로그 : "+content);
                    session.setAttribute("UPDATE_FOLDER_NUM", Integer.parseInt(content));
                } catch (Exception e) {
                    session.setAttribute("UPDATE_FOLDER_NUM", 0);
                }

            }


        }

//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
        return "views/updateEditing";
    }

    @RequestMapping(value="/content.do", method=RequestMethod.GET)
    public String content(HttpServletRequest request, HttpServletResponse response,
                          Model model, BoardDTO boardDTO, ReplyDTO replyDTO, MemberDTO memberDTO, ServletContext servletContext) {
        // 글 하나 선택 페이지
//		ActionForward forward = new ActionForward();
//		String path = "post.jsp"; // 선택한 글 하나 보는 페이지
//		boolean flagRedirect = false; // 포워드 방식

        // 글 pk 가져오기
//		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		System.out.println("게시글 번호: " + board_num);

        // pk로 selectOne하기
//		BoardDAO boardDAO = new BoardDAO();
//		BoardDTO boardDTO = new BoardDTO();
//		boardDTO.setboard_num(board_num); // pk
//        boardDTO.setboard_condition("BOARD_ONE"); // 글 selectOne 컨디션
        boardDTO = boardService.selectOne(boardDTO); // pk로 글 selectOne

        System.out.println("게시글 정보 조회: " + boardDTO);

        // MemberDAO에서 프로필 정보를 가져옴
//		MemberDAO memberDAO = new MemberDAO();
//		MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(boardDTO.getBoard_writer_id()); // 세션에 있는 사용자의 아이디
//        memberDTO.setmember_condition("MEMBER_SEARCH_ID"); // member selectOne 컨디션
        memberDTO = memberService.selectOneSearchId(memberDTO); // 프로필 사진을 보여주기 위해 member selectOne
        System.out.println("회원 정보 조회: " + memberDTO);

        String filename = memberDTO.getMember_profile();
        model.addAttribute("member_profile", servletContext.getContextPath() + "/profile_img/" + filename);

//		request.setAttribute("member_profile", request.getServletContext().getContextPath() + "/profile_img/" + filename);

        int board_cnt = boardDTO.getBoard_cnt() + 1; // 조회수 증가
        boardDTO.setBoard_cnt(board_cnt);

//		BoardDTO data_cnt = new BoardDTO();
        boardDTO.setBoard_cnt(board_cnt);//		data_cnt.setboard_num(board_num); // 글의 번호
//        boardDTO.setBoard_condition("BOARD_UPDATE_CNT"); // 글 조회수 업데이트 컨디션
        boardService.updateCnt(boardDTO); // 글 조회수 업데이트

        System.out.println("게시글 조회수 업데이트: " + board_cnt);

//		ReplyDAO replyDAO = new ReplyDAO();
//		ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setReply_board_num(boardDTO.getBoard_num()); // boardDTO 안에 있는 것들만 보내는 것들로
        List<ReplyDTO> replyList = replyService.selectAll(replyDTO);

        //System.out.println("댓글 리스트 조회: " + replyList);

//		request.setAttribute("BOARD", boardDTO); // 글 정보 넘겨주기
//		request.setAttribute("REPLY", replyList); // 댓글 리스트 넘겨주기
        model.addAttribute("BOARD", boardDTO);
        model.addAttribute("REPLY", replyList);

//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
        return "views/content";
    }

    public String location(String location) {
        Map<String, String> location_Map = new HashMap<String, String>();

        location_Map.put("서울특별시", "SEOUL");
        location_Map.put("경기도", "GYEONGGI");
        location_Map.put("인천광역시", "INCHEON");
        location_Map.put("세종특별자치도", "SEJONG");
        location_Map.put("부산광역시", "BUSAN");
        location_Map.put("대구광역시", "DAEGU");
        location_Map.put("대전광역시", "DAEJEON");
        location_Map.put("광주광역시", "GWANGJU");
        location_Map.put("울산광역시", "ULSAN");
        location_Map.put("충청남도", "CHUNGCHEONGNAMDO");
        location_Map.put("충청북도", "CHUNGCHEONGBUKDO");
        location_Map.put("전라남도", "JEONLANAMDO");
        location_Map.put("전라북도", "JEONLABUKDO");
        location_Map.put("경상남도", "GYEONGSANGNAMDO");
        location_Map.put("경상북도", "GYEONGSANGBUKDO");
        location_Map.put("강원도", "GANGWONDO");

        return location_Map.getOrDefault(location, "SEOUL");
    }


}
