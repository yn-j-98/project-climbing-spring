package com.coma.app.view.payment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.coma.app.biz.reservation.PaymentInfoDTO;

@Slf4j
public class PaymentPortOne {
    // 결제 단건 조회
    public static PaymentInfoDTO paymentSearchOne(PaymentInfoDTO paymentInfoDTO) {
        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/payments/" + paymentInfoDTO.getImp_uid()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + paymentInfoDTO.getToken())
                .GET() // GET 요청
                .build();

        HttpResponse<String> response = null;
        // HTTP 요청 보내기 및 응답 받기
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            paymentInfoDTO.setResponse(response);
            if (response != null) {
                log.info("response != null");
            }
            else {
                log.info("response == null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paymentInfoDTO;
    }

    // 사전 검증 등록
    public static boolean prepare(PaymentInfoDTO paymentInfoDTO) {
        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/payments/prepare"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + paymentInfoDTO.getToken())
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"merchant_uid\":\"" + paymentInfoDTO.getMerchant_uid() + "\",\"amount\":" + paymentInfoDTO.getAmount() + "}"))
                .build();
        HttpResponse<String> response = null;
        // HTTP 요청 보내기 및 응답 받기
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        log.info("사전등록 = [{}]",response.body());
        return true;
    }

    // 결제 복수조회
    public static String paymentSearchAll(PaymentInfoDTO paymentInfoDTO) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/payments"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException: " + e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "InterruptedException: " + e.getMessage();
        }
        // 응답 본문(response.body())를 반환
        String searchAll = response.body();
        return searchAll;
    }


    // 사전 검증 조회
    public static PaymentInfoDTO prepareResult(PaymentInfoDTO paymentInfoDTO) {
        // HTTP 요청 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/payments/prepare/"+ paymentInfoDTO.getMerchant_uid()+"?_token="+ paymentInfoDTO.getToken()))
                .header("Content-Type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.ofString("{}"))
                .build();
        HttpResponse<String> response = null;
        // HTTP 요청 보내기 및 응답 받기
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("IOException occurred while sending request", e);
            return null;
        } catch (InterruptedException e) {
            log.error("ParseException occurred while parsing response body", e);
            return null;
        }

        JSONParser parser = new JSONParser();
        // 금액은 -가 나올 수 없는 값이어서 -1로 변수 초기화(유효성 검사 강화)
        int amount_result = -1;
        try {
            log.info("response = [{}]",response.body());
            JSONObject jsonObject = (JSONObject) parser.parse(response.body());
            JSONObject responseObject = (JSONObject) jsonObject.get("response");

            if(responseObject != null) {
                Long amount = (Long) responseObject.get("amount");
                amount_result = amount.intValue();
                paymentInfoDTO.setAmount(amount_result);
            }
            else {
                log.info("Response 객체가 null입니다.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("사전등록조회 = [{}]",response.body());
        return paymentInfoDTO;
    }

    // 결제 취소
    public static boolean cancelPayment(PaymentInfoDTO paymentInfoDTO) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iamport.kr/payments/cancel?_token="+ paymentInfoDTO.getToken()))
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer "+paymentInfo.getToken())
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"merchant_uid\":\"" + paymentInfoDTO.getMerchant_uid() + "\",\"checksum\":" + paymentInfoDTO.getAmount() + "}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        log.info("response = [{}]",response.body());
        return true;
    }

    //	public static void paymentTest(String token, String imp_uid) {
    //		HttpRequest request = HttpRequest.newBuilder()
    //				.uri(URI.create("https://api.iamport.kr/payments/"+imp_uid))
    //				.header("Content-Type", "application/json")
    //				.header("Authorization", "Bearer "+token)
    //				.method("GET", HttpRequest.BodyPublishers.ofString("{}"))
    //				.build();
    //		HttpResponse<String> response = null;
    //		try {
    //			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    //			System.out.println(response.body());
    //		} catch (IOException | InterruptedException e) {
    //			// TODO Auto-generated catch block
    //			e.printStackTrace();
    //		}
    //		String jsonString = response.body(); // 응답 본문에서 JSON 문자열을 추출
    //
    //		// JSON 파서 및 파싱
    //		JSONParser parser = new JSONParser();
    //		JSONObject jsonObject = null;
    //		try {
    //			jsonObject = (JSONObject) parser.parse(jsonString);
    //		} catch (ParseException e) {
    //			// TODO Auto-generated catch block
    //			e.printStackTrace();
    //		}
    //
    //		// 'response' 객체 가져오기
    //		JSONObject responseObject = (JSONObject) jsonObject.get("response");
    //
    //		// 필요한 값 추출
    //		long amount = (long) responseObject.get("amount");
    //		String buyerName = (String) responseObject.get("buyer_name");
    //		String receiptUrl = (String) responseObject.get("receipt_url");
    //		String cardName = (String) responseObject.get("card_name");
    //
    //		// 출력
    //		System.out.println("Amount: " + amount);
    //		System.out.println("Buyer Name: " + buyerName);
    //		System.out.println("Receipt URL: " + receiptUrl);
    //		System.out.println("Card Name: " + cardName);
    //
    //}
    //
    //
    //	public static void main(String[] args) {
    //		String token = Test.portOne_code();
    //		System.out.println("portone token : "+token);
    //		//		Test.test(token);
    //
    //	}
}

