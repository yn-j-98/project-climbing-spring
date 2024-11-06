package com.coma.app.view.crew.common;


import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import com.coma.app.view.annotation.CrewCheck;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;


@Slf4j
@Controller
public class CrewController {
    @Autowired
    private CrewService crewService;

    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private MemberService memberService;


    @GetMapping("/crewList.do")
    public String crewList(Model model, CrewDTO crewDTO) {
        //페이지 네이션
        int pageNum = crewDTO.getPage();
        log.info("pageNum = [{}]", pageNum);
        int boardSize = 10; // 한 페이지에 표시할 게시글 수 설정
        int minBoard = 0; // 최소 게시글 수 초기화

        if (pageNum > 1) { // 페이지가 0일 때 (npe방지)
            minBoard = ((pageNum - 1) * boardSize);
        }
        int listNum = 0; // 게시글 총 개수를 저장할 변수 초기화

        //크루 전체 목록 조회 + 페이지네이션 적용
        crewDTO.setCrew_min_num(minBoard);
        List<CrewDTO> crew_datas = this.crewService.selectAll(crewDTO);
        if (!crew_datas.isEmpty()) {
            log.error("crew_datas 비어있음");
        }
        // 페이지네이션용 크루 전체 개수
        CrewDTO crewCount = this.crewService.selectOneCount(crewDTO);
        listNum = crewCount.getTotal();

        log.info("CrewCount.listNum [{}]", listNum);
        log.info("crew_datas = [{}]", crew_datas);
        model.addAttribute("crew_datas", crew_datas);
        model.addAttribute("total", listNum);
        model.addAttribute("page", pageNum);

        return "views/crewList";
    }


    @GetMapping("/crewInfo.do")
    public String crewInfo(Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO) {

        //해당 크루 정보 + 현재 크루원 숫자
        crewDTO = this.crewService.selectOneCountCurretMemberSize(crewDTO);
        log.info("crewDTO = [{}]", crewDTO);

        //이미지 url 저장
        model.addAttribute("crew_profile", crewDTO.getCrew_profile());

        //크루번호로 승리기록 조회
        battle_recordDTO.setBattle_record_crew_num(crewDTO.getCrew_num());
        List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinner(battle_recordDTO);

        model.addAttribute("CREW", crewDTO);
        model.addAttribute("battle_record_datas", battle_record_datas);
        return "views/crewInformation";
    }


    @LoginCheck
    @GetMapping("/crewJoin.do")
    public String crewJoin(Model model, CrewDTO crewDTO, HttpSession session) {
        log.info("crewJoin.crew_num = [{}]", crewDTO.getCrew_num());
        //얼럿창 info 데이터
        String title = "";
        String msg = "";
        String path = "";
        String member_id = (String) session.getAttribute("MEMBER_ID");

        //커맨드 객체로 바인딩된 크루 번호
        int crew_num = crewDTO.getCrew_num();

        //세션의 로그인된 사용자 아이디로 사용자가 속한 크루번호 출력
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(member_id);
        MemberDTO data = memberService.selectOneSearchMyCrew(memberDTO);
        log.info("data = [{}]", data);
        log.info("data crew num = [{}]",data.getMember_crew_num());
        // 크루 가입 유효성 검사
        if (data.getMember_crew_num() >= 1) {
            model.addAttribute("title","크루 가입 실패");
            model.addAttribute("msg","이미 소속된 크루가 있습니다.");
            model.addAttribute("path","crewList.do");
            return "views/info";
        }

        //세션의 사용자 아이디, url로 넘어온 crew_num으로 크루 가입
        memberDTO.setMember_id(member_id);
        memberDTO.setMember_crew_num(crew_num);
        boolean flag = this.memberService.updateCrew(memberDTO);
        if (flag) {
            // 업데이트 성공 시 세션 갱신
            session.setAttribute("CREW_CHECK", crew_num);
            title = "가입 성공";
            msg = "해당 크루에 가입을 완료했습니다";
            path = "crew.do";
        } else {
            // 업데이트 실패 시
            title = "가입 실패";
            msg = "크루 가입에 실패했습니다";
            path = "crewList.do";
        }
        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", path);

        return "views/info";
    }

    @LoginCheck
    @CrewCheck
    @GetMapping("/crew.do")
    public String crewPage(Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO, MemberDTO memberDTO,
                           @SessionAttribute(value = "CREW_CHECK",required = false) Integer crew_num) {
        log.info("crewPage.crew_num = [{}]", crew_num);


        // 크루 정보 가져오기
        crewDTO.setCrew_num(crew_num);
        crewDTO = this.crewService.selectOne(crewDTO);

        if (crewDTO == null) {
            model.addAttribute("title", "크루 정보 없음");
            model.addAttribute("msg", "해당 크루의 정보를 찾을 수 없습니다.");
            model.addAttribute("path", "crewList.do");
            return "views/info";
        }

        // 크루 정보 + 크루 이미지 URL
        model.addAttribute("CREW", crewDTO);
        model.addAttribute("crew_profile", crewDTO.getCrew_profile());

        // 해당 크루에 속한 사용자 이름 전부 출력
        memberDTO.setMember_crew_num(crew_num);
        List<MemberDTO> member_crew_datas = this.memberService.selectAllSearchCrewMemberName(memberDTO);
        model.addAttribute("member_crew_datas", member_crew_datas);

        // 해당 크루에 승리 기록 전부 출력
        battle_recordDTO.setBattle_record_crew_num(crew_num);
        List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinner(battle_recordDTO);
        model.addAttribute("battle_record_datas", battle_record_datas);

        return "views/myCrewPage";
    }

}