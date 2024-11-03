package com.coma.app.view.community;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyService;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class BoardController {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private BoardService boardService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private MemberService memberService;


    @LoginCheck
    @GetMapping("/boardDelete.do")
    public String boardDelete(HttpSession session, BoardDTO boardDTO) {
        //사용자 아이디
        String member_id = (String) session.getAttribute("MEMBER_ID");

        //사용자가 선택한 글번호를 받아서
        boardDTO.setBoard_writer_id(member_id);
        //model 에 전달해 글을 삭제하고
        boolean flag = this.boardService.delete(boardDTO);

        return "redirect:myPage.do";
    }

    @PostMapping("/boardInsert.do")
    public String boardInsert(HttpSession session, Model model, BoardDTO boardDTO) {

        String member_id = (String) session.getAttribute("MEMBER_ID");

        System.out.println("로그인 확인: " + member_id);//로그인 확인 로그
        //만약 로그인 정보가 없다면
        if (member_id == null) {
            //로그인 페이지로 전달해줍니다.

            model.addAttribute("title", "로그인을 해주세요.");

            model.addAttribute("msg", "글 작성은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "LoginPage");

        } else {
            // 요청에서 게시글 제목과 내용을 가져옴

            // 세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
            // int folder_session = (session.getAttribute("FOLDER_NUM") != null) ? (Integer) session.getAttribute("FOLDER_NUM") : 0;

            // 게시글의 제목, 내용, 지역, 작성자를 DTO에 설정

            boardDTO.setBoard_writer_id(member_id);

            // 게시글을 데이터베이스에 저장

            model.addAttribute("title", "성공!");

            model.addAttribute("msg", "글 작성이 완료되었습니다");
            model.addAttribute("path", "community.do");
            model.addAttribute("FOLDER_NUM", null);

            boolean flag = this.boardService.insert(boardDTO);//글 insert
            if (!flag) {
                //실패했을때

                model.addAttribute("title", "실패..");

                model.addAttribute("msg", "글이 작성이 실패했습니다");
                model.addAttribute("path", "insertBoard.do");
                //        model.addAttribute("FOLDER_NUM", folder_session);

            }
        }

        return "views/info";

    }

    @LoginCheck
    @GetMapping("/boardInsert.do")
    public String boardInsert(HttpSession session) {

        //로그인 정보가 있는지 확인해주고

        String member_id = (String) session.getAttribute("MEMBER_ID");

        System.out.println("InsertBoard 로그인 정보 로그 : " + member_id);
        //만약 로그인 정보가 없다면
        if (member_id == null) {
            //로그인 페이지로 넘어간다
            return "redirect:login.do";
        }

        //로그인이 되어 있다면
//		//글 작성 페이지로 넘어간다

        return "views/editing";

    }

    @LoginCheck
    @GetMapping(value = "/boardUpdate.do")
    public String boardUpdate(HttpRequest request, HttpSession session, Model model, BoardDTO boardDTO) {


        String member_id = (String) session.getAttribute("MEMBER_ID"); // 세션에 있는 사용자의 아이디
        System.out.println("로그인 확인: " + member_id);

        // 만약 로그인 정보가 없다면
        if (member_id == null) {

            return "redirect:login.do";

        } else {
            // 로그인이 되어있다면
            // 업데이트 가능
            // 내용 받기
            // 번호 받기

            //세션을 불러와서

//			System.out.println(board_content);

            //board_num, board_content, board_location, board_title
            boardDTO.setBoard_location(location(boardDTO.getBoard_location()));

//            boardDTO.setBoard_condition("BOARD_UPDATE_CONTENT_TITLE"); // 글 수정 컨디션
            boolean updateFlag = this.boardService.updateContentTitle(boardDTO); // 업데이트

        }

        return "views/myPage";
    }

    /* 뷰에서 전달받은 지역 값을 실제 지역명으로 변환하는 함수
     */
    @LoginCheck
    @PostMapping("/boardUpdate.do")
    public String boardUpdatePage(Model model, BoardDTO boardDTO) {

            //사용자가 선택한 글번호를 받아서


//            boardDTO.setBoard_condition("BOARD_ONE_WRITER_ID");
            //model 에 전달하여 글 내용을 받아오고
            boardDTO = this.boardService.selectOneWriterId(boardDTO);

            //만약 데이터가 null 이라면 mypage.do 로 전달
            if (boardDTO == null) {

                model.addAttribute("title", "죄송합니다");

                model.addAttribute("path", "mypage.do");
                model.addAttribute("msg", "없는 게시글입니다.");
                return "views/info";


            } else {
                //해당 글 내용을 view 로 전달해줍니다.

                System.out.println("BoardController.BoardLocation : [" + boardDTO.getBoard_location() + "]");

                model.addAttribute("BOARD_NUM", boardDTO.getBoard_num());
                model.addAttribute("BOARD_TITLE", boardDTO.getBoard_title());
                model.addAttribute("BOARD_LOCATION", location(boardDTO.getBoard_location()));


                String content = boardDTO.getBoard_content();
                model.addAttribute("BOARD_CONTENT", content);

                try {
                    //글 내용에서 img 태그가 있다면 해당 이미지 폴더의 번호만 가져오는 로직
                    content = content.substring(content.lastIndexOf("img") + 3).split("/")[2];
                    System.out.println("BoardController.boardUpdate.content : [" + content + "]");
                    //session.setAttribute("UPDATE_FOLDER_NUM", Integer.parseInt(content));
                } catch (Exception e) {
                    //session.setAttribute("UPDATE_FOLDER_NUM", 0);
                }

            }

        return "views/updateEditing";
    }

    @GetMapping("/content.do")
    public String content(Model model, BoardDTO boardDTO, ReplyDTO replyDTO, MemberDTO memberDTO) {

        log.info("content.board_num :{[]}", boardDTO.getBoard_num());

//      boardDTO.setboard_condition("BOARD_ONE"); // 글 selectOne 컨디션
        boardDTO = this.boardService.selectOne(boardDTO); // pk로 글 selectOne

        System.out.println("게시글 정보 조회: " + boardDTO);

        // MemberDAO에서 프로필 정보를 가져옴

        memberDTO.setMember_id(boardDTO.getBoard_writer_id()); // 세션에 있는 사용자의 아이디

//        memberDTO.setmember_condition("MEMBER_SEARCH_ID"); // member selectOne 컨디션
        memberDTO = this.memberService.selectOneSearchId(memberDTO); // 프로필 사진을 보여주기 위해 member selectOne
        System.out.println("BoardController.content.memberDTO [" + memberDTO + "]");

        String filename = memberDTO.getMember_profile();
        model.addAttribute("member_profile", servletContext.getContextPath() + "/profile_img/" + filename);


        int board_cnt = boardDTO.getBoard_cnt() + 1; // 조회수 증가
        boardDTO.setBoard_cnt(board_cnt);

        boardDTO.setBoard_cnt(board_cnt);//		data_cnt.setboard_num(board_num); // 글의 번호
//        boardDTO.setBoard_condition("BOARD_UPDATE_CNT"); // 글 조회수 업데이트 컨디션
        this.boardService.updateCnt(boardDTO); // 글 조회수 업데이트

        System.out.println("BoardController.content.board_cnt [" + board_cnt + "]");

        replyDTO.setReply_board_num(boardDTO.getBoard_num()); // boardDTO 안에 있는 것들만 보내는 것들로
        List<ReplyDTO> replyList = this.replyService.selectAll(replyDTO);

        System.out.println("BoardController.content.replyList [" + replyList + "]");

        model.addAttribute("BOARD", boardDTO);// 글 정보 넘겨주기
        model.addAttribute("REPLY", replyList);// 댓글 리스트 넘겨주기


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
