package com.coma.app.view.asycnServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.coma.app.view.function.SMSPush;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/smscheck")
public class SMSCheckAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SMSCheckAction() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//인증번호 최대 길이
		int citationMax = 8;
		
		//인증 번호 생성 함수 호출
		String code = verificationCode(citationMax);
		
		//인증 문자 발송 View 에서 전달해준 값으로 인증번호를 생성해서 전달합니다.
		boolean flag = SMSPush.smsSend(request.getParameter("member_phoneNumber"), code);
		
		if(flag) {
			//view 로 문자 전달
			out.print(code);
			//전달된 인증번호 확인용
			System.out.println("문자발송 로그 성공 : " + code);
		}
		else {
			//flag 가 false 라면 false 를 반환합니다.
			System.err.println("SMSCheckAction flag false로그 문자발송 실패");
//			out.print(code);
			System.err.println("문자발송 로그 실패 : "+code);
			out.print(flag);			
		}
		
	}
	
	private String verificationCode(int citationMax) {
		Random random = new Random();
		String checkSms = "";
		//인증번호 최대 길이 만큼 값을 전달합니다.
		for (int i = 0; i < citationMax; i++) {
			//영문 생성 random 함수
			int encher = random.nextInt(25)+65;
			//숫자 생성 random 함수
			int num = random.nextInt(10);
			// 숫자 문자 랜덤 문자를 발생하기 위한 random 함수
			// 50% 확율로 true 면 영어 false 면 숫자를 생성하여
			// 인증번호를 생성
			if(random.nextBoolean()){
				checkSms += (char) encher;
			}
			else {
				checkSms += num;
			}
		}
		
		//생성된 인증번호 반환
		return checkSms;
	}

}
