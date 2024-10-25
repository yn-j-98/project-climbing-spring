package com.coma.app.view.function;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Component
public class ProfileUpload {

    private final ServletContext servletContext;

    public ProfileUpload(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String upload(MultipartFile photoUpload, HttpSession session) {
        String filename = null;

        try {
            // 파일 이름 가져오기
            filename = photoUpload.getOriginalFilename();

            // 파일이 업로드 되었다면 처리
            if (filename != null && !filename.isEmpty()) {
                // 파일 형식 가져오기
                String fileForm = filename.substring(filename.lastIndexOf("."));

                // 업로드 경로 설정
                String uploadPath = servletContext.getRealPath("/profile_img/");
                
                // 사용자 아이디 + 사용자가 올린 파일 형식
                filename = (String) session.getAttribute("MEMBER_ID") + fileForm;

                // 파일 저장 경로
                File file = new File(uploadPath + filename);
                photoUpload.transferTo(file); // 파일 저장
            }

            System.out.println("File 이름 로그 " + filename);

        } catch (Exception e) {
            System.out.println("file upload error");
            return filename; // 오류 발생 시 파일 이름 반환
        }
        
        return filename; // 성공적으로 업로드한 파일 이름 반환
    }
}
