package com.coma.app.view.community;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.reply.ReplyDAO;
import com.coma.app.biz.reply.ReplyDTO;

import jakarta.servlet.http.HttpSession;

@Controller("replyController")
public class ReplyController{

	@RequestMapping("/REPLYACTION.do")
	public String replay(HttpSession session, Model model, ReplyDTO replyDTO, ReplyDAO replyDAO) {
		// 기본으로 넘어가야하는 페이지와 redirect 여부를 설정
		String path = "info";
		String info_path = "BOARDONEPAGEACTION.do?model_board_num=" + replyDTO.getModel_reply_board_num();
		// 로그인 정보가 있는지 확인
		String memeber_id = (String)session.getAttribute("MEMBER_ID");
		System.out.println("로그인 확인: " + memeber_id);

		// 만약 로그인 정보가 없다면
		if (memeber_id == null) {
			// 로그인 페이지로 전달
			model.addAttribute("msg", "댓글 작성은 로그인 후 사용 가능합니다.");
			model.addAttribute("path", "LOGINPAGEACTION.do");
		} 
		else {
			// 댓글 작성
			if(!replyDAO.insert(replyDTO)) {
				model.addAttribute("msg", "댓글 작성을 성공하였습니다.");
			}
			else {
				model.addAttribute("msg", "댓글 작성을 성공하였습니다.");
			}
			model.addAttribute("path", info_path);
		}
		return path;
	}
	
	@RequestMapping("/REPLYDELETEACTION.do")
    public String execute(HttpSession session, Model model, ReplyDTO replyDTO, ReplyDAO replyDAO) {
		String path = "info";
		String info_path = "BOARDONEPAGEACTION.do?model_board_num=" + replyDTO.getModel_reply_board_num();
		// 로그인 정보가 있는지 확인
		String memeber_id = (String)session.getAttribute("MEMBER_ID");
		System.out.println("로그인 확인: " + memeber_id);

        // 만약 로그인 정보가 없다면
        if (memeber_id == null) {
            // 로그인 페이지로 전달
            model.addAttribute("msg", "댓글 삭제는 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "LOGINPAGEACTION.do");
        } 
        else {
            // 댓글 삭제
            if(replyDAO.delete(replyDTO)) {
                model.addAttribute("msg", "댓글 삭제를 성공하였습니다.");
            }
            else {
                // 로그인 페이지로 전달
                model.addAttribute("msg", "댓글 삭제를 실패하였습니다.");
            }
            model.addAttribute("path", info_path);
        }
        return path;
    }
	
	@RequestMapping("/REPLYUPDATEACTION.do")
    public String replyUpdate(HttpSession session, Model model, ReplyDTO replyDTO, ReplyDAO replyDAO) {
		String path = "info";
		String info_path = "BOARDONEPAGEACTION.do?model_board_num=" + replyDTO.getModel_reply_board_num();
		// 로그인 정보가 있는지 확인
		String memeber_id = (String)session.getAttribute("MEMBER_ID");
		System.out.println("로그인 확인: " + memeber_id);

        // 만약 로그인 정보가 없다면
        if (memeber_id == null) {
            // 로그인 페이지로 전달
            model.addAttribute("msg", "댓글 수정은 로그인 후 사용 가능합니다.");
            model.addAttribute("path", "LOGINPAGEACTION.do");
        } 
        else {
            // 댓글 업데이트 가능
            if(replyDAO.update(replyDTO)) {
                model.addAttribute("msg", "댓글 수정을 성공하였습니다.");
            }
            else {
                // 로그인 페이지로 전달
                model.addAttribute("msg", "댓글 수정을 실패하였습니다.");
            }
            model.addAttribute("path", info_path);
        }

        return path;
    }
}
