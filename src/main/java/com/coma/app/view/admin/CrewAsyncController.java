package com.coma.app.view.admin;


import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CrewAsyncController {

    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private CrewService crewService;

    @PostMapping("/crewName.do")
    public @ResponseBody List<Battle_recordDTO> crewName(@RequestBody Battle_recordDTO battle_recordDTO) {

        List<Battle_recordDTO> battle_record_datas = battle_recordService.selectAllWinner(battle_recordDTO);
        log.info("battle_record_datas = {}",battle_record_datas);

        return battle_record_datas;
    }

    @PostMapping("/mvpMember.do")
    public @ResponseBody List<CrewDTO> mvpMember(@RequestBody CrewDTO crewDTO) {

        List<CrewDTO> datas = crewService.selectAll(crewDTO);
        log.info("datas = {}",datas);

        return datas;
    }
}
