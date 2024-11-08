package com.coma.app.view.admin;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberServiceImpl;
import com.coma.app.biz.reply.ReplyDTO;
import com.coma.app.biz.reply.ReplyServiceImpl;
import com.coma.app.view.annotation.AdminCheck;
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

    @AdminCheck
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
        // 검색 조건에 따라 게시글 목록 조회
        if(boardDTO.getSearch_content() == null){
            list_num = boardService.selectOneCount(boardDTO).getTotal();
            datas = boardService.selectAll(boardDTO);
        }
        else if("BOARD_WRITER_ID".equals(search_keyword)){ // ID
            list_num = boardService.selectOneSearchIdCount(boardDTO).getTotal();
            datas = boardService.selectAllSearchPatternId(boardDTO);
        }
        else if("BOARD_TITLE".equals(search_keyword)){ // 제목
            list_num = boardService.selectOneSearchTitleCount(boardDTO).getTotal();
            datas = boardService.selectAllSearchTitle(boardDTO);
        }

        log.info("boardManagement min_num = [{}], page=[{}], total=[{}]", min_num,page,list_num);

        //-----------------------------------------------------------------------------

        model.addAttribute("total", list_num);
        model.addAttribute("page", page);
        model.addAttribute("search_keyword", search_keyword);
        model.addAttribute("search_content", boardDTO.getSearch_content());
        model.addAttribute("datas", datas);

        return "admin/boardManagement";
    }
    // 게시판 관리
    @AdminCheck
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

    @AdminCheck
    @GetMapping("/boardManagementDetail.do")
    public String boardManagementDetail(ReplyDTO replyDTO, BoardDTO boardDTO, MemberDTO memberDTO, Model model) {
        int board_num = boardDTO.getBoard_num();
        log.info("boardManagementDetail.board_num = [{}]",board_num);

        //게시판 상세보기
        BoardDTO board_data = boardService.selectOne(boardDTO);
        log.info("board_data = [{}]",board_data);

        //사용자 이미지, 아이디
        memberDTO.setMember_id(board_data.getBoard_writer_id());
        MemberDTO profile = memberService.selectOneSearchId(memberDTO);
        log.info("profile = [{}]",profile);

        model.addAttribute("board_title",board_data.getBoard_title());
        model.addAttribute("board_content",board_data.getBoard_content());
        model.addAttribute("board_writer_profile",profile.getMember_profile());
        model.addAttribute("board_writer_id",board_data.getBoard_writer_id());

        //해당 게시글의 댓글 목록
        replyDTO.setReply_board_num(board_num);
        List<ReplyDTO> reply_datas = replyService.selectAll(replyDTO);
        log.info("reply_datas = [{}]",reply_datas);
        model.addAttribute("REPLY", reply_datas);

        return "admin/boardManagementDetail";
    }

    @AdminCheck
    @PostMapping("/boardManagementDetail.do")
    public String boardManagementDetail(Model model, ReplyDTO replyDTO) {
        log.info("PostMapping.boardManagementDetail 시작");
        boolean flag = false;
        flag = this.replyService.delete(replyDTO);
        model.addAttribute("title", "댓글 삭제");
        if (flag) {
            model.addAttribute("msg", "댓글 삭제 성공!");
            log.info("댓글 삭제 성공!");
        }
        else{
            model.addAttribute("msg","댓글 삭제 실패..");
            log.info("댓글 삭제 실패..");
        }
        model.addAttribute("path", "boardManagementDetail.do?board_num="+replyDTO.getReply_board_num());

        return "views/info";
    }
}