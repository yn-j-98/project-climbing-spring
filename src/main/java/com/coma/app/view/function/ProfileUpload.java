package com.coma.app.view.function;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ProfileUpload {

	public static String upload(HttpServletRequest request) {
		String filename = null;
		try {
			// 파트로 인코딩한 파일 가져옵니다.
			Part filePart = request.getPart("photoUpload");
			// 파일 이름 가져옵니다.
			filename = filePart.getSubmittedFileName();
			//만약 사용자가 file 업로드 했다면 fileName 을 반환해줍니다.
			if(!filename.isEmpty()) {
				// 파일 이름에서 파일 형식 만 가져와 줍니다.
				//파일 형식은 .으로 시작하기 때문에 마지막 .xxx 를 가져오기 위해
				//substring 과 lastIndexOf 를 사용했습니다.
				String fileform = filename.substring(filename.lastIndexOf("."));

				// 업로드 경로 설정(webapp파일에 위치 찾기)
				//String uploadPath = ""; // 기본 서버에 저장 @MultipartConfig으로 \tmp0\work\Catalina\localhost\내프로젝트(COMA_PROJECT_CONTROLLER)\profile_img 에 저장했음
				String uploadPath = request.getServletContext().getRealPath("/profile_img/"); // 내프로젝트(COMA_PROJECT_CONTROLLER)\profile_img\ 에 저장했음

				HttpSession session = request.getSession();
				//사용자 아이디 + 사용자가 올린 파일 형식
				filename = (String)session.getAttribute("MEMBER_ID") + fileform;

				// 파일 저장
				// profile_img 폴더 주소 + 사용자 아이디.사용자가 올린 파일 형식
				String filePath = uploadPath + filename;
				filePart.write(filePath);
			}
			//만약 file을 업로드 하지 않았다면 null을 반환합니다. fileName을 null로 반환한다.
			// 로그 : 응답 출력
			System.out.println("File 이름 로그 " + filename);

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("file upload error");
			return filename;
		}
		return filename;
	}

}
