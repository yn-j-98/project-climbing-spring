package com.coma.app.view.crew.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrewCommunityPageController {

    @GetMapping("/crewCommunity.do")
    public String showCrewCommunityPage() {
        return "views/crewCommunity"; // View 이름 반환
    }
}