//package com.coma.app.view.asycnServlet;
//
//import java.io.File;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//import com.coma.app.view.annotation.LoginCheck;
//import com.coma.app.view.function.Mkdir_File;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//@LoginCheck
//@WebServlet("/ckupload")
//@MultipartConfig
//public class CKEditor_Upload extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//    public CKEditor_Upload() {
//        super();
//    }
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//--------------------------------------------------------------------------
//		//FIXME 사용된 변수명
//
//		//세션을 불러와서
//		HttpSession session = request.getSession();
//
//		//로그인 정보가 있는지 확인해주고
//		String login[] = LoginCheck.Success(request, response);
//		String member_id = login[0];//세션에 있는 사용자의 아이디
//
//        // simpleUpload 의 기본 파라미터는 "upload" 파라미터로 전송된다.
//        Part filePart = request.getPart("upload");
//        //넘어오는 파일의 명칭과 형식을 가져와서 변수에 저장합니다.
//        String fileName = filePart.getSubmittedFileName();
//        //받아온 파일의 형식만 불러와줍니다.
//        String fileform = fileName.substring(fileName.lastIndexOf("."));
//
//		//이미지이름을 보안코드로 변환하여 서버에 저장해줍니다.
//		fileName = CKEditor_Upload.img_security()+fileform;
//
//        //저장할 폴더 명칭
//        String folerName = "/board_img/"+member_id+"/";
//        //가져온 파일을 저장할 위치를 지정해줍니다.
//        String uploadPath = getServletContext().getRealPath(folerName);
//        //저장될 위치를 확인하기 위한 로그
//        System.out.println("파일 주소 : "+uploadPath);
//		System.out.println("FOLDER_NUM : "+session.getAttribute("FOLDER_NUM"));
//		//세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록 수정
//		int folder_session = (session.getAttribute("FOLDER_NUM") == null) ? 0:(Integer)session.getAttribute("FOLDER_NUM");
//		System.out.println("UPDATE_FOLDER_NUM : "+session.getAttribute("UPDATE_FOLDER_NUM"));
//		//세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록 수정
//		int update_folder_session = (session.getAttribute("UPDATE_FOLDER_NUM") == null) ? 0:(Integer)session.getAttribute("UPDATE_FOLDER_NUM");
//
//		//--------------------------------------------------------------------------
//		//FIXME 저장될 폴더를 생성 로직
//		int folder_num = 0;
//		//폴더가 없을 수 있기 때문에 만들어둔 폴더생성 함수를 활용
//		Mkdir_File.create(uploadPath);
//		if(update_folder_session > 0) {
//			System.out.println("CKEditor_Upload.java folder_session 로그 : "+update_folder_session);
//			folder_num = update_folder_session;
//		}
//		else {
//			//현재 폴더 개수를 불러와줍니다.
//			//(처음 불러올때는 서버/사용자폴더/ 로 불러옵니다)
//			folder_num = CKEditor_Upload.member_folder_num(uploadPath);
//			//만약 저장되어 있는 개수가 없다면
//			if(folder_session <= 0 && update_folder_session <= 0) {
//				//사용자의 게시판 이미지 폴더 개수 확인용 로그
//				System.out.println("사용자 게시판 이미지 폴더 확인용 로그 :"+folder_num);
//				//번호를 불러와서 +1을 해준다음
//				folder_num = CKEditor_Upload.member_folder_num(uploadPath)+1;
//				//폴더를 추가해줍니다.
//				Mkdir_File.create(uploadPath+folder_num);
//				//추가된 폴더의 번호를 세션에 저장해줍니다.
//				session.setAttribute("FOLDER_NUM", folder_num);
//			}
//		}
//		//--------------------------------------------------------------------------
//		//FIXME 서버에 이미지 파일 저장 로직
//		//파일 주소가 변경되었으니 변경된 주소를 추가해줍니다.
//		uploadPath = uploadPath + "/" + folder_num;
//
//		//받아온 주소로 파일을 저장합니다.
//        //운영체제마다 파일 구분자가 다르기 때문에 File.separator를 추가해줍니다.
//		//이런 File.separator 클래스가 있다는 걸 처음보아 사용해보았습니다.
//        filePart.write(uploadPath + File.separator + fileName);
//
//		//--------------------------------------------------------------------------
//		//FIXME View로 서버에 저장된 주소를 전달하는 로직
//        //view로 보내줄 파일 주소를 저장해서 전달해줍니다.
//        String fileUrl = request.getContextPath() + folerName + folder_num + "/" + fileName;
//        System.out.println("파일 주소 : "+fileUrl);
//        response.getWriter().write("{ \"url\": \"" + fileUrl + "\" }");
//     	//--------------------------------------------------------------------------
//	}
//
//	//현재 폴더 개수를 가져오는 함수
//	private static int member_folder_num(String uploadPath) {
//		//폴더 위치를 불러옵니다.
//		File directory = new File(uploadPath);
//		//폴더에 있는 파일리스트를 가져오고 배열리스트로 전환합니다.
//		ArrayList<File> arr_files = new ArrayList<File>(Arrays.asList(directory.listFiles()));
//		System.out.println(arr_files);
//		//해당 폴더의 개수를 반환해줍니다.
//		return arr_files.size();
//	}
//
//	private static String img_security() {
//		String security_code = "";
//		try {
//		//랜덤한 보안키를 만들기 위한 Random 함수를 추가
//		Random rnd = new Random();
//
//		//랜덤한 숫자를 받아오고
//		String rndKey = Integer.toString(rnd.nextInt(32000));
//		//랜덤 숫자를 변환할 형식을 지정해줍니다.
//		MessageDigest md = MessageDigest.getInstance("MD5");
//
//		//랜덤 숫자를 바이트 형식으로 변환하고
//		byte[] bytData = rndKey.getBytes();
//		//"MD5" 형식으로 변환합니다.
//		md.update(bytData);
//		//랜덤 숫자로 만들어야 하므로 변환한 형식을 바이트로 만들어주고
//		byte[] digest = md.digest();
//		for(int i =0;i<digest.length;i++){
//			//변환된 형식의 보안 코드를 생성해줍니다.
//			security_code = security_code + Integer.toHexString(digest[i] & 0xFF);
//		}
//		//해당 보안코드를 모두 사용하게 되면 너무 길기 때문에 11자리 숫자만 사용합니다.
//		security_code = security_code.substring(0,11);
//
//		} catch (NoSuchAlgorithmException e) {
//			System.out.println("CKEditor_Upload.java 로그 MessageDigest.getInstance(\"MD5\") 생성오류 발생");
//		}
//		//보안코드를 반환해줍니다.
//		return security_code;
//	}
//}
