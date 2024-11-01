package com.coma.app.view.asycnServlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.payment.PaymentPortOne;
import com.coma.app.view.payment.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.coma.app.biz.reservation.PaymentInfoDTO;

@Slf4j
@RestController
public class Payment {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/paymentPrepare.do")
    public void paymentPrepare (HttpServletResponse response, @RequestBody PaymentInfoDTO paymentInfoDTO) throws IOException {
        log.info("사전 등록 POST paymentPrepare.do 도착");

        //생성한 식별코드와 금액 저장
        String merchant_uid = paymentInfoDTO.getMerchant_uid();
        int reservation_price = paymentInfoDTO.getAmount();
        log.info("merchant_uid = [{}]", paymentInfoDTO.getMerchant_uid());
        log.info("reservation_price = [{}]", paymentInfoDTO.getAmount());

        // 토큰 발행
        paymentInfoDTO = TokenService.portOne_code();
        log.info("portone token = [{}] ", paymentInfoDTO.getToken());

        //저장한 금액과 식별코드를 DTO에 set
        paymentInfoDTO.setMerchant_uid(merchant_uid);
        paymentInfoDTO.setAmount(reservation_price);

        // 사전 등록
        boolean flag = PaymentPortOne.prepare(paymentInfoDTO);

        // 클라이언트에게 성공여부 전달
        PrintWriter out = response.getWriter();
        if(flag) {
            out.print("true");
        }
        else {
            out.print("false");
        }
    }


    @PostMapping(value = "/paymentPrepared.do")
    public void paymentPrepared (HttpServletResponse response, @RequestBody PaymentInfoDTO paymentInfoDTO) throws IOException {
        log.info("사전 등록 조회 POST PaymentPrepared.do");

        //생성한 식별코드 저장
        String merchant_uid = paymentInfoDTO.getMerchant_uid();
        log.info("merchant_uid = [{}]",merchant_uid);

        // 토큰 발행
        paymentInfoDTO = TokenService.portOne_code();
        log.info("portone token = [{}]",paymentInfoDTO.getToken());


        // 저장한 식별코드 DTO에 set
        paymentInfoDTO.setMerchant_uid(merchant_uid);

        // 사전 등록 조회
        paymentInfoDTO = PaymentPortOne.prepareResult(paymentInfoDTO);
        log.info("DTO 사전등록 = [{}]",paymentInfoDTO);

        response.setContentType("application/json"); // JSON으로 응답 설정
        PrintWriter out = response.getWriter();


        String jsonResponse;

        if (paymentInfoDTO.getAmount() > 0) {
            jsonResponse = "{\"amountRes\": " + paymentInfoDTO.getAmount() + "}";
            log.info("JSON 응답 = [{}]",jsonResponse); // JSON 응답을 로그에 출력
        } else {
            jsonResponse = "{\"amountRes\": 0}"; // JSON 형식으로 0 반환
            log.info("JSON 응답 = [{}]",jsonResponse); // JSON 응답을 로그에 출력
        }

        out.print(jsonResponse);
        out.flush();

    }


    @PostMapping(value = "/paymentValidate.do")
    public void paymentValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("결제된 후 결제 내역 조회 POST paymentValidate.do");

        // 바인딩할 커맨드객체에 gym_num이 없어서 request사용
        String imp_uid = request.getParameter("imp_uid");
        String reservation_date = request.getParameter("reservation_date");
        int product_num = Integer.parseInt(request.getParameter("gym_num"));
        log.info("imp_uid = [{}]",imp_uid);
        log.info("product_num = [{}]",product_num);
        log.info("reservation_date = [{}]",reservation_date);

        // uid 값 담을 DTO 생성
        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();
        paymentInfoDTO.setImp_uid(imp_uid);

        // 토큰 발급
        paymentInfoDTO = TokenService.portOne_code();
        log.info("portone token = [{}]",paymentInfoDTO.getToken());

        // uid 다시 담아줌
        paymentInfoDTO.setImp_uid(imp_uid);
        log.info("imp_uid = [{}]",imp_uid);

        // 단건 조회
        paymentInfoDTO = PaymentPortOne.paymentSearchOne(paymentInfoDTO);
        String responseBody = paymentInfoDTO.getResponse().body();
        System.out.println("응답 객체: " + responseBody);
        // JSON 파싱
        JSONParser parser = new JSONParser();
        ReservationDTO reservationDTO= null;
        boolean flag = false;
        int amount_result = 0;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(paymentInfoDTO.getResponse().body());

            // 'response' 객체 가져오기
            JSONObject responseObject = (JSONObject) jsonObject.get("response");
            System.out.println("139"+responseObject);
            if (responseObject != null) {
                // 필요한 값 추출
                Long amount = (Long) responseObject.get("amount");
                amount_result = amount.intValue(); // 결제 금액
                String imp_uid1 = (String) responseObject.get("imp_uid");
                String merchant_uid = (String) responseObject.get("merchant_uid");
                String member_id = (String) responseObject.get("buyer_email");

                // 결과 출력
//                log.info("결제된 가격 = [{}]",amount_result);
                System.out.println("결제된 가격 = ["+amount_result+"]");
//                log.info("결제 고유번호 = [{}]",imp_uid1);
                System.out.println("결제 고유번호 = ["+imp_uid1+"]");
//                log.info("결제 날짜 = [{}]",reservation_date);
                System.out.println("결제 날짜 = ["+reservation_date+"]");
//                log.info("상점 고유번호 = [{}]",merchant_uid);
                System.out.println("상점 고유번호 = ["+merchant_uid+"]");
//                log.info("결제자 아이디 = [{}]",member_id);
                System.out.println("결제자 아이디 = ["+member_id+"]");

                int reservation_gym_num=product_num;
                // 조회한 결제 내역 DB에 저장
                reservationDTO = new ReservationDTO();
                reservationDTO.setReservation_num(merchant_uid);
                reservationDTO.setReservation_date(reservation_date);
                reservationDTO.setReservation_gym_num(reservation_gym_num);
                reservationDTO.setReservation_member_id(member_id);
                reservationDTO.setReservation_price(amount_result);
                System.out.println("170도착 reservationDTO = ["+reservationDTO+"]");
                flag = reservationService.insert(reservationDTO);
            } else {
                System.out.println("Response 객체가 null입니다.");
                flag = false;
            }
        } catch (ParseException e) {
            System.err.println("JSON 파싱 오류: " + e.getMessage());
        }
        PrintWriter out = response.getWriter();
        if(flag) {
            out.print(amount_result);
        }
        else {
            out.print(flag);
        }

    }

}