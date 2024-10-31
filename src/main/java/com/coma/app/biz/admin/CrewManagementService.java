package com.coma.app.biz.admin;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.crew.CrewDAO;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.member.MemberDAO;
import com.coma.app.biz.member.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CrewManagementService")
public class CrewManagementService {

    @Autowired
    BattleDAO battleDAO;

    @Autowired
    CrewDAO crewDAO;

    @Autowired
    MemberDAO memberDAO;


    public List<BattleDTO> selectAll(BattleDTO battleDTO){
        List<BattleDTO> datas = new ArrayList<BattleDTO>();
        //검색어가 없이 검색입력하거나 공백 입력시
        if( battleDTO.getSearch_keyword() == null){
            datas = battleDAO.selectAllBattle(battleDTO);
        }
        else if(battleDTO.getSearch_keyword().equals("")){
            return null;
        }
        //크루전 번호 검색
        else if( battleDTO.getSearch_keyword().equals("NUM")){
            datas = battleDAO.selectAllSearchBattleNum(battleDTO);
        }
        //암벽장 이름 검색
        else if( battleDTO.getSearch_keyword().equals("MEMBERNAME")){
            datas = battleDAO.selectAllSearchBattleName(battleDTO);
        }
        //경기 날짜 검색
        else if( battleDTO.getSearch_keyword().equals("MEMBERID")){
            datas = battleDAO.selectAllSearchGameDate(battleDTO);
        }
        //크루전 생성일 검색
        else if( battleDTO.getSearch_keyword().equals("DATE")){
            datas = battleDAO.selectAllSearchRegistrationDate(battleDTO);
        }
        log.info("selectAllService = [{}]",datas);
        return datas;
    }
    public List<MemberDTO> mvpMember(CrewDTO crewDTO){
        //크루 이름으로 크루를 찾고
        int crew_num = crewDAO.selectOneName(crewDTO).getCrew_num();
        //크루pk로 크루원들 전부 출력
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_crew_num(crew_num);
        List<MemberDTO> datas = memberDAO.selectAllSearchCrewMemberName(memberDTO);
        return datas;
    }
}
