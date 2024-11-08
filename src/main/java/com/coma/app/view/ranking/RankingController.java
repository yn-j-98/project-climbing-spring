package com.coma.app.view.ranking;

import com.coma.app.biz.grade.GradeDTO;
import com.coma.app.biz.grade.GradeService;
import com.coma.app.biz.member.MemberDTO;
import com.coma.app.biz.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class RankingController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private GradeService gradeService;

    // 크루 랭킹 페이지
    @GetMapping("/crewRank.do")
    public String crewRank(MemberDTO memberDTO, GradeDTO gradeDTO, Model model) {

        // 무한 스크롤 설정
        int page = memberDTO.getMember_min_num();
        log.info("view에서 가져온 memberDTO.getMember_min_num() {}",memberDTO.getMember_min_num());
        int size = 8; // 페이지당 항목 수
        int offset = page * size; // `page`를 기반으로 `offset`을 계산
        memberDTO.setPage(size);
        memberDTO.setMember_min_num(offset);

        // 크루 랭킹 데이터
        //요청 값 : 전체 point / 등급 이미지 / 크루 이름 / 크루장 / 크루원명
        List<MemberDTO> member_datas = this.memberService.selectAllCrewRank(memberDTO);

        // 크루 등급정보
        // model에 요청해서 등급 정보를 전부 불러오고 현재 등급 9개
        List<GradeDTO> model_grade_datas = this.gradeService.selectAll(gradeDTO);

        // 랭킹 데이터에 등급 정보 추가
        for (MemberDTO data : member_datas) {
            int member_total_point = data.getMember_total_point();
            if (member_total_point >= 0) {
                for (GradeDTO grade : model_grade_datas) {
                    int grade_min = grade.getGrade_min_point();
                    int grade_max = grade.getGrade_max_point();
                    if (grade_min <= member_total_point && member_total_point <= grade_max) {
                        data.setMember_grade_profile(grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                    } else if (grade_max < member_total_point) {
                        data.setMember_grade_profile(grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                        break;
                    }
                }
            }
        }
        model.addAttribute("member_datas",member_datas);
        return "views/crewRank";
    }

    @GetMapping("/personalRank.do")
    public String personalRank(MemberDTO memberDTO, GradeDTO gradeDTO, Model model) {

        // 무한 스크롤 설정
        int page = memberDTO.getMember_min_num();
        log.info("view에서 가져온 memberDTO.getMember_min_num() {}",memberDTO.getMember_min_num());
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
                    // 사용자의 총 포인트가 현재 등급 범위 내에 있는 경우
                    if (grade_min <= member_total_point && member_total_point <= grade_max) {
                        data.setMember_grade_profile(grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                        // 사용자의 총 포인트가 모든 등급의 최대 포인트보다 큰 경우
                    } else if (grade_max < member_total_point) {
                        data.setMember_grade_profile(grade.getGrade_profile());
                        data.setMember_grade_name(grade.getGrade_name());
                        break;
                    }
                }
            }
        }

        //받아온 값을 member_datas 값에 저장하여 전달
        model.addAttribute("member_datas", member_datas);

        return "views/personalRank";
    }

}
