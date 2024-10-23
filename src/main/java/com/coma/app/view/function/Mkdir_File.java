package com.coma.app.view.function;

import java.io.File;

public class Mkdir_File {
	public static void create(String folder_path) {
		
		// File 객체 생성
		File folder = new File(folder_path);

		// 폴더가 존재하는지 확인하고, 없으면 생성
		if (!folder.exists()) {
			boolean flag = folder.mkdirs();  // 폴더를 생성

			if (flag) {
				System.out.println("폴더 생성 완료 : " + folder_path);
			} 
			else {
				System.out.println("폴더 생성 실패");
			}
		} 
		else {
			System.out.println("폴더가 존재 : " + folder_path);
		}
		System.out.println("파일 생성 종료");
	}
	
	
}
