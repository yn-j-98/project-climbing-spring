package com.coma.app.view.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyService;
import com.coma.app.view.function.LoginCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    @RequestMapping(value="/reply.do", method=RequestMethod.POST)
    public String Reply(HttpServletRequest request, HttpServletResponse response, Model model, ReplyDTO replyDTO) {
        // 댓글 달기
//		int reply_board_num = Integer.parseInt(request.getParameter("board_id")); // 댓글 작성할 게시글 번호
//
//		// 기본으로 넘어가야하는 페이지와 redirect 여부를 설정
//		ActionForward forward = new ActionForward();
//		String path = "info.jsp";
        // 글 하나 보는 페이지로 돌아오기 위해 해당 댓글의 글번호를 get 방식으로 전달
//		boolean flagRedirect = false; // 리다이렉트 방식 사용

        String info_path = "content.do?board_num=" + replyDTO.getReply_board_num();



        // 로그인 정보가 있는지 확인
        String login[] = LoginCheck.Success(request, response);
        String memeber_id = login[0];
        System.out.println("로그인 확인: " + memeber_id);

        // 만약 로그인 정보가 없다면
        if (memeber_id == null) {
            // 로그인 페이지로 전달
//			request.setAttribute("msg", "댓글 작성은 로그인 후 사용 가능합니다.");
//			request.setAttribute("path", "LOGINPAGEACTION.do");
            model.addAttribute("title", "로그인을 해주세요");
            model.addAttribute("msg", "댓글 작성은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "login.do");
        }
        else {
            // 댓글 작성
//			String reply_content = request.getParameter("reply_content");
//			String reply_writer_id = memeber_id; // 세션에 있는 사용자의 아이디
//
//			System.out.println("댓글 작성자 ID: " + reply_writer_id);
//			System.out.println("댓글 내용: " + reply_content);
//			System.out.println("댓글 작성할 게시글 번호: " + reply_board_num);

//			ReplyDTO replyDTO = new ReplyDTO();
//			ReplyDAO replyDAO = new ReplyDAO();
//			replyDTO.setModel_reply_board_num(reply_board_num); // 해당 댓글의 글 번호
//			replyDTO.setModel_reply_content(reply_content); // 댓글 내용
//			replyDTO.setModel_reply_writer_id(reply_writer_id); // 댓글 작성자

            boolean insertResult = replyService.insert(replyDTO); // 댓글 삽입

            if(!insertResult) {
//				request.setAttribute("msg", "댓글 작성을 성공하였습니다.");
                model.addAttribute("title", "실패");
                model.addAttribute("msg", "댓글 작성을 실패하였습니다.");
            }
            else {
//				request.setAttribute("msg", "댓글 작성을 성공하였습니다.");
                model.addAttribute("title", "성공");
                model.addAttribute("msg", "댓글 작성을 성공하였습니다.");
            }
//			request.setAttribute("path", info_path);
            model.addAttribute("path", info_path);
        }

//		forward.setPath(path);
//		forward.setRedirect(flagRedirect);
        return "views/info";
    }



    @RequestMapping(value="/replyDelete.do", method=RequestMethod.POST)
    public String boardDelete(HttpServletRequest request, HttpServletResponse response, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {
//    int board_num = Integer.parseInt(request.getParameter("board_num")); // 댓글이 속한 게시글 번호
//    ActionForward forward = new ActionForward();
//    String path = "info.jsp";
//    // 댓글 삭제 후 해당 글 하나 보는 페이지로 돌아오기 위해 글의 번호를 get 방식으로 전달
//    boolean flagRedirect = false; // 리다이렉트 방식 사용

        String info_path = "content.do?board_num=" + boardDTO.getBoard_num();

        // 로그인 정보가 있는지 확인
        String login[] = LoginCheck.Success(request, response);
        System.out.println("로그인 확인: " + login[0]);

        // 만약 로그인 정보가 없다면
        if (login[0] == null) {
            // 로그인 페이지로 전달
//        request.setAttribute("msg", "댓글 삭제는 로그인 후 사용 가능합니다.");
//        request.setAttribute("path", "LOGINPAGEACTION.do");

            model.addAttribute("title", "로그인을 해주세요");
            model.addAttribute("msg", "댓글 삭제는 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "login.do");
        }
        else {
            // 댓글 삭제
//        int reply_num = Integer.parseInt(request.getParameter("replyId")); // 댓글 PK
            String reply_id = login[0]; // 세션에 있는 사용자의 아이디

//        System.out.println("댓글 번호: " + reply_num);
//        System.out.println("사용자 ID: " + reply_id);

//        ReplyDTO replyDTO = new ReplyDTO();
//        ReplyDAO replyDAO = new ReplyDAO();

            // replyDTO.setReply_writer_id(reply_id); // 사용자 아이디
//        replyDTO.setModel_reply_num(reply_num); // 댓글 번호

            boolean deleteReply = replyService.delete(replyDTO); // 댓글 삭제

            if(deleteReply) {
//            request.setAttribute("msg", "댓글 삭제를 성공하였습니다.");
                model.addAttribute("title", "로그인을 해주세요");

                model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
            }
            else {
                // 로그인 페이지로 전달
//            request.setAttribute("msg", "댓글 삭제를 실패하였습니다.");
                model.addAttribute("title", "성공");

                model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
            }
//        request.setAttribute("msg", "댓글 삭제를 성공하였습니다.");
            model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
        }

//    forward.setPath(path);
//    forward.setRedirect(flagRedirect);
        return "views/info";

    }

    @RequestMapping(value="/replyUpdate.do", method=RequestMethod.POST)
    public String replyUpdate(HttpServletRequest request, HttpServletResponse response, Model model, BoardDTO boardDTO, ReplyDTO replyDTO) {
//    int board_num = Integer.parseInt(request.getParameter("board_id")); // 되돌아갈 글 번호
//    ActionForward forward = new ActionForward();
//    String path = "info.jsp";
//    // 댓글 수정 후 해당 글 보는 페이지로 이동하기 위해 글 번호를 get 방식으로 전달
//    boolean flagRedirect = false; // 리다이렉트 방식 사용

        String info_path = "content.do?board_num=" + boardDTO.getBoard_num();

        // 로그인 정보가 있는지 확인
        String login[] = LoginCheck.Success(request, response);
        System.out.println("로그인 확인: " + login[0]);

        // 만약 로그인 정보가 없다면
        if (login[0] == null) {
            // 로그인 페이지로 전달
//        request.setAttribute("msg", "댓글 수정은 로그인 후 사용 가능합니다.");
//        request.setAttribute("path", "LOGINPAGEACTION.do");
            model.addAttribute("title", "로그인을 해주세요");

            model.addAttribute("msg", "댓글 수정은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "login.do");
        }
        else {
            // 댓글 업데이트 가능
//        String reply_writer_id = login[0]; // 세션에 있는 사용자의 아이디
//        String reply_content = request.getParameter("reply_content"); // 댓글 내용
//        int reply_num = Integer.parseInt(request.getParameter("reply_id")); // 댓글 번호
//
//        System.out.println("댓글 번호: " + reply_num);
//        System.out.println("사용자 ID: " + reply_writer_id);
//        System.out.println("댓글 수정 내용: " + reply_content);

//        ReplyDTO replyDTO = new ReplyDTO();
//        ReplyDAO replyDAO = new ReplyDAO();
//        replyDTO.setModel_reply_num(reply_num); // 댓글 번호6
//        replyDTO.setModel_reply_content(reply_content); // 댓글 내용

            boolean updateResult = replyService.update(replyDTO); // 업데이트

            if(updateResult) {
//            request.setAttribute("msg", "댓글 수정을 성공하였습니다.");
                model.addAttribute("title", "성공");

                model.addAttribute("msg", "댓글 수정을 성공하였습니다.");
            }
            else {
                // 로그인 페이지로 전달
//            request.setAttribute("msg", "댓글 수정을 실패하였습니다.");
                model.addAttribute("title", "실패");

                model.addAttribute("msg", "댓글 수정을 실패하였습니다.");
            }
//        request.setAttribute("path", info_path);
            model.addAttribute("path", info_path);
        }

//    forward.setPath(path);
//    forward.setRedirect(flagRedirect);
        return "views/info";
    }


}
