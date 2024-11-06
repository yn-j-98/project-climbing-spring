package com.coma.app.view.admin;


import com.coma.app.biz.admin.CrewManagementService;
import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.member.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CrewAsyncController { // 크루전 관리 비동기

    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private CrewManagementService crewManagementService;

    @PostMapping("/crewName.do")
    public @ResponseBody List<Battle_recordDTO> crewName(@RequestBody Battle_recordDTO battle_recordDTO) {

        log.info("crewName.battle_recordDTO = {}",battle_recordDTO);
        List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllParticipantCrew(battle_recordDTO);
        log.info("battle_record_datas = {}",battle_record_datas);

        return battle_record_datas;
    }

    @PostMapping("/mvpMember.do")
    public @ResponseBody String mvpMember(@RequestBody CrewDTO crewDTO, MemberDTO memberDTO) throws JsonProcessingException {

        log.info("mvpMember.crewDTO = {}",crewDTO);
        //크루 이름으로 크루를 찾고

        int crew_num = crewManagementService.searchCrew(crewDTO).getCrew_num();
        //크루pk로 크루원들 전부 출력

        memberDTO.setMember_crew_num(crew_num);
        List<MemberDTO> datas = crewManagementService.mvpMember(memberDTO);
        log.info("mvpMemberService = [{}]",datas);
        log.info("datas = {}",datas);

        // 멤버 목록을 JSON 형식으로 변환
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(datas);
    }
}
