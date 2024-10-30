package com.coma.app.view.admin;


import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReservationManagementController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservationManagement.do")
    // 예약 관리
    public String reservationManagement(Model model, ReservationDTO reservationDTO) {

      /*

      예약자명
      예약한 암벽장 이름
      결제한 금액
      예약한 날짜
      ↑ SELECTALL

      페이지네이션

       */


        int pageNum = reservationDTO.getPage();//요거 필요
        int size = 6; // 한 페이지에 표시할 게시글 수 설정
        int minNum = 0; // 최소 게시글 수 초기화

        if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
            pageNum = 1;
        }
        minNum = ((pageNum - 1) * size); // 최소 게시글 번호 계산
        int listNum = reservationDTO.getTotal(); // 게시글 총 개수를 저장할 변수 초기화


        reservationDTO.setReservation_min_num(minNum);

        //ReservationDTO reservationCount = this.reservationService.selectOneAdminCount(reservationDTO);

        //listNum = reservationCount.getTotal();

        //=============페이지네이션=================

        List<ReservationDTO> datas = this.reservationService.selectAllAdmin(reservationDTO);
        // 예약자명
        //      예약한 암벽장 이름
        //      결제한 금액
        //      예약한 날짜

        model.addAttribute("datas", datas);
        model.addAttribute("total", listNum);
        model.addAttribute("page", pageNum);


        return "admin/reservationManagement";
    }


}
