package com.coma.app.view.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class CrewAdminController {

    @GetMapping("/crewManagement.do")
    public String crewManagement() {
        return "admin/crewManagement";
    }

    @GetMapping("/crewManagementDetail.do")
    public String crewManagementDetail() {
        return "admin/crewManagementDetail";
    }



    // 크루전 관리
    public String crewBattleManagement(Model model) {
		/*
		selectbox
			크루전 번호, 암벽장 이름, 크루전 진행 날짜


		크루전 번호
		암벽장 이름
		크루전 진행 날짜
		크루전 생성일
		상태 ( t/f )
		↑ SELECTALL


		 */

        // 크루 정보 등록 필요 nullcheck (t/f)


        return null;
    }

    public String crewBattleManagementModal(Model model) {

        //		크루전 진행한 전체 크루 사람 selectall
        //
        //		크루전 진행한 1개 크루명 자체를 불러오기 selectall
        //
        //		selectall* selectall 재활용(C)
		/*
		!!!!!!!!!!!!!!!!!!!!!!!비동기
		암벽장 이름
		크루전 진행 날짜
		승리 크루 크루명 (크루전 진행한 크루의 크루명 selectall 검색해야하기때문에)
		크루전 MVP
		↑ INSERT


		 */

        return null;
    }
}
