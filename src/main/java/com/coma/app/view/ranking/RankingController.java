package com.coma.app.view.ranking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coma.app.biz.grade.GradeDTO;
import com.coma.app.biz.grade.GradeService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RankingController {

    // 의존 주입
    @Autowired
    private MemberService memberService;
    @Autowired
    private GradeService gradeService;

    @GetMapping("/crewRank.do")
    public String crewRank(MemberDTO memberDTO, GradeDTO gradeDTO, Model model) {
        //크루 랭킹을 model에 요청 point 순으로 selectAll 예정
        //랭킹 페이지가 두개 이기 때문에 model에 condition 값 전달

        // 무한 스크롤
        int page = memberDTO.getMember_min_num();
        System.out.println("view에서 가져온 memberDTO.getMember_min_num()" + memberDTO.getMember_min_num());
        int size = 8; // 페이지당 항목 수
        int offset = page * size; // `page`를 기반으로 `offset`을 계산
        memberDTO.setPage(size);
        memberDTO.setMember_min_num(offset);

        //요청 값 : 전체 point / 등급 이미지 / 크루 이름 / 크루장 / 크루원명
        List<MemberDTO> member_datas = this.memberService.selectAllCrewRank(memberDTO);

        //등급 이미지는 서버에 저장해 두었기 때문에
        //model에서 받아온 등급 이미지 앞에 서버 주소를 추가해줍니다.
        //----------------------------------------------------------
        //트리거 사용안할시 아래 코드 사용


        //model에 요청해서 등급 정보를 전부 불러오고 현재 등급 9개
        List<GradeDTO> model_grade_datas = this.gradeService.selectAll(gradeDTO);

        for (MemberDTO data : member_datas) {
            int member_total_point = data.getMember_total_point();
            if (member_total_point >= 0) {
                for (GradeDTO grade : model_grade_datas) {
                    int grade_min = grade.getGrade_min_point();
                    int grade_max = grade.getGrade_max_point();
                    if (grade_min <= member_total_point && member_total_point <= grade_max) {
                        data.setMember_grade_profile("/grade_folder/" + grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                    } else if (grade_max < member_total_point) {
                        data.setMember_grade_profile("/grade_folder/" + grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                        break;
                    }
                }
            }
        }
        //FIXME V파트 model_member_datas -> member_datas 확인하기
        model.addAttribute("member_datas",member_datas);
        return "views/crewRank";
    }

    @GetMapping("/personalRank.do")
    public String personalRank(MemberDTO memberDTO, GradeDTO gradeDTO, Model model) {
        //개인 랭킹을 model에 요청 point 요청 순으로 selectAll 예정
        //랭킹 페이지가 두개 이기 때문에 model에 condition 값 조정 후 진행

        // 무한 스크롤
        int page = memberDTO.getMember_min_num();
        System.out.println("view에서 가져온 memberDTO.getMember_min_num()" + memberDTO.getMember_min_num());
        int size = 8; // 페이지당 항목 수
        int offset = page * size; // `page`를 기반으로 `offset`을 계산
        memberDTO.setPage(size);
        memberDTO.setMember_min_num(offset);

        //요청 값 : 전체 point / 등급 이미지 / 사용자 이름
        List<MemberDTO> member_datas = this.memberService.selectAllSearchRank(memberDTO);

        //등급 이미지는 서버에 저장해 두었기 때문에
        //model에서 받아온 등급 이미지 앞에 서버 주소를 추가해줍니다.
        //----------------------------------------------------------

        //model에 요청해서 등급 정보를 전부 불러오고 현재 등급 9개
        List<GradeDTO> grade_datas = this.gradeService.selectAll(gradeDTO);

        //불러온 랭킹에 등급 사진을 추가하기 위해 for문을 사용
        for (MemberDTO data : member_datas) {
            int member_total_point = data.getMember_total_point();

            //member_total_point 가 0보다 크면 for문 실행
            if(member_total_point >= 0) {
                for (GradeDTO grade : grade_datas) {
                    int grade_min = grade.getGrade_min_point();
                    int grade_max = grade.getGrade_max_point();
                    if (grade_min <= member_total_point && member_total_point <= grade_max) {
                        data.setMember_grade_profile("/grade_folder/" + grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                    } else if (grade_max < member_total_point) {
                        data.setMember_grade_profile("/grade_folder/" + grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                        break;
                    }
                }
            }// if(0 < member_total_point) { 종료
        }//	for (MemberDTO data : member_datas) { 랭킹 등급이미지 등록 for문 종료

        //받아온 값을 member_datas 값에 저장하여 전달
        // FIXME
        model.addAttribute("member_datas", member_datas);

        return "views/personalRank";
    }

}
