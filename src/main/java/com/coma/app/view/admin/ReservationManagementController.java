package com.coma.app.view.admin;


import com.coma.app.biz.reservation.PaymentInfoDTO;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.annotation.AdminCheck;
import com.coma.app.view.payment.PaymentPortOne;
import com.coma.app.view.payment.TokenService;
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

    @AdminCheck
    @GetMapping("/reservationManagement.do")
    // 예약 관리
    public String reservationManagement(Model model, ReservationDTO reservationDTO) {
        log.info("reservationManagement 시작");
        log.info("reservationDTO = [{}]",reservationDTO);

        // 페이지네이션 페이지 데이터
        int pageNum = reservationDTO.getPage();
        log.info("pageNum = [{}]",pageNum);

        int size = 10; // 한 페이지에 표시할 게시글 수 설정
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
        String search_keyword = reservationDTO.getSearch_keyword();
        if(search_keyword==null){
            log.info("처음 화면");
            reservationCount = this.reservationService.selectOneCountAdmin(reservationDTO);
            datas = this.reservationService.selectAllAdmin(reservationDTO);
        }

        else if(search_keyword.equals("RESERVATION_MEMBER_ID")) { // 예약자로 찾기
            log.info("Search_keyword = 예약자 = RESERVATION_MEMBER_ID");
            datas = this.reservationService.selectAllAdminSearchMemberId(reservationDTO);
            log.info("datas = [{}]",datas);
            reservationCount = this.reservationService.selectOneCountSearchMemberIdAdmin(reservationDTO);
            log.info("reservationCount = [{}]",reservationCount);
        }

        else if(search_keyword.equals("RESERVATION_GYM_NUM")) { // 암벽장 번호로 찾기
            log.info("Search_keyword = 암벽장 번호 = RESERVATION_GYM_NUM");
            datas = this.reservationService.selectAllAdminSearchGymName(reservationDTO);
            reservationCount = this.reservationService.selectOneCountSearchGymNameAdmin(reservationDTO);
        }

        else if(search_keyword.equals("")) {
            return null;
        }
        listNum = reservationCount.getTotal();

        // 검색어 저장
        String search_content = reservationDTO.getSearch_content();

        // 클라이언트에 보낼 값
        model.addAttribute("datas", datas);
        model.addAttribute("total", listNum);
        model.addAttribute("page", pageNum);
        model.addAttribute("search_keyword", search_keyword);
        model.addAttribute("search_content", search_content);


        return "admin/reservationManagement";
    }

    // 예약 삭제
    @AdminCheck
    @PostMapping("/reservationManagement.do")
    public String reservationDeleteManagement(Model model, ReservationDTO reservationDTO) {
        log.info("reservationManagement.do POST 도착");

        model.addAttribute("title","실패");
        model.addAttribute("msg","예약 취소를 실패했습니다");
        model.addAttribute("path","reservationManagement.do");

        // front에서 받아온 reservation_num으로 db데이터 가져옴
        ReservationDTO reservation_data = this.reservationService.selectOne(reservationDTO);
        log.info("reservation_data = [{}]",reservation_data);

        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();

        // 포트원에서 조회할 고유식별번호
        String merchant_uid=reservationDTO.getReservation_num();
        log.info("merchant_uid = [{}]",merchant_uid);

        // 고유식별번호 set
        paymentInfoDTO.setMerchant_uid(merchant_uid);

        // 토큰 발급
        paymentInfoDTO = TokenService.portOne_code();
        paymentInfoDTO.setMerchant_uid(merchant_uid);

        // 비교할 가격 set
        log.info("reservation_price = [{}]",reservation_data.getReservation_price());
        paymentInfoDTO.setAmount(reservation_data.getReservation_price());

        // 포트원 환불 진행
        boolean flag = PaymentPortOne.cancelPayment(paymentInfoDTO);
        log.info("포트원 환불 여부 =[{}]",flag);

        if(flag) { // 환불 성공
            flag =  this.reservationService.delete(reservationDTO);
            if(flag) { // db삭제 실패
                model.addAttribute("title","성공");
                model.addAttribute("msg","예약취소를 성공했습니다");
            }
        }

        return "views/info";

    }
}
