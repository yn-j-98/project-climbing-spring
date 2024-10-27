package com.coma.app.biz.admin;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDAO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.reservation.ReservationDAO;
import com.coma.app.biz.reservation.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("MainManagementService")
public class MainManagementService {

    @Autowired
    private MemberDAO memberDAO;
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

        int memberTotalDate = getMemberTotal(memberDTO);
        log.info("getManagementTotalDate memberTotalDate : [{}]",memberTotalDate);
        int gymTotalDate = getGymTotal(gymDTO);
        log.info("getManagementTotalDate gymTotalDate : [{}]",gymTotalDate);
        int reservationTotalDate = getReservationTotal(reservationDTO);
        log.info("getManagementTotalDate reservationTotalDate : [{}]",reservationTotalDate);
        int boardTotalDate = getBoardTotal(boardDTO);
        log.info("getManagementTotalDate boardTotalDate : [{}]",boardTotalDate);
        int battleTotalDate = getBattleTotal(battleDTO);
        log.info("getManagementTotalDate battleTotalDate : [{}]",battleTotalDate);

        String formatData = """
                [{title:%s, text:%s},
                {title:%s, text:%s},
                {title:%s, text:%s},
                {title:%s, text:%s},
                {title:%s, text:%s}]
                """;
        String data = String.format(formatData,
                "사용자",memberTotalDate,
                "암벽장",gymTotalDate,
                "예약",reservationTotalDate,
                "게시판",boardTotalDate,
                "크루전",battleTotalDate);
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
        return reservationDAO.selectOneCount(reservationDTO).getTotal();
    }

    private int getBoardTotal(BoardDTO boardDTO) {
        return boardDAO.selectOneCount(boardDTO).getBoard_total();
    }

    private int getBattleTotal(BattleDTO battleDTO) {
        return battleDAO.selectOneCountActive(battleDTO).getBattle_total();
    }
}
