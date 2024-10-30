package com.coma.app.view.asycnServlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.coma.app.biz.reservation.ReservationDAO;
import com.coma.app.biz.reservation.ReservationDTO;
import com.coma.app.biz.reservation.ReservationService;
import com.coma.app.view.payment.PaymentUtil;
import com.coma.app.view.payment.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.coma.app.biz.reservation.PaymentInfo;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Payment {

    private ReservationService reservationService;

    @RequestMapping(value = "paymentPrepare.do", method = RequestMethod.POST)
    public void paymentPrepare (HttpServletRequest request, HttpServletResponse response) throws IOException {

        String merchant_uid = request.getParameter("merchant_uid");
        int reservation_price = Integer.parseInt(request.getParameter("reservation_price"));
        System.out.println(merchant_uid);
        System.out.println(reservation_price);

        // 토큰 발행
        PaymentInfo paymentInfo = TokenService.portOne_code();
        System.out.println("portone token : "+paymentInfo.getToken());

        paymentInfo.setMerchant_uid(merchant_uid);
        paymentInfo.setAmount(reservation_price);

        boolean flag = PaymentUtil.prepare(paymentInfo);

        PrintWriter out = response.getWriter();
        if(flag) {
            out.print("true");
        }
        else {
            out.print("false");
        }
    }

    @RequestMapping(value = "paymentPrepared.do", method = RequestMethod.POST)
    public void paymentPrepared (HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("PaymentPrepared : POST 요청 도착");
        String merchant_uid = request.getParameter("merchant_uid");
        System.out.println(merchant_uid);

        // 토큰 발행
        PaymentInfo paymentInfo = TokenService.portOne_code();
        System.out.println("portone token : "+paymentInfo.getToken());

        paymentInfo.setMerchant_uid(merchant_uid);

        paymentInfo = PaymentUtil.prepareResult(paymentInfo);

        response.setContentType("application/json"); // JSON으로 응답 설정
        PrintWriter out = response.getWriter();

        //System.out.println(paymentInfo.getAmount());
        String jsonResponse;

        if (paymentInfo.getAmount() > 0) {
            jsonResponse = "{\"amountRes\": " + paymentInfo.getAmount() + "}";
            System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
        } else {
            jsonResponse = "{\"amountRes\": 0}"; // JSON 형식으로 0 반환
            System.err.println("JSON 응답: " + jsonResponse); // JSON 응답을 로그에 출력
        }

        out.print(jsonResponse);
        out.flush();

    }

    @RequestMapping(value = "paymentValidate.do", method = RequestMethod.POST)
    public void paymentValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("POST 요청 도착");
        String imp_uid = request.getParameter("imp_uid");
        int product_num = Integer.parseInt(request.getParameter("product_num"));


        PaymentInfo paymentInfo = TokenService.portOne_code();
        System.out.println("portone token : "+paymentInfo.getToken());
        System.out.println(imp_uid);

        paymentInfo.setImp_uid(imp_uid);

        paymentInfo = PaymentUtil.paymentSearchOne(paymentInfo);
        String responseBody = paymentInfo.getResponse().body();
        System.out.println("응답 객체: " + responseBody);
        // JSON 파싱
        JSONParser parser = new JSONParser();
        ReservationDTO reservationDTO= null;
        boolean flag = false;
        int amount_result = 0;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(paymentInfo.getResponse().body());

            // 'response' 객체 가져오기
            JSONObject responseObject = (JSONObject) jsonObject.get("response");

            if (responseObject != null) {
                // 필요한 값 추출
                System.out.println(product_num);
                Long amount = (Long) responseObject.get("amount");
                amount_result = amount.intValue(); // 결제 금액
                String imp_uid1 = (String) responseObject.get("imp_uid");
                String merchant_uid = (String) responseObject.get("merchant_uid");
                String reservation_date = (String) responseObject.get("reservationDate");
                int gym_num = (Integer) responseObject.get("gym_num");
                String member_id = (String) responseObject.get("member_id");

                // 결과 출력
                System.out.println("결제된 가격: " + amount_result);
                System.out.println("결제 고유번호: " + imp_uid1);
                System.out.println("상점 고유번호: " + merchant_uid);

                reservationDTO = new ReservationDTO();
                reservationDTO.setReservation_num(merchant_uid);
                reservationDTO.setReservation_date(reservation_date);
                reservationDTO.setReservation_gym_num(gym_num);
                reservationDTO.setReservation_member_id(member_id);
                reservationDTO.setReservation_price(amount_result);

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