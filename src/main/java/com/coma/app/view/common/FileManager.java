//package com.coma.app.view.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.coma.app.view.function.Mkdir_File;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.ServletContext;
//
//@Component
//public class FileManager {
//
//    @Autowired
//    private ServletContext servletContext;
//
//    // 초기화 작업을 위한 메서드
//    @PostConstruct
//    public void init() {
//        // 생성할 폴더 이름들을 배열로 저장 (존재하지 않으면 생성)
//        String[] folders = {"grade_folder", "crew_img_folder", "profile_img", "board_img"};
//
//        // 실제 경로를 구하고 폴더 생성 메서드를 호출
//        for (String folder : folders) {
//            // 각 폴더의 실제 파일 시스템 상의 경로를 가져오기
//            String folderPath = servletContext.getRealPath("/" + folder + "/");
//
//            // Mkdir_File 클래스의 create 메서드 호출
//            Mkdir_File.create(folderPath);
//        }
//    }
//}
