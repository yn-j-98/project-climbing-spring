//package com.coma.app.view.payment;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import com.coma.app.biz.payment.PaymentDAO;
//import com.coma.app.biz.payment.PaymentDTO;
//
//public class UpdatePaymentTest{
//
//
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("UpdatePaymentAction 시작");
//
////        String login_member_id = LoginCheck.loginCheck(request);
////        ActionForward forward=new ActionForward();
////        if(login_member_id.equals("")) {
////            System.out.println("로그인 세션 없음");
////            request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
////            request.setAttribute("path", "loginPage.do");
////
////            forward.setPath("info.jsp");
////            forward.setRedirect(false);
////            return forward;
////        }
//
//        int payment_num = Integer.parseInt(request.getParameter("payment_num"));
//
//        //데이터 로그
//        System.out.println("payment_num : "+payment_num);
//
//        PaymentDTO paymentDTO = new PaymentDTO();
//        paymentDTO.setPayment_num(payment_num);
//        paymentDTO.setPayment_status("결제취소");
//
//        PaymentDAO paymentDAO = new PaymentDAO();
//        boolean flag = paymentDAO.update(paymentDTO);
//
//        if(flag) {
//            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 성공");
//            request.setAttribute("msg", "결제가 취소가 완료 되었습니다.");
//            request.setAttribute("path", "myReservationListPage.do");
//            forward.setPath("info.jsp");
//            forward.setRedirect(false);
//        }
//        else {
//            System.out.println("UpdatePaymentAction 로그 : 결제 상태 업데이트 실패");
//            request.setAttribute("msg", "결제 취소에 실패 했습니다. 다시 시도해주세요.");
//            request.setAttribute("path", "main.do");
//            forward.setPath("info.jsp");
//            forward.setRedirect(false);
//        }
//
//        System.out.println("UpdatePaymentAction 끝");
//        return forward;
//    }
//
//}
