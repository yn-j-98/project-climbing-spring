package com.coma.app.biz.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.http.HttpResponse;

@Getter
@Setter
@ToString
public class PaymentInfoDTO {
    private String token;
    private String imp_uid;
    private String merchant_uid;
    private int amount;
    private HttpResponse<String> response;
}
