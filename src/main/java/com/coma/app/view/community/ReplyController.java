package com.coma.app.view.community;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyService;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @LoginCheck
    @PostMapping("/reply.do")
    public String Reply(HttpSession session, Model model, ReplyDTO replyDTO) {
        // 댓글 달기
//		int reply_board_num = Integer.parseInt(request.getParameter("board_id")); // 댓글 작성할 게시글 번호

        String info_path = "redirect:content.do?board_num=" + replyDTO.getReply_board_num();



        // 로그인 정보가 있는지 확인

        String memeber_id = (String) session.getAttribute("MEMBER_ID");
        System.out.println("로그인 확인: " + memeber_id);

        // 만약 로그인 정보가 없다면
        if (memeber_id == null) {
            // 로그인 페이지로 전달

            model.addAttribute("title", "로그인을 해주세요");
            model.addAttribute("msg", "댓글 작성은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "login.do");
        }
        else {
            // 댓글 작성

            String reply_writer_id = memeber_id; // 세션에 있는 사용자의 아이디
//
            System.out.println("댓글 작성자 ID: " + reply_writer_id);



            replyDTO.setReply_writer_id(reply_writer_id); // 댓글 작성자

            boolean insertResult = this.replyService.insert(replyDTO); // 댓글 삽입

            if(!insertResult) {

                model.addAttribute("title", "실패");
                model.addAttribute("msg", "댓글 작성을 실패하였습니다.");
            }
            else {

                model.addAttribute("title", "성공");
                model.addAttribute("msg", "댓글 작성을 성공하였습니다.");
            }

            model.addAttribute("path", info_path);
        }


        return "views/info";
    }


    @LoginCheck
    @PostMapping("/replyDelete.do")
    public String boardDelete(HttpSession session, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {

        String info_path = "redirct:content.do?board_num=" + boardDTO.getBoard_num();


        // 댓글 삭제
        String reply_id = (String) session.getAttribute("MEMBER_ID"); // 세션에 있는 사용자의 아이디

        System.out.println("사용자 ID: " + reply_id);


        replyDTO.setReply_writer_id(reply_id); // 사용자 아이디

        boolean deleteReply = this.replyService.delete(replyDTO); // 댓글 삭제

        if(deleteReply) {
            model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
        }
        else {

            model.addAttribute("msg", "댓글 삭제를 실패했습니다.");
            model.addAttribute("path", "login.do");

        }
        model.addAttribute("path", info_path);


        return "views/info";

    }

    @LoginCheck
    @PostMapping("/replyUpdate.do")
    public String replyUpdate(HttpSession session, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {
//    // 댓글 수정 후 해당 글 보는 페이지로 이동하기 위해 글 번호를 get 방식으로 전달


        String info_path = "redirct:content.do?board_num=" + boardDTO.getBoard_num();
        // 댓글 업데이트 가능
        String reply_writer_id = (String) session.getAttribute("MEMBER_ID"); // 세션에 있는 사용자의 아이디


        System.out.println("사용자 ID: " + reply_writer_id);


        boolean updateResult = this.replyService.update(replyDTO); // 업데이트

        if(updateResult) {
            model.addAttribute("msg", "댓글 수정을 성공하였습니다.");
        }
        else {

            model.addAttribute("msg", "댓글 수정을 실패하였습니다.");
        }

        model.addAttribute("path", info_path);

        return "views/info";
    }


}
