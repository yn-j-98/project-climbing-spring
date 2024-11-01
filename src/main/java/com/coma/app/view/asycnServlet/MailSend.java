package com.coma.app.view.asycnServlet;

import java.sql.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j; // 로깅을 위한 라이브러리
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.function.GoogleAuthentication;

@Slf4j
@RestController
public class MailSend {

    @Autowired // Spring의 의존성 주입을 사용하여 MemberService를 자동 주입
    MemberService memberService;

    @PostMapping("/sendMail.do")
    public String sendMail(@RequestBody MemberDTO memberDTO) { // 요청 본문에서 MemberDTO를 가져옴
        String memberId = memberDTO.getMember_id(); // MemberDTO에서 회원 ID를 가져옴
        System.out.println("Received memberId: " + memberId); // 수신된 회원 ID 출력

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword(); // 임시 비밀번호 생성 메서드 호출
        log.info("password : [{}]", tempPassword); // 임시 비밀번호 로깅
        memberDTO.setMember_password(tempPassword); // 임시 비밀번호를 MemberDTO에 설정
        memberDTO.setMember_id(memberId); // 회원 ID 설정

        // 비밀번호 업데이트 시도
        boolean updateFlag = memberService.updatePassword(memberDTO); // 비밀번호 업데이트 메서드 호출
        if (updateFlag) { // 업데이트 성공 시
            System.out.println("비밀번호 업데이트 성공"); // 성공 메시지 출력
            String subject = "코마 비밀번호 찾기 메일."; // 이메일 제목
            String content = "임시 비밀번호: " + tempPassword; // 이메일 내용

            try {
                // 이메일 서버 속성 설정
                Properties p = System.getProperties(); // 시스템 속성 객체 생성
                p.put("mail.smtp.starttls.enable", "true"); // STARTTLS를 사용하여 보안 연결 활성화
                p.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP 서버 호스트
                p.put("mail.smtp.auth", "true"); // SMTP 인증 활성화
                p.put("mail.smtp.port", "587"); // SMTP 포트 번호

                // Google 인증 객체 생성
                Authenticator auth = new GoogleAuthentication(); // 인증 정보를 위한 객체 생성
                Session session = Session.getInstance(p, auth); // 세션 생성
                MimeMessage message = new MimeMessage(session); // MIME 메시지 객체 생성
                Address receiver_address = new InternetAddress(memberId);

                // 이메일 메시지 설정
                message.setHeader("content-type", "text/html;charset=utf-8"); // 메시지의 내용 타입과 문자 인코딩 설정
                message.addRecipient(Message.RecipientType.TO, receiver_address);
                message.setSubject(subject); // 메시지 제목 설정
                message.setContent(content, "text/html;charset=utf-8"); // 메시지 내용 및 형식 설정
                message.setSentDate(new Date(System.currentTimeMillis())); // 현재 날짜 설정

                Transport.send(message); // 이메일 전송
                System.out.println("임시 비밀번호가 이메일로 전송되었습니다."); // 전송 성공 메시지 출력
                return "임시 비밀번호가 이메일로 전송되었습니다."; // 클라이언트에게 응답

            } catch (Exception e) { // 이메일 전송 중 예외 발생 시
                e.printStackTrace(); // 예외 스택 트레이스 출력
                return "메일 전송 중 오류가 발생했습니다."; // 클라이언트에게 오류 메시지 반환
            }

        } else { // 비밀번호 업데이트 실패 시
            System.out.println("비밀번호 업데이트 실패"); // 실패 메시지 출력
            return "비밀번호 업데이트 실패"; // 클라이언트에게 응답
        }
    }

    // 임시 비밀번호 생성 메서드
    private String generateTempPassword() {
        return String.valueOf((int) (Math.random() * 9000 + 1000)); // 4자리 임시 비밀번호 생성
    }
}
