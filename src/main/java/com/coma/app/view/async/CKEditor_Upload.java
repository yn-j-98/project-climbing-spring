package com.coma.app.view.async;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CKEditor_Upload {

    @Autowired
    FTPService ftpService;

    @PostMapping("/ckupload.do")
    public @ResponseBody Map<String, String> returnImage(HttpSession session, @RequestBody MultipartFile upload) throws IOException {
        //현재 들어온 파일 타입 확인용
        //upload.getContentType();

        //--------------------------------------------------------------------------
        //FIXME 사용된 변수명
        //로그인 정보가 있는지 확인해주고
        String member_id = (String)session.getAttribute("MEMBER_ID");//세션에 있는 사용자의 아이디
        //FTP 서버에 저장되어 있는 폴더의 명칭을 입력
        String FTPFolderPath = "board_img";

        System.out.println("FOLDER_NUM : "+session.getAttribute("FOLDER_NUM"));
        //세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록 수정
        int folder_session = (session.getAttribute("FOLDER_NUM") == null) ? 0:(Integer)session.getAttribute("FOLDER_NUM");
        System.out.println("UPDATE_FOLDER_NUM : "+session.getAttribute("UPDATE_FOLDER_NUM"));
        //세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록 수정
        int update_folder_session = (session.getAttribute("UPDATE_FOLDER_NUM") == null) ? 0:(Integer)session.getAttribute("UPDATE_FOLDER_NUM");

        //--------------------------------------------------------------------------
        //FIXME 저장될 폴더를 생성 로직
        int folder_num = 0;
        if(update_folder_session > 0) {
            System.out.println("CKEditor_Upload.java folder_session 로그 : "+update_folder_session);
            folder_num = update_folder_session;
        }
        else {
            //현재 폴더 개수를 불러와줍니다.
            //(처음 불러올때는 서버/사용자폴더/ 로 불러옵니다)
            folder_num = this.ftpService.ftpFolderCount(FTPFolderPath,member_id);
            //만약 저장되어 있는 개수가 없다면
            if(folder_session <= 0 && update_folder_session <= 0) {
                //사용자의 게시판 이미지 폴더 개수 확인용 로그
                System.out.println("사용자 게시판 이미지 폴더 확인용 로그 :"+folder_num);
                //번호를 불러와서 +1을 해준다음
                folder_num = folder_num+1;
                //폴더를 추가해줍니다.
                this.ftpService.ftpCreateFolder(FTPFolderPath,member_id,folder_num);
                //추가된 폴더의 번호를 세션에 저장해줍니다.
                session.setAttribute("FOLDER_NUM", folder_num);
            }
        }
        //--------------------------------------------------------------------------

        //FTPService 를 호출하여 이미지를 FTP에 저장합니다.
        String uploadPath = this.ftpService.ftpFileUpload(upload,FTPFolderPath,member_id,folder_num);

        //--------------------------------------------------------------------------
        //FIXME View로 서버에 저장된 주소를 전달하는 로직
        //view로 보내줄 파일 주소를 저장해서 전달해줍니다.
        System.out.println("파일 주소 : "+uploadPath);//

        Map<String,String> jsonData = new HashMap<String,String>();
        jsonData.put("url",uploadPath);
        // JSON 응답 생성
        return jsonData;
        //--------------------------------------------------------------------------
    }


}
