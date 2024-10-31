package com.coma.app.view.admin;


import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Slf4j
@Controller
public class ReservationManagementController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservationManagement.do")
    // 예약 관리
    public String reservationManagement(Model model, ReservationDTO reservationDTO) {
        log.info("reservationManagement 시작");
        log.info("reservationDTO = [{}]",reservationDTO);

        // 페이지네이션 페이지 데이터
        int pageNum = reservationDTO.getPage();
        log.info("pageNum = [{}]",pageNum);

        int size = 6; // 한 페이지에 표시할 게시글 수 설정
        int minNum = 0; // 최소 게시글 수 초기화

        if (pageNum <= 0) { // 페이지가 0일 때 (npe방지)
            pageNum = 1;
        }
        minNum = ((pageNum - 1) * size); // 최소 게시글 번호 계산
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

        reservationDTO.setReservation_min_num(minNum);

        ReservationDTO reservationCount = null;

        //=============페이지네이션=================

        List<ReservationDTO> datas = null;

        if(reservationDTO.getSearch_keyword()==null){
            log.info("처음 화면");
            reservationCount = this.reservationService.selectOneCountAdmin(reservationDTO);
            datas = this.reservationService.selectAllAdmin(reservationDTO);
        }

        else if(reservationDTO.getSearch_keyword().equals("RESERVATION_MEMBER_ID")) {
            log.info("Search_keyword = 예약자 = RESERVATION_MEMBER_ID");
            datas = this.reservationService.selectAllAdminSearchMemberId(reservationDTO);
            reservationCount = this.reservationService.selectOneCountSearchMemberIdAdmin(reservationDTO);
        }

        else if(reservationDTO.getSearch_keyword().equals("RESERVATION_GYM_NUM")) {
            log.info("Search_keyword = 암벽장 번호 = RESERVATION_GYM_NUM");
            datas = this.reservationService.selectAllAdminSearchGymName(reservationDTO);
            reservationCount = this.reservationService.selectOneCountSearchGymNameAdmin(reservationDTO);
        }

        else if(reservationDTO.getSearch_keyword().equals("")) {
            return null;
        }
        listNum = reservationCount.getTotal();
        // 예약자명
        //      예약한 암벽장 이름
        //      결제한 금액
        //      예약한 날짜

        model.addAttribute("datas", datas);
        model.addAttribute("total", listNum);
        model.addAttribute("page", pageNum);


        return "admin/reservationManagement";
    }

    // 예약 삭제
    @PostMapping("/reservationManagement.do")
    public String reservationDeleteManagement(Model model, ReservationDTO reservationDTO) {
        log.info("reservationDeleteManagement 시작");

        // 경로 설정
        String path = "reservationManagement.do";

        // 예약 삭제
        boolean flag=reservationService.delete(reservationDTO);
        log.info("delete 결과 = [{}]",flag);

        // 스위트알랏 설정
        String title="삭제 성공";
        String msg="예약 삭제 성공 하였습니다.";

        // 삭제 못하면
        if(!flag) {
            title="삭제 패";
            msg="예약 삭제 실패 하였습니다.";
        }

        model.addAttribute("title",title);
        model.addAttribute("msg",msg);
        model.addAttribute("path",path);
        return "views/info";
    }
}
