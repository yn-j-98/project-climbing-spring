package com.coma.app.view.payment;

import com.coma.app.biz.reservation.PaymentInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class TokenService {

    private final static String IMP_KEY = "";//포트원 IMP : 확인 키
    private final static String IMP_SECRET = "";//포트원 IMP : 암호화 키

    // 토큰 발급 요청
    public static PaymentInfoDTO portOne_code() {
        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/users/getToken"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"imp_key\":\"" + IMP_KEY + "\",\"imp_secret\":\"" + IMP_SECRET + "\"}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info("토큰 요청 실패");
            e.printStackTrace();
        }
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response.body());
        } catch (ParseException e) {
            log.info("json 변환 실패");
            e.printStackTrace();
        }
        JSONObject responseObject = (JSONObject) jsonObject.get("response");
        String token = (String)responseObject.get("access_token");
        paymentInfoDTO.setToken(token);
        return paymentInfoDTO;
    }
}
