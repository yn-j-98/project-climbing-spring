package com.coma.app.view.community;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyService;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class BoardController {

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
        //model 에 전달해 글을 삭제
        boolean flag = this.boardService.delete(boardDTO);

        return "redirect:myPage.do";
    }

    @LoginCheck
    @PostMapping("/boardInsert.do")
    public String boardInsert(HttpSession session, Model model, BoardDTO boardDTO) {

        String member_id = (String) session.getAttribute("MEMBER_ID");//세션에 존재하는 사용자의 id

        log.info("boardInsert.member_id [{}]", member_id);//로그인 확인 로그
            // 요청에서 게시글 제목과 내용을 가져옴
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

            }

        return "views/info";
    }

    @LoginCheck
    @GetMapping("/boardInsert.do")
    public String boardInsert(HttpSession session) {

        //로그인 정보가 있는지 확인해주고

        String member_id = (String) session.getAttribute("MEMBER_ID");

        log.info("boardInsert.member_id [{}]", member_id);//로그인 확인 로그
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
    @PostMapping(value = "/boardUpdate.do")
    public String boardUpdate(Model model, BoardDTO boardDTO) {

        String title = "게시글 수정";
        String msg = "게시글이 수정되었습니다.";
        String path = "myPage.do";

        boardDTO.setBoard_location(location(boardDTO.getBoard_location()));

        if(!this.boardService.updateContentTitle(boardDTO)) {//글 수정 실패시
            msg = "게시긓 수정을 실패했습니다.";
            path = "boardUpdate.do?board_num=" + boardDTO.getBoard_num();
        }

        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", path);

        return "views/info";
    }
    @LoginCheck
    @GetMapping("/boardUpdate.do")
    public String boardUpdatePage(Model model, BoardDTO boardDTO) {

            //사용자가 선택한 글번호를 받아서


//            boardDTO.setBoard_condition("BOARD_ONE_WRITER_ID");
            //model 에 전달하여 글 내용을 받아오고
            boardDTO = this.boardService.selectOne(boardDTO);

            //만약 데이터가 null 이라면 mypage.do 로 전달
            if (boardDTO == null) {

                model.addAttribute("title", "죄송합니다.");

                model.addAttribute("path", "myPage.do");
                model.addAttribute("msg", "없는 게시글입니다.");

                return "views/info";

            } else {
                //해당 글 내용을 view 로 전달해줍니다.

                log.info("BoardUpdate.BoardLocation [{}]", boardDTO.getBoard_location());//지역
                model.addAttribute("board_num", boardDTO.getBoard_num());
                model.addAttribute("board_title", boardDTO.getBoard_title());
                model.addAttribute("board_location", location(boardDTO.getBoard_location()));


                String content = boardDTO.getBoard_content();
                model.addAttribute("board_content", content);

                try {
                    //글 내용에서 img 태그가 있다면 해당 이미지 폴더의 번호만 가져오는 로직
                    content = content.substring(content.lastIndexOf("img") + 3).split("/")[2];

                    log.info("BoardUpdate.boardUpdate.content [{}]", content);//내용

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


        log.info("content.boardDTO :{[]}", boardDTO);
        // MemberDAO에서 프로필 정보를 가져옴

        memberDTO.setMember_id(boardDTO.getBoard_writer_id()); // 세션에 있는 사용자의 아이디

//        memberDTO.setmember_condition("MEMBER_SEARCH_ID"); // member selectOne 컨디션
        memberDTO = this.memberService.selectOneSearchId(memberDTO); // 프로필 사진을 보여주기 위해 member selectOne
        log.info("content.memberDTO :{[]}", memberDTO);

        model.addAttribute("member_profile", memberDTO.getMember_profile());


        int board_cnt = boardDTO.getBoard_cnt() + 1; // 조회수 증가
        boardDTO.setBoard_cnt(board_cnt);

//        boardDTO.setBoard_condition("BOARD_UPDATE_CNT"); // 글 조회수 업데이트 컨디션
        this.boardService.updateCnt(boardDTO); // 글 조회수 업데이트

        log.info("content.board_cnt :{[]}", board_cnt);
        model.addAttribute("content.board_cnt",  board_cnt);

        replyDTO.setReply_board_num(boardDTO.getBoard_num()); // boardDTO 안에 있는 것들만 보내는 것들로
        List<ReplyDTO> replyList = this.replyService.selectAll(replyDTO);

        log.info("content.replyList :{[]}", replyList);
        model.addAttribute("content.replyList",  replyList);

        model.addAttribute("member_profile", memberDTO.getMember_profile());

        model.addAttribute("BOARD", boardDTO);// 글 정보 넘겨주기
        model.addAttribute("REPLY", replyList);// 댓글 리스트 넘겨주기


        return "views/content";
    }

    public String location(String location) {
        Map<String, String> locationMap = new HashMap<String, String>();

        locationMap.put("SEOUL", "서울특별시");
        locationMap.put("GYEONGGI", "경기도");
        locationMap.put("INCHEON", "인천광역시");
        locationMap.put("CHUNGNAM", "충청남도");

        return locationMap.getOrDefault(location, "SEOUL");
    }


}
