//package com.coma.app.view.asycnServlet;
//
//import java.io.UnsupportedEncodingException;
//import java.sql.Date;
//import java.util.Properties;
//
//import javax.mail.Address;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.coma.app.biz.member.MemberDTO;
//import com.coma.app.biz.member.MemberService;
//import com.coma.app.view.function.GoogleAuthentication;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Controller
//public class MailSend {
//
//    @Autowired
//    MemberService memberService;
//
//    @PostMapping("/sendMail")
//    public void sendMail(HttpServletRequest request, HttpServletResponse response, MemberDTO memberDTO) throws Exception {
//        try {
//            request.setCharacterEncoding("utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 클라이언트로부터 인증번호와 이메일 주소를 받습니다.
//        String num = request.getParameter("num");
//        String receiver = request.getParameter("member_id");
//        System.out.println("receiver: " + receiver);
//
//        // 이메일 제목과 내용을 설정합니다.
//        String subject = "코마 비밀번호 찾기 메일.";
//        String content = "인증번호: " + num;
//
//        response.setContentType("text/html; charset=utf-8");
//
//        try {
//            // 메일 서버 속성 설정
//            Properties p = System.getProperties();
//            p.put("mail.smtp.starttls.enable", "true");
//            p.put("mail.smtp.host", "smtp.gmail.com");
//            p.put("mail.smtp.auth", "true");
//            p.put("mail.smtp.port", "587");
//
//            // GoogleAuthentication을 사용하여 인증 정보를 생성합니다.
//            Authenticator auth = new GoogleAuthentication();
//            Session session = Session.getInstance(p, auth);
//
//            // 이메일 메시지를 작성합니다.
//            MimeMessage message = new MimeMessage(session);
//            Address receiver_address = new InternetAddress(receiver);
//
//            message.setHeader("content-type", "text/html;charset=utf-8");
//            message.addRecipient(Message.RecipientType.TO, receiver_address);
//            message.setSubject(subject);
//            message.setContent(content, "text/html;charset=utf-8");
//            message.setSentDate(new Date());
//
//            // 메일을 전송합니다.
//            Transport.send(message);
//
//            // 사용자를 검색
//            memberDTO.setMember_id(receiver);
//            MemberDTO memberSearch = memberService.selectOneSearchId(memberDTO);
//
//            // 사용자가 존재하는지 확인 후 비밀번호 업데이트
//            if (memberSearch != null) {
//
//                memberDTO.setMember_id(receiver);
//                memberDTO.setMember_password(num); // 인증번호를 비밀번호로 설정
//
//                // 비밀번호 업데이트 실행
//                boolean flag = memberService.update(memberDTO);
//
//                if (flag) {
//                    System.out.println("asyncServlet.MailSend [비밀번호 업데이트 성공]");
//                } else {
//                    System.out.println("asyncServlet.MailSend [비밀번호 업데이트 실패]");
//                }
//            }
//            else {
//                System.out.println("사용자를 찾을 수 없습니다.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //아이디 체크
//    //아이디 중복체크가 아니라 아이디가 존재하는지의 체크
//    //이메일 입력
//    //입력한 이메일로 전송
//    //이메일로 전송된 번호로 비밀번호 설정
//
//
//
//
//}
