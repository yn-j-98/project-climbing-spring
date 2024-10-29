package com.coma.app.view.crew.community;

import com.coma.app.biz.crew_board.Crew_boardDTO;
import com.coma.app.biz.crew_board.Crew_boardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CrewCommunityPageController {

    @Autowired
    private Crew_boardService crewBoardService;

    @GetMapping("/crewCommunity.do")
    public String getCrewCommunity(HttpSession session, Model model) {
        int crewNum = (int) session.getAttribute("CREW_CHECK");

        // Crew_boardDTO 인스턴스 생성 및 크루 번호 설정
        Crew_boardDTO crewBoardDTO = new Crew_boardDTO();
        crewBoardDTO.setCrew_num(crewNum);

        List<Crew_boardDTO> messages = crewBoardService.selectAll(crewBoardDTO);
        model.addAttribute("messages", messages);
        return "views/crewCommunity"; // JSP 파일 이름
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/crew/{crewNum}")
    public Crew_boardDTO sendMessage(@Payload Crew_boardDTO message, HttpSession session) {
        String memberId = (String) session.getAttribute("MEMBER_ID");

        // 사용자 정보 설정
        message.setCrew_board_writer_id(memberId);
        message.setCrew_board_writer_profile((String) session.getAttribute("MEMBER_PROFILE"));
        message.setCrew_board_writer_name((String) session.getAttribute("MEMBER_NAME"));

        // 메시지를 데이터베이스에 저장
        crewBoardService.insert(message);

        return message;
    }
}