package com.coma.app.view.admin;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberServiceImpl;
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
    @Autowired
    private MemberServiceImpl memberService;

    @LoginCheck
    @GetMapping("/boardManagement.do")
    public String boardManagement(Model model,BoardDTO boardDTO) {

        //-----------------------------------------------------------------------------
        // 페이지네이션
        String search_keyword = boardDTO.getSearch_keyword();
        int page = boardDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;

        boardDTO.setBoard_min_num(min_num);
        List<BoardDTO> datas = null;
        int list_num = 0;
        if(boardDTO.getSearch_content() == null){
            list_num = boardService.selectOneCount(boardDTO).getTotal();
            datas = boardService.selectAll(boardDTO);
        }
        else if("BOARD_WRITER_ID".equals(search_keyword)){
            list_num = boardService.selectOneSearchIdCount(boardDTO).getTotal();
            datas = boardService.selectAllSearchPatternId(boardDTO);
        }
        else if("BOARD_TITLE".equals(search_keyword)){
            list_num = boardService.selectOneSearchTitleCount(boardDTO).getTotal();
            datas = boardService.selectAllSearchTitle(boardDTO);
        }

        log.info("boardManagement min_num = {}, page={}, total={}", min_num,page,list_num);

        //-----------------------------------------------------------------------------

        model.addAttribute("total", list_num);
        model.addAttribute("page", page);
        model.addAttribute("search_keyword", search_keyword);
        model.addAttribute("search_content", boardDTO.getSearch_content());
        model.addAttribute("datas", datas);

        return "admin/boardManagement";
    }
    // 게시판 관리
    @PostMapping("/boardManagement.do")
    public String boardManagement(@RequestParam(value = "board_num_list", required = false) List<Integer> board_num_list, Model model, BoardDTO boardDTO) {
        boolean flag = true;

        if (board_num_list != null) {
            log.info("board_num_list : [{}]",board_num_list);
            for (int data : board_num_list) {
                log.info("board_num_list data : [{}]",data);
                boardDTO.setBoard_num(data);
                boolean deleteSuccess = this.boardService.deleteSelectedBoard(boardDTO);
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
        return "views/info";
    }

    @GetMapping("/boardManagementDetail.do")
    public String boardManagementDetail(ReplyDTO replyDTO, BoardDTO boardDTO, MemberDTO memberDTO, Model model) {

        //TODO VIEW와 생각해보기 V는 LIST로 안 했기 때문에 ..
        BoardDTO board_data = boardService.selectOne(boardDTO);

        memberDTO.setMember_id(board_data.getBoard_writer_id());
        MemberDTO profile = memberService.selectOneSearchId(memberDTO);

        model.addAttribute("board_title",board_data.getBoard_title());
        model.addAttribute("board_content",board_data.getBoard_content());
        model.addAttribute("board_writer_profile",profile.getMember_profile());
        model.addAttribute("board_writer_id",board_data.getBoard_writer_id());

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