package com.coma.app.view.community;



import java.util.HashMap;
import java.util.Map;

import com.coma.app.biz.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.board.BoardDAO;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.view.function.CKEditorDeleteFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller("boardUpdateController")
public class BoardUpdateController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("/BOARDUPDATEPAGEACTION.do")
	public String boardUpdate(HttpSession session, Model model, BoardDAO boardDAO, BoardDTO boardDTO) {
		//기본으로 넘어가야하는 페이지 와 redirect 여부를 설정
		String path = "updateEditing";

	      //사용자 아이디
	      String member_id = (String)session.getAttribute("MEMBER_ID");
	      
		//만약 로그인 정보가 없다면
		if(member_id == null) {
			//LoginPageAction 페이지로 전달해줍니다.
			path = "LOGINPAGEACTION.do";
		}
		else {
			//사용자가 선택한 글번호를 받아서
			boardDTO.setBoard_writer_id(member_id);
			boardDTO.setBoard_condition("BOARD_ONE_WRITER_ID");
			//model 에 전달하여 글 내용을 받아오고
			boardDTO = boardDAO.selectOne(boardDTO);
			
			//만약 데이터가 null 이라면 mypage.do 로 전달
			if(boardDTO == null) {
				path="info";
				model.addAttribute("path", "MYPAGEPAGEACTION.do");
				model.addAttribute("msg", "없는 게시글입니다.");
			}
			else {
				//해당 글 내용을 view 로 전달해줍니다.
				model.addAttribute("BOARD_NUM", boardDTO.getBoard_num());
				model.addAttribute("BOARD_TITLE", boardDTO.getBoard_title());
				System.out.println("지역 로그 1 : "+boardDTO.getBoard_location());
				System.out.println("지역 로그 2 : "+location(boardDTO.getBoard_location()));
				model.addAttribute("BOARD_LOCATION", location(boardDTO.getBoard_location()));
				
				String content = boardDTO.getBoard_content();
				model.addAttribute("BOARD_CONTENT", content);
				
				try {
					//글 내용에서 img 태그가 있다면 해당 이미지 폴더의 번호만 가져오는 로직
					content = content.substring(content.lastIndexOf("img")+3).split("/")[2];
					System.out.println("BoardUpdatePageAction.java content 로그 : "+content);
					session.setAttribute("UPDATE_FOLDER_NUM", Integer.parseInt(content));
				} catch (Exception e) {
					session.setAttribute("UPDATE_FOLDER_NUM", 0);
				}
				
			}

			
		}
		
		return path;
	}
	
	@RequestMapping("/BOARDUPDATEACTION.do")
	public String boardUpdate(HttpSession session, ServletContext servletContext, Model model, BoardDTO boardDTO, BoardDAO boardDAO) {
		// 기본으로 넘어가야하는 페이지와 redirect 여부를 설정
		String path = "MYPAGEPAGEACTION.do"; // 마이페이지로

		// 로그인 정보가 있는지 확인
		String member_id = (String)session.getAttribute("MEMBER_ID"); // 세션에 있는 사용자의 아이디
		System.out.println("로그인 확인: " + member_id);

		// 만약 로그인 정보가 없다면
		if (member_id == null) {
			// 로그인 페이지로 전달
			path = "LOGINPAGEACTION.do";

		} else {
			// 로그인이 되어있다면
			// 업데이트 가능
			//------------------------------------------------------------------------
			//CKEditor 제거된 이미지 제거하는 로직
			//세션을 불러와서
			//세션에 저장되어 있는 폴더 개수를 가져옵니다. 삼항연산자로 만약 세션값이 null이 아니라면 정수형으로 변경하여 가져오도록합니다.
			int folder_session = (session.getAttribute("UPDATE_FOLDER_NUM") != null) ? (Integer)session.getAttribute("UPDATE_FOLDER_NUM"):0;

			//View에서 보내준 내용 확인합니다.
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

			boardDTO.setBoard_location(location(boardDTO.getBoard_location()));

			//boardDTO.setBoard_condition("BOARD_UPDATE_CONTENT_TITLE"); // 글 수정 컨디션
			boardService.updateContentTitle(boardDTO); // 업데이트
		}

		return path;
	}
	/* 뷰에서 전달받은 지역 값을 실제 지역명으로 변환하는 함수
	 */
	public String location(String view_Location) {
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
