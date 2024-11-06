package com.coma.app.view.admin;

import com.coma.app.biz.battle.BattleDTO;
import com.coma.app.biz.battle.BattleServiceImpl;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.view.annotation.AdminCheck;
import com.coma.app.view.annotation.LoginCheck;
import com.coma.app.view.asycnServlet.FTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
public class GymManagementController {


    @Autowired
    private GymService gymService;

    @Autowired
    private FTPService ftpService;
    @Autowired
    private BattleServiceImpl battleService;

    @AdminCheck
    @GetMapping("/gymManagement.do")
    public String gymManagement(Model model, GymDTO gymDTO) {
        /*
    - 암벽장 관리 페이지
    - 암벽장 관리 리스트
        - 암벽장 사진 	암벽장 이름 	암벽장 장소 	암벽장 가격 	암벽장 소개
        	↑
    SELECTALL
    */
        String search_keyword =  gymDTO.getSearch_keyword();
        int listNum = 0;
        int page = gymDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;

        List<GymDTO> datas = null;
        //페이지 네이션을 위한 값
        gymDTO.setGym_min_num(min_num);
        if (search_keyword == null) {
            listNum = this.gymService.selectOneCount(gymDTO).getTotal();
            datas = this.gymService.selectAllAdmin(gymDTO);
            log.debug("if (search_keyword == null) end");
        }
        else if("ALL".equals(search_keyword)){
            //전체 암벽장 이름 검색
            listNum = this.gymService.selectOneAdminSearchCount(gymDTO).getTotal();
            datas = this.gymService.selectAllAdminSearch(gymDTO);
            log.debug("else if(\"ALL\".equals(search_keyword)) end");
        }
        else if("adminCertified".equals(search_keyword)){
            //크루전 등록 된 암벽장 이름 검색
            gymDTO.setGym_admin_battle_verified("T");
            listNum = this.gymService.selectOneAdminVerifiedCount(gymDTO).getTotal();
            datas = this.gymService.selectAllAdminVerified(gymDTO);
            log.debug("else if(\"adminCertified\".equals(search_keyword)) end");
        }
        else if("adminUnCertified".equals(search_keyword)){
            //크루전 등록 안된 암벽장 이름 검색
            gymDTO.setGym_admin_battle_verified("F");
            listNum = this.gymService.selectOneAdminVerifiedCount(gymDTO).getTotal();
            datas = this.gymService.selectAllAdminVerified(gymDTO);
            log.debug("else if(\"adminUnCertified\".equals(search_keyword)) end");
        }//승인 비승인된 암벽장 검색

        log.info("admin gym datas [{}]",datas);
        log.info("admin gym listNum [{}]",listNum);
        log.info("admin gym page [{}]",page);

        model.addAttribute("datas", datas);//암벽장 전체 데이터
        model.addAttribute("search_keyword",gymDTO.getSearch_keyword());
        model.addAttribute("search_content",gymDTO.getSearch_content());
        model.addAttribute("page", page);//현재 페이지
        model.addAttribute("total", listNum);//암벽장 총 개수

        return "admin/gymManagement";
    }

    @AdminCheck
    @PostMapping("/gymInsert.do")
    public String gymInsert(Model model, GymDTO gymDTO) throws IOException {
        String infoPath = "gymManagement.do";
        String title = "성공!";
        String msg = "암벽장이 등록되었습니다";
       /*
    - 암벽장 추가하기
        - 암벽장 이름
    암벽장 장소
    암벽장 가격
    암벽장 소개
    암벽장 사진 (파일 업로드)
    */
        MultipartFile file = gymDTO.getGym_file();
        String fileName = ftpService.ftpFileUpload(file,"gym_img");
        log.info("파일명 : [{}]",fileName);

        gymDTO.setGym_profile(fileName);
        log.info("프로필 이미지 저장 로그 : [{}]", gymDTO);

        if (!this.gymService.insertAdmin(gymDTO)) {
            title = "실패";
            msg = "암벽장 등록이 실패했습니다";
        }
        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", infoPath);

        return "views/info";
    }

    @AdminCheck
    @PostMapping("/gymManagementReq.do")
    public String gymManagementReq(Model model, GymDTO gymDTO, BattleDTO battleDTO) {

        String infoPath = "gymManagement.do";
        String title = "크루전이 등록되었습니다.";
        String msg = "";

        battleDTO.setBattle_gym_num(gymDTO.getGym_num());

        boolean flag=false;

        if(this.battleService.selectOneSearchBattleAdmin(battleDTO)==null) {
            flag=this.battleService.insertFirst(battleDTO);
            if(!flag) {
                System.out.println("battle insert 오류");
            }
        }

        if("T".equals(gymDTO.getGym_admin_battle_verified())){
            title = "이미 크루전이 등록되어 있는 암벽장입니다.";
        }
        else if("F".equals(gymDTO.getGym_admin_battle_verified())){
            gymDTO.setGym_admin_battle_verified("T");
            log.info("GymDTO [{}]",gymDTO);

            //암벽장 번호, 암벽장 크루전 등록 여부를 받습니다.
            if(!gymService.updateAdminBattleVerified(gymDTO)){
                title = "크루전 등록 실패";
                msg = "Server 오류로 크루전에 등록에 실패하였습니디.";
            }
        }
        else{
            log.info("battle_verified = [{}]",gymDTO.getGym_admin_battle_verified());
            title = "잘못된 요청";
            msg = "전달된 데이터 : "+gymDTO.getGym_admin_battle_verified();
        }

        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", infoPath);

        return "views/info";
    }

}
