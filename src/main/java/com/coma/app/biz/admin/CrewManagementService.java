package com.coma.app.biz.admin;

import com.coma.app.biz.battle.BattleDAO;
import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle_record.Battle_recordDAO;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.crew.CrewDAO;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.member.MemberDAO3;
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
    Battle_recordDAO battle_recordDAO;

    @Autowired
    CrewDAO crewDAO;

    @Autowired
    MemberDAO3 memberDAO;


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
    public List<MemberDTO> mvpMember(MemberDTO memberDTO){
        //크루 이름으로 크루를 찾고

        //크루pk로 크루원들 전부 출력
        List<MemberDTO> datas = memberDAO.selectAllSearchCrewMemberName(memberDTO);
        log.info("mvpMemberService = [{}]",datas);
        return datas;
    }

    public CrewDTO searchCrew(CrewDTO crewDTO){
        return crewDAO.selectOneName(crewDTO);
    }


    public CrewDTO battleRecord(CrewDTO crewDTO){
        //크루 이름을 받아와 크루 번호를 찾습니다.
        //받은 크루 번호를 전달합니다.
        return this.crewDAO.selectOneName(crewDTO);
    }
    public BattleDTO selectOneSearchWinner(BattleDTO battleDTO){
        return battleDAO.selectOneSearchWinner(battleDTO);
    }
    public List<BattleDTO> selectAllSearchPariticipants(BattleDTO battleDTO){
        return battleDAO.selectAllSearchPariticipants(battleDTO);
    }


    public boolean updateBattleRecord(Battle_recordDTO battle_recordDTO){
        boolean result;
        BattleDTO battleDTO = new BattleDTO();
        battleDTO.setBattle_num(battle_recordDTO.getBattle_record_battle_num());
        battleDTO.setBattle_status("T");
        //승리 크루를 모두 업데이트한 후
        battle_recordDTO.setBattle_record_is_winner("T");
        result=this.battle_recordDAO.update(battle_recordDTO);

        log.info("battle_recordDAO.update result = [{}]",result);

            result = this.battle_recordDAO.updateMvp(battle_recordDTO);
            log.info("battle_recordDAO.updateMvp result = [{}]", result);

        log.info("battle_recordDAO.updateMvp result = [{}]",result);

        CrewDTO crewDTO = new CrewDTO();
        crewDTO.setCrew_battle_num(battle_recordDTO.getBattle_record_battle_num());
        List<CrewDTO> datas=this.crewDAO.selectAllAdmin(crewDTO);

        int cnt=1;
        for(CrewDTO data:datas){
            result=this.crewDAO.updateBattleFalse(data);
            log.info("{}번째 crewDAO.updateBattleFalse result = [{}]",cnt,result);
            cnt++;
        }

        result=battleDAO.updateStatus(battleDTO);
        log.info("battleDAO.updateStatus result = [{}]",result);
        return result;
    }
}
