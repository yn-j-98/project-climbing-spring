package com.coma.app.view.admin;

import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.view.annotation.LoginCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.MappingMatch.PATH;


@Controller
public class GymManagementController {

    @Autowired
    private GymService gymService;

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
        int size = 5; // 한 페이지에 표시할 게시글 수
        if (page <= 0) { // 페이지가 0일 때 (npe방지)
            page = 1;
        }
        int min_num = (page - 1) * size;

        gymDTO.setGym_min_num(min_num);
        gymDTO = this.gymService.selectOneCount(gymDTO);

        int listNum = gymDTO.getTotal();

        //사진

        List<GymDTO> datas = this.gymService.selectAllAdmin(gymDTO);

        model.addAttribute("datas", datas);
        model.addAttribute("page", page);
        model.addAttribute("total", listNum);


        return "admin/gymManagement";
    }

    @LoginCheck
    @PostMapping("/gymInsert.do")
    public String gymInsert(GymDTO gymDTO) throws IOException {
       /*
    - 암벽장 추가하기

        - 암벽장 이름
    암벽장 장소
    암벽장 가격
    암벽장 소개
    암벽장 사진 (파일 업로드)
    */
        //MultipartFile file = gymDTO.getFile();
       // String fileName = file.getOriginalFilename();
        String directoryPath = PATH + "gym_image/";

        // gym_image 폴더가 존재하는지 확인하고, 없으면 생성
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

       // System.out.println("파일명 " + fileName);

        // 파일을 gym_image 폴더에 저장
     //   file.transferTo(new File(directoryPath + fileName));

      //  gymDTO.setGym_profile(fileName);

        boolean flag = this.gymService.insert(gymDTO);
        if (!flag) {

        }

        return "admin/main";
    }



}
