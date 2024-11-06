package com.coma.app.view.community;

import com.coma.app.biz.board.BoardDTO;
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

@Slf4j
@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @LoginCheck
    @PostMapping("/reply.do")
    public String Reply(HttpSession session, Model model, ReplyDTO replyDTO) {
        // 댓글 달기
        // 댓글 작성 후 해당 글 보는 페이지로 이동하기 위해 글 번호를 get 방식으로 전달
        log.info("board_num : [{}]", replyDTO.getReply_board_num());
        String info_path = "content.do?board_num=" + replyDTO.getReply_board_num(); // 댓글 작성 후 이동할 경로

        // 로그인 정보가 있는지 확인
        String member_id = (String) session.getAttribute("MEMBER_ID");
        log.info("member_id : [{}]", member_id);

        // 댓글 작성
        replyDTO.setReply_writer_id(member_id); // 댓글 작성자 설정

        boolean insertResult = this.replyService.insert(replyDTO); // 댓글 insert

        log.info("insertResult : [{}]", insertResult);
        if (!insertResult) {
            model.addAttribute("title", "실패");
            model.addAttribute("msg", "댓글 작성을 실패하였습니다.");
        } else {
            log.info("Reply.board_num : [{}]", replyDTO.getReply_board_num());
            model.addAttribute("title", "성공");
            model.addAttribute("msg", "댓글 작성을 성공하였습니다.");
        }
        model.addAttribute("path", info_path); // 댓글 작성 후

        return "views/info";
    }


    @LoginCheck
    @GetMapping("/replyDelete.do")
    public String boardDelete(HttpSession session, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {
        // 댓글 삭제 후 해당 글 보는 페이지로 이동하기 위해 글 번호를 get 방식으로 전달
        String info_path = "content.do?board_num=" + replyDTO.getReply_board_num(); // 댓글 삭제 후 이동

        // 댓글 삭제
        String reply_id = (String) session.getAttribute("MEMBER_ID"); // 세션에서 사용자 아이디 가져오기

        log.info("board_num : [{}]", replyDTO.getReply_board_num());
        log.info("reply_id : [{}]", reply_id);
        replyDTO.setReply_board_num(boardDTO.getBoard_num()); // 삭제할 댓글이 속한 게시판 번호
        replyDTO.setReply_writer_id(reply_id); // 삭제하려는 댓글의 작성자 아이디

        boolean deleteReply = this.replyService.delete(replyDTO); // 댓글 삭제

        if (deleteReply) { // 댓글 삭제 성공
            model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
        }
        model.addAttribute("path", info_path); // 삭제 후

        return "views/info";

    }

    @LoginCheck
    @PostMapping("/replyUpdate.do")
    public String replyUpdate(HttpSession session, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {
        // 댓글 수정 후 해당 글 보는 페이지로 이동하기 위해 글 번호를 get 방식으로 전달
        String info_path = "content.do?board_num=" + replyDTO.getReply_board_num();

        // 댓글 업데이트 가능
        String reply_writer_id = (String) session.getAttribute("MEMBER_ID"); // 세션에서 사용자 아이디 가져오기

        log.info("replyUpdate.reply_id : [{}]", reply_writer_id);

        boolean updateResult = this.replyService.update(replyDTO); // 댓글 업데이트

        if (updateResult) {
            model.addAttribute("msg", "댓글 수정을 성공하였습니다.");
        } else {
            model.addAttribute("msg", "댓글 수정을 실패하였습니다.");
        }

        model.addAttribute("path", info_path); // 수정 후

        return "views/info";
    }

}

