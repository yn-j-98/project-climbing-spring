package com.coma.app.view.admin;

import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.view.annotation.LoginCheck;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.util.List;


@Controller
public class GymManagementController {


    @Autowired
    private GymService gymService;

    @Autowired
    private ServletContext servletContext;


    @LoginCheck
    @GetMapping("/gymManagement.do")
    public String gymManagement(Model model, GymDTO gymDTO) {
        /*
    - 암벽장 관리 페이지
    - 암벽장 관리 리스트
        - 암벽장 사진 	암벽장 이름 	암벽장 장소 	암벽장 가격 	암벽장 소개
        	↑
    SELECTALL (아마도)
    */
        int page = gymDTO.getPage();
        int size = 10; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;


        gymDTO = this.gymService.selectOneCount(gymDTO);
        int listNum = gymDTO.getTotal();


        gymDTO.setGym_min_num(min_num);
        List<GymDTO> datas = this.gymService.selectAllAdmin(gymDTO);

        this.gymService.selectAllAdminVerified(gymDTO);
        //승인 비승인된 암벽장 검색





        model.addAttribute("datas", datas);//암벽장 전체 데이터
        model.addAttribute("page", page);//현재 페이지
        model.addAttribute("total", listNum);//암벽장 총 개수


        return "admin/gymManagement";
    }

    @LoginCheck
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
        String uploadPath = servletContext.getRealPath("/gym_img/");
        MultipartFile file = gymDTO.getGym_file();
        String fileName = file.getOriginalFilename();
        System.out.println("파일명 " + fileName);

        file.transferTo(new File(uploadPath + fileName));

        gymDTO.setGym_profile(fileName);
        System.out.println("프로필 이미지 저장 로그: " + gymDTO); // 프로필 이미지 저장 로그

        if (!this.gymService.insertAdmin(gymDTO)) {
            title = "실패";
            msg = "암벽장 등록이 실패했습니다";
        }
        model.addAttribute("title", title);
        model.addAttribute("msg", msg);
        model.addAttribute("path", infoPath);

        return "views/info";
    }


}
