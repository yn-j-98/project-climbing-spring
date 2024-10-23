package com.coma.app.view.store;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coma.app.biz.product.ProductDTO;

import jakarta.servlet.ServletContext;

@Controller
public class StoreController {

	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping("/StorePage.do")
	public String store(Model model, ProductDTO productDTO) {
		String path = "store"; // view에서 알려줄 예정 alert 창 띄우기 위한 JavaScript 페이지

		//------------------------------------------------------------
		//TODO 해당 페이지에서 공통으로 사용할 변수 and 객체
		//product_datas 상품이 담겨 있는 session 불러오기
		ArrayList<ProductDTO> datas = (ArrayList<ProductDTO>)servletContext.getAttribute("product_datas");
		if(datas == null || datas.isEmpty()) {
			path = "info";
			model.addAttribute("msg", "현재 상품이 없습니다.");
			model.addAttribute("path", "MAINPAGEACTION.do");
		}
		else {
			ArrayList<ProductDTO> model_product_datas = new ArrayList<ProductDTO>();
			
			//------------------------------------------------------------
			//session 에 담겨 있는 상품 목록을 출력해주는 로직 시작
			//TODO (페이지 번호 / 상품 총 개수) 를 구하는 로직을 작성
			int page_num = 1; // page_num 초기 변수 지정
			if(productDTO != null) {
				page_num = productDTO.getPage();			
			}
			int gym_size = 12; // 한 페이지에 표시할 게시글 수 설정
			int min_gym = 1; // 최소 게시글 수 초기화
			int max_gym = 1; // 최대 게시글 수 초기화

			// 페이지 번호에 따라 최소 및 최대 게시글 수 설정
			if(page_num <= 1) {
				// 페이지 번호가 1 이하일 경우
				min_gym = 1; // 최소 게시글 번호를 1로 설정
				max_gym = min_gym * gym_size; // 최대 게시글 번호 계산
			}
			else {
				// 페이지 번호가 2 이상일 경우
				min_gym = ((page_num - 1) * gym_size) + 1; // 최소 게시글 번호 계산
				max_gym = page_num * gym_size; // 최대 게시글 번호 계산
			}
			System.out.println("모든 상품 : "+datas);
			System.out.println("최소 번호 : " + min_gym);
			System.out.println("최대 번호 : " + max_gym);

			System.out.println("상품 개수1 : "+ datas.size());
			int product_total = datas.size();
			System.out.println("상품 개수2 : "+product_total);

			if(datas.size() < gym_size) {
				max_gym = datas.size();
				System.out.println("상품 개수 12 미만 : "+max_gym);
			}
			else if(datas.size() <= max_gym){
				max_gym = datas.size();
			}

			for(int i = min_gym-1; i < max_gym; i++) {
				System.out.println("상품 번호 : " + i);
				model_product_datas.add(datas.get(i));
			}

			System.out.println("넘어갈 모든 상품 :"+model_product_datas);
			// 페이지 번호 : page_num
			model.addAttribute("page_num", page_num);
			// 상품의 총 개수 : product_total
			model.addAttribute("product_total", product_total);
			//상품 목록 전달
			model.addAttribute("model_product_datas", model_product_datas);

			//session 에 담겨 있는 상품 목록을 출력해주는 로직 종료
			//------------------------------------------------------------
		}

		return path;
	}

}
