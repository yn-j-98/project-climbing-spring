package com.coma.app.view.grigri;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GriGri {

    @GetMapping("/grigri.do")
    public  String griGri() {
        return "views/grigri";
    }
}
