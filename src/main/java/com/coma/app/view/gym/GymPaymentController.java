package com.coma.app.view.gym;

import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class GymPaymentController {
    @Autowired
    private HttpSession session;

    @LoginCheck
    @RequestMapping("/paymentSuccess.do")
    public String PaymentDo(Model model) {
        // 예약
        log.info("paymentSuccess.do 시작");

        model.addAttribute("title","성공");
        model.addAttribute("msg","성공적으로 예약되었습니다");
        model.addAttribute("path","main.do");

        System.out.println("MakeReservationAction 끝");
        return "views/info";
    }
}

