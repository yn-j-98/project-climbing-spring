package com.coma.app.biz.reservation;

import java.net.http.HttpResponse;

public class PaymentInfo {
    private String token;
    private String imp_uid;
    private String merchant_uid;
    private int amount;
    private HttpResponse<String> response;


    public HttpResponse<String> getResponse() {
        return response;
    }
    public void setResponse(HttpResponse<String> response) {
        this.response = response;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getImp_uid() {
        return imp_uid;
    }
    public void setImp_uid(String imp_uid) {
        this.imp_uid = imp_uid;
    }
    public String getMerchant_uid() {
        return merchant_uid;
    }
    public void setMerchant_uid(String merchant_uid) {
        this.merchant_uid = merchant_uid;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }


}
