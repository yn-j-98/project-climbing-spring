package com.coma.app.view.common;

import com.coma.app.view.function.Mkdir_File;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class FileManager implements ServletContextListener {

	public FileManager() {
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("파일 생성 시작");
		String[] folders = 
				//등급 이미지 폴더 , 크루 이미지 폴더 , 사용자 프로필 폴더, 게시글 이미지 
			{"grade_folder","crew_img_folder","profile_img","board_img"};

		for (String data : folders) {
			// 실제 경로 받아오기
			String folder_path = sce.getServletContext().getRealPath("/"+data+"/");

			// 폴더 경로 로고
			System.out.println("폴더 경로: " + folder_path);
			
			//만들어둔 폴더생성 함수 사용
			Mkdir_File.create(folder_path);
		}
	}

	public void contextDestroyed(ServletContextEvent sce)  { 
	}

}
