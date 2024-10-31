package com.coma.app.view.payment;

import com.coma.app.biz.reservation.PaymentInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenService {

    private final static String IMP_KEY = "key";
    private final static String IMP_SECRET = "secret";

    // 토큰 발급 요청
    public static PaymentInfo portOne_code() {
        PaymentInfo paymentInfo = new PaymentInfo();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/users/getToken"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"imp_key\":\"" + IMP_KEY + "\",\"imp_secret\":\"" + IMP_SECRET + "\"}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("토큰 요청 실패");
            e.printStackTrace();
        }
        System.out.println("response.body() = ["+response.body()+"]");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response.body());
        } catch (ParseException e) {
            System.out.println("json 변환 실패");
            e.printStackTrace();
        }
        JSONObject responseObject = (JSONObject) jsonObject.get("response");
        String token = (String)responseObject.get("access_token");
        paymentInfo.setToken(token);
        return paymentInfo;
    }
}
