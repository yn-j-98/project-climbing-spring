package com.coma.app.view.function;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CKEditorDeleteFile {

	public static void imgDelete(String content, String uploadPath) {
		//----------------------------------------------------------------------------
		//파일이 있는지 확인하기 위해 해당 폴더의 파일을 가져옵니다.
		File directory = new File(uploadPath);
		//폴더에 있는 파일리스트를 가져옵니다.

		//배열리스트로 전환
		ArrayList<File> arr_files = new ArrayList<File>(Arrays.asList(directory.listFiles()));

		//보내준 파일에서 이미지 경로만 문자열 배열로 불러와 줍니다.
		String[] img_paths = content.split("src=\"");

		//문자열 배열을 기준으로 for문을 돌립니다.
		for(String img_path : img_paths) {
			System.out.println("받아온 이미지 태그 : "+img_path);
			try {
				//저장된 주소+받아온 이미지 경로 끝에 큰따옴표(")가 붙어있기 때문에 제거해줍니다.
				String editorContent = uploadPath+File.separator+img_path.split("\"")[0].toString().substring(img_path.split("\"")[0].lastIndexOf("/")+1);
				//받은 이미지 로그
				System.out.println("이미지 : "+editorContent);
				File remove_file = new File(editorContent);
				//만약 파일이 있다면 배열에서 삭제합니다.
				if(arr_files.remove(remove_file)) {
					System.out.println("배열리스트에서 지워진 이미지 : " + remove_file);
				}
			}catch (Exception e) {

			}
		}

		//forEach 를 사용하여 배열을 확인하고
		for (File file : arr_files) {
			//삭제 파일 로그
			System.out.println("삭제된 파일 : "+file);
			//해당 파일을 삭제합니다.
			file.delete();
		}
		//----------------------------------------------------------------------------
	}
}
