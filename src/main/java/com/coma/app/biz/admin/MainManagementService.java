package com.coma.app.biz.admin;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDAO3;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDAO;
import com.coma.app.biz.reservation.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("MainManagementService")
public class MainManagementService {

    @Autowired
    private MemberDAO3 memberDAO;
    @Autowired
    private GymDAO gymDAO;
    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private BattleDAO battleDAO;

    public String getManagementTotalDate(MemberDTO memberDTO, GymDTO gymDTO, ReservationDTO reservationDTO,
                                      BoardDTO boardDTO, BattleDTO battleDTO){

        String memberTotalDate = getMemberTotal(memberDTO)+"";
        log.info("getManagementTotalDate memberTotalDate : [{}]",memberTotalDate);
        String gymTotalDate = getGymTotal(gymDTO)+"";
        log.info("getManagementTotalDate gymTotalDate : [{}]",gymTotalDate);
        String reservationTotalDate = getReservationTotal(reservationDTO)+"";
        log.info("getManagementTotalDate reservationTotalDate : [{}]",reservationTotalDate);
        String boardTotalDate = getBoardTotal(boardDTO)+"";
        log.info("getManagementTotalDate boardTotalDate : [{}]",boardTotalDate);
        String battleTotalDate = getBattleTotal(battleDTO)+"";
        log.info("getManagementTotalDate battleTotalDate : [{}]",battleTotalDate);

        String formatData = "[{title:%s, text:%s},{title:%s, text:%s},{title:%s, text:%s},{title:%s, text:%s},{title:%s, text:%s}]";
        String data = String.format(formatData,
                "'사용자'",memberTotalDate,
                "'암벽장'",gymTotalDate,
                "'예약'",reservationTotalDate,
                "'게시판'",boardTotalDate,
                "'크루전'",battleTotalDate);
        log.info("data : [{}]",data);

        return data;
    }

    private int getMemberTotal(MemberDTO memberDTO) {
        return memberDAO.selectOneCountAdmin(memberDTO).getTotal();
    }

    private int getGymTotal(GymDTO gymDTO) {
        return gymDAO.selectOneCount(gymDTO).getTotal();
    }

    private int getReservationTotal(ReservationDTO reservationDTO) {
        return reservationDAO.selectOneCountYearAdmin(reservationDTO).getTotal();
    }

    private int getBoardTotal(BoardDTO boardDTO) {
        return boardDAO.selectOneCount(boardDTO).getTotal();
    }

    private int getBattleTotal(BattleDTO battleDTO) {
        return battleDAO.selectOneCountActive(battleDTO).getTotal();
    }
}
