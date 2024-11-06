package com.coma.app.view.store;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coma.app.biz.product.ProductDTO;

import jakarta.servlet.ServletContext;

@Slf4j
@Controller
public class StoreController {

	@Autowired
	private ServletContext context;

	@GetMapping("/store.do")
	public String store(ProductDTO productDTO, Model model) {
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

			int page = productDTO.getPage();
			int size = 12; // 한 페이지에 표시할 게시글 수
			if (page <= 0) { // 페이지가 0일 때 (npe방지)
				page = 1;
			}
			int min_num = (page - 1) * size;


			productDTO.setProduct_min_num(min_num);
			log.info("min_num = {}" , min_num);

			int total = datas.size();

			int max_num = page * size;
			if(max_num > total) {
				max_num = total;
			}
			for(int i = min_num; i < max_num; i++) {
				ProductDatas.add(datas.get(i));
			}

			// Model에 데이터 전달
			model.addAttribute("page", page); // 현재 페이지 번호
			model.addAttribute("total", total); // 상품 총 개수
			model.addAttribute("ProductDatas", ProductDatas); // 해당 페이지의 상품 목록
		}
		return "views/store";
	}
}
