package com.coma.app.view.community;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.view.function.CKEditorDeleteFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller("InsertBoardController")
public class InsertBoardController {

	@RequestMapping("/INSERTBOARDPAGEACTION.do")
	public String execute(HttpSession session) {

		String path = "editing";//글작성페이지

		//로그인 정보가 있는지 확인해주고
		String member_id = (String)session.getAttribute("MEMBER_ID");
		
		System.out.println("InsertBoard 로그인 정보 로그 : "+member_id);
		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//로그인 페이지로 넘어간다
			path = "LOGINPAGEACTION.do";
		}
		
		//로그인이 되어 있다면
		//글 작성 페이지로 넘어간다
		return path;
	}
	
	@RequestMapping("/BOARDINSERTACTION.do")
	public String boardInsert(HttpSession session, ServletContext servletContext, Model model, BoardDTO boardDTO, BoardDAO boardDAO) {
		
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "info";//메인커뮤니티페이지로 이동

		//로그인 정보가 있는지 확인해주고
		String member_id = (String)session.getAttribute("MEMBER_ID");//세션에 있는 사용자의 아이디

		System.out.println("로그인 확인: " + member_id);//로그인 확인 로그
		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//로그인 페이지로 전달해줍니다.
			model.addAttribute("msg", "글 작성은 로그인 후 사용 가능합니다.");
			model.addAttribute("path", "LOGINPAGEACTION.do");
		}
		else {
			//------------------------------------------------------------------------
			//CKEditor 제거된 이미지 제거하는 로직
			//UTF-8 형식으로 보내주니 UTF-8 로 인코딩 해주고
			//세션을 불러와서
			//세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
			int folder_session = (session.getAttribute("FOLDER_NUM") != null) ? (Integer)session.getAttribute("FOLDER_NUM"):0;
			
			//View에서 보내준 내용 확인합니다.
			System.out.println(boardDTO.getBoard_content());
			//세션에 있는 값이 0보다 크다면 이미지 폴더가 생성된 것으로 if문 실행
			if(folder_session > 0) {
				//폴더를 명시해줍니다.
				String folder = "/board_img/"+member_id+"/"+folder_session;

				//명시된 폴더를 추가해주고 내 서버의 폴더주소를 불러옵니다.
				String uploadPath = servletContext.getRealPath(folder);
				
				System.out.println("BoardInsertAction.java 확인용 로그 : "+uploadPath);
				
				//넘어온 이미지와 서버에 저장된 이미지를 체크하여
				//없는 이미지는 삭제해줍니다.
				CKEditorDeleteFile.imgDelete(boardDTO.getBoard_content(), uploadPath);
			}
			//------------------------------------------------------------------------

			System.out.println("게시글 작성자: " + member_id);//작성자 로그

			// 게시글의 제목, 내용, 지역, 작성자를 DTO에 설정
			boardDTO.setBoard_location(Location(boardDTO.getBoard_location()));
			boardDTO.setBoard_writer_id(member_id);

			// 게시글을 데이터베이스에 저장

			model.addAttribute("msg", "글이 작성 완료");
			model.addAttribute("path", "MainCommunityPage.do");
			session.setAttribute("FOLDER_NUM",null);
			
			if (!boardDAO.insert(boardDTO)) {
				//실패했을때
				model.addAttribute("msg", "글이 작성 실패");
				model.addAttribute("path", "INSERTBOARDPAGEACTION.do");
				session.setAttribute("FOLDER_NUM",folder_session);
			} 
		}
		return path;
	}

	/* 뷰에서 전달받은 지역 값을 실제 지역명으로 변환하는 함수
	 */
	public String Location(String view_Location) {
		Map<String, String> locationMap = new HashMap<String, String>();

		locationMap.put("SEOUL", "서울특별시");
		locationMap.put("GYEONGGI", "경기도");
		locationMap.put("INCHEON", "인천광역시");       
		locationMap.put("SEJONG", "세종특별자치도");
		locationMap.put("BUSAN", "부산광역시");
		locationMap.put("DAEGU", "대구광역시");
		locationMap.put("DAEJEON", "대전광역시");
		locationMap.put("GWANGJU", "광주광역시");
		locationMap.put("ULSAN", "울산광역시");
		locationMap.put("CHUNGCHEONGNAMDO", "충청남도");
		locationMap.put("CHUNGCHEONGBUKDO", "충청북도");
		locationMap.put("JEONLANAMDO", "전라남도");
		locationMap.put("JEONLABUKDO", "전라북도");
		locationMap.put("GYEONGSANGNAMDO", "경상남도");
		locationMap.put("GYEONGSANGBUKDO", "경상북도");
		locationMap.put("GANGWONDO", "강원도");
		locationMap.put("CHUNGNAM", "충청남도");

		return locationMap.getOrDefault(view_Location, "서울특별시"); // 기본값은 서울특별시
		//getOrDefault  Map 인터페이스에서 제공하는 메서드로, 
		//특정 키에 해당하는 값을 반환하되,
		//만약 그 키가 존재하지 않으면 기본값을 반환하는 역할을 합니다.
	}
}
