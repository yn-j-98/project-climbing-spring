package com.coma.app.view.store;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.product.ProductDTO;

import jakarta.servlet.ServletContext;

@Controller
public class StoreController {


	@Autowired
	private ServletContext context;

	@GetMapping("/store.do")
	public String store() {
		return "views/store";
	}
	@PostMapping("/store.do")
	public String store(ProductDTO productDTO, Model model) {

		
		int page = productDTO.getPage();
		// product_datas 상품 데이터 가져오기
		ArrayList<ProductDTO> datas = (ArrayList<ProductDTO>) context.getAttribute("product_datas");
		if (datas == null) {
			model.addAttribute("msg", "현재 상품이 없습니다.");
			model.addAttribute("path", "main.do");
			return "views/info";
		} 
		else {
			// 상품 목록을 페이징 처리하여 view로 전달
			ArrayList<ProductDTO> ProductDatas = new ArrayList<>();

			int gymSize = 12; // 한 페이지에 표시할 상품 수
			int minGym = (page - 1) * gymSize + 1; // 시작 번호
			int maxGym = page * gymSize; // 끝 번호

			// 상품 총 개수 확인
			int total = datas.size();

			// 최대 상품 번호가 상품 개수보다 크면 상품 개수로 맞춤
			if (maxGym > total) {
				maxGym = total;
			}

			// 해당 페이지의 상품 목록 추출
			for (int i = minGym - 1; i < maxGym; i++) {
				ProductDatas.add(datas.get(i));
			}

			// Model에 데이터 전달
			model.addAttribute("page", page); // 현재 페이지 번호
			model.addAttribute("total", total); // 상품 총 개수
			// FIXME V파트 modelProductDatas-> ProductDatas 확인하기
			model.addAttribute("ProductDatas", ProductDatas); // 해당 페이지의 상품 목록
		}
		return "views/store";
	}
}
