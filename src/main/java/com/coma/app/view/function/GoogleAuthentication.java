package com.coma.app.view.function;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

// GoogleAuthentication 클래스를 정의합니다. 이 클래스는 메일 서버 인증을 처리합니다.
public class GoogleAuthentication extends Authenticator {
    // PasswordAuthentication 객체를 선언합니다.
    PasswordAuthentication passAuth;

    // 생성자 메서드입니다. GoogleAuthentication 객체가 생성될 때 호출됩니다.
    public GoogleAuthentication() {
        // 사용자 이메일 주소와 애플리케이션 비밀번호를 사용하여 PasswordAuthentication 객체를 초기화합니다.
        // 이메일 주소와 비밀번호는 실제 값을 사용하는 것이 아니라 예시로 제공된 것입니다.
        passAuth = new PasswordAuthentication("google developers email", "google email developers password");
    }

    // 메일 서버와의 인증을 위한 PasswordAuthentication 객체를 반환하는 메서드입니다.
    public PasswordAuthentication getPasswordAuthentication() {
        // 생성자에서 설정한 이메일 주소와 비밀번호를 반환합니다.
        return passAuth;
    }
}
