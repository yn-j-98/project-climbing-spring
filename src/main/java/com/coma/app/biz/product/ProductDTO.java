package com.coma.app.biz.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
	private int product_num; //상품 PK
	private String product_name; //상품 이름
	private String product_profile; //상품 사진
	private int product_default_price; //상품 원가
	private String product_discount_rate; //상품 할인율
	private int product_discount_price; //상품 할인된 가격
	private String product_link; //상품 링크

	private int page;	// 사용자 페이지네이션
	private int product_min_num;	// 페이지 네이션 시작 PK
}
