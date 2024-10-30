package com.coma.app.view.admin;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyServiceImpl;
import com.coma.app.view.annotation.LoginCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Slf4j
@Controller
public class BoardManagementController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ReplyServiceImpl replyService;

    @LoginCheck
    @GetMapping("/boardManagement.do")
    public String boardManagement(Model model,BoardDTO boardDTO) {

        //-----------------------------------------------------------------------------
        // 페이지네이션
        int page = boardDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;
        int total = boardDTO.getTotal();
        log.info("boardManagement min_num = {}, page={}, total={}", min_num,page,total);

        boardDTO.setBoard_min_num(min_num);


        //-----------------------------------------------------------------------------

        List<BoardDTO> datas = boardService.selectAll(boardDTO);
        model.addAttribute("page", page);

        model.addAttribute("datas", datas);

        model.addAttribute("total", total);

        return "admin/boardManagement";
    }
    // 게시판 관리
    @PostMapping("/boardManagement.do")
    public String boardManagement(@RequestParam(value = "board_num_list", required = false) List<String> board_num_list, Model model, BoardDTO boardDTO) {
        boolean flag = true;

        if (board_num_list != null) {
            // TODO 아마도 수정 필요 ..
            for (String data : board_num_list) {

                boardDTO.setBoard_num(Integer.parseInt(data));
                boolean deleteSuccess = this.boardService.delete(boardDTO);
                if (!deleteSuccess) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        model.addAttribute("title", "글 삭제");
        model.addAttribute("msg", flag ? "선택된 글 삭제 성공!" : "글 삭제 실패..");
        model.addAttribute("path", "boardManagement.do");

//        //TODO 일괄삭제기능도 포함
//        //	트랜잭션
        return "views/info";
    }

    @GetMapping("/boardManagementDetail.do")
    public String boardManagementDetail(ReplyDTO replyDTO, BoardDTO boardDTO, Model model) {

        //TODO VIEW와 생각해보기 V는 LIST로 안 했기 때문에 ..
        List<BoardDTO> board_datas = boardService.selectAll(boardDTO);
        model.addAttribute("board_datas", board_datas);
        List<ReplyDTO> reply_datas = replyService.selectAll(replyDTO);
        model.addAttribute("REPLY", reply_datas);

        return "admin/boardManagementDetail";
    }

    @PostMapping("/boardManagementDetail.do")
    public String boardManagementDetail(Model model, ReplyDTO replyDTO) {

        boolean flag = false;
        flag = this.replyService.delete(replyDTO);
        model.addAttribute("title", "댓글 삭제");
        if (flag) {
            model.addAttribute("msg", "댓글 삭제 성공!");
        }
        else{
            model.addAttribute("msg","댓글 삭제 실패..");
        }
        model.addAttribute("path", "boardManagementDetail.do");

        return "views/info";
    }
}