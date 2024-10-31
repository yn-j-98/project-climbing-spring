package com.coma.app.view.crew.common;


import com.coma.app.biz.battle_record.Battle_recordDTO;
import com.coma.app.biz.battle_record.Battle_recordService;
import com.coma.app.biz.crew.CrewDTO;
import com.coma.app.biz.crew.CrewService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
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
    private ServletContext servletContext;

    @Autowired
    private CrewService crewService;

    @Autowired
    private Battle_recordService battle_recordService;

    @Autowired
    private MemberService memberService;


    @LoginCheck
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

    @LoginCheck
    @GetMapping("/crewInfo.do")
    public String crewInfo(Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO) {
        /*
         * 크루 상세보기 페이지
         * 뷰에게 크루 번호를 받아옵니다
         * crewDTO와 DAO를 생성해서 받아온 크루번호를 DTO에 set해서
         * crew selectOne 크루의 정보을 불러옵니다.
         * memverDTO와 DAO를 생성해서 선택한 크루의 인원을
         * selectOne해줍니다.
         * model_battle_record_datas라는
         * ArrayList를 생성해서 승리목록을 뷰에게 전달합니다.
         * crewDTO(크루의 정보)또한 뷰에게 전달합니다.
         */

        //해당 크루 정보 + 현재 크루원 숫자
        crewDTO = this.crewService.selectOneCountCurretMemberSize(crewDTO);
        log.info("crewDTO = [{}]", crewDTO);

        //이미지 url 저장
        model.addAttribute("crew_profile", makeURL(crewDTO));

        //크루번호로 승리기록 조회
        battle_recordDTO.setBattle_record_crew_num(crewDTO.getCrew_num());
        List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinner(battle_recordDTO);

        model.addAttribute("CREW", crewDTO);
        model.addAttribute("battle_record_datas", battle_record_datas);
        return "views/crewInformation";
    }


    @LoginCheck
    @GetMapping("/crewJoin.do")
    public String crewJoin(Model model, CrewDTO crewDTO, @SessionAttribute("MEMBER_ID") String member_id, HttpSession session) {
        log.info("crewJoin.crew_num = [{}]", crewDTO.getCrew_num());
        //얼럿창 info 데이터
        String title = "";
        String msg = "";
        String path = "";

        //커맨드 객체로 바인딩된 크루 번호
        int crew_num = crewDTO.getCrew_num();

        //세션의 로그인된 사용자 아이디로 사용자가 속한 크루번호 출력
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(member_id);
        MemberDTO data = memberService.selectOneSearchMyCrew(memberDTO);

        // 크루 가입 유효성 검사
        if (data.getMember_crew_num() == crew_num) {
            model.addAttribute("title", "크루 가입 실패");
            model.addAttribute("msg", "이미 소속된 크루가 있습니다.");
            model.addAttribute("path", "crewList.do");
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
            path = "myCrewPage.do";
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
    @GetMapping("/crew.do")
    public String crewPage(Model model, CrewDTO crewDTO, Battle_recordDTO battle_recordDTO, MemberDTO memberDTO, @SessionAttribute("CREW_CHECK") Integer crew_num) {
        log.info("crewPage.crew_num = [{}]", crew_num);
        if (crew_num <= 0) {
            return "redirect:crewList.do";
        } else {
            // 크루 정보 가져오기
            crewDTO.setCrew_num(crew_num);
            crewDTO = this.crewService.selectOne(crewDTO);

            // 크루 정보 + 크루 이미지 URL
            model.addAttribute("CREW", crewDTO);
            model.addAttribute("crew_profile", makeURL(crewDTO));

            // 해당 크루에 속한 사용자 이름 전부 출력
            memberDTO.setMember_crew_num(crew_num);
            List<MemberDTO> member_crew_datas = this.memberService.selectAllSearchCrewMemberName(memberDTO);
            model.addAttribute("member_crew_datas", member_crew_datas);

            // 해당 크루에 승리 기록 전부 출력
            battle_recordDTO.setBattle_record_crew_num(crew_num);
            List<Battle_recordDTO> battle_record_datas = this.battle_recordService.selectAllWinner(battle_recordDTO);
            model.addAttribute("battle_record_datas", battle_record_datas);
        }
        return "views/myCrewPage";
    }

    //크루 이미지 파일명으로 url 생성
    private String makeURL(CrewDTO crewDTO) {
        String filename = "";
        if (crewDTO.getCrew_profile() == null) {
            filename = "default.jpg"; // 기본 이미지
        } else {
            filename = crewDTO.getCrew_profile(); // 크루 이미지 받아옴
        }
        return servletContext.getContextPath() + "/crew_img_folder/" + filename;
    }
}