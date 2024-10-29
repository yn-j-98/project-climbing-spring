package com.coma.app.view.admin;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrewAsyncController {

    @PostMapping("/crewName.do")
    public String crewName() {
        return null;
    }

    @PostMapping("/mvpMember.do")
    public String mvpMember() {
        return null;
    }
}
