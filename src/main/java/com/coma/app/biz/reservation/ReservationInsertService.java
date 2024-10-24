package com.coma.app.biz.reservation;

import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reservationInsertService")
public class ReservationInsertService {

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    MemberDAO memberDAO;

    public boolean insert(ReservationDTO reservationDTO, MemberDTO memberDTO) {

        boolean flag=false;

        flag=reservationDAO.insert(reservationDTO);
        if(!flag) {
            System.out.println("com.coma.app.biz.reservation.ReservationInsertService reservationDAO.insert 오류");
        }
        memberDTO.setMember_id(reservationDTO.getReservation_member_id());
        flag=memberDAO.updateCurrentPoint(memberDTO);

        return flag;
    }
}
