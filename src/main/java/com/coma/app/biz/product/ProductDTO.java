package com.coma.app.biz.product;

public class ProductDTO {
	private int product_num; //상품 PK
	private String product_name; //상품 이름
	private String product_profile; //상품 사진
	private int product_default_price; //상품 원가
	private String product_discount_rate; //상품 할인율
	private int product_discount_price; //상품 할인된 가격
	private String product_link; //상품 링크

	public int getProduct_num() {
		return product_num;
	}

	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_profile() {
		return product_profile;
	}

	public void setProduct_profile(String product_profile) {
		this.product_profile = product_profile;
	}

	public int getProduct_default_price() {
		return product_default_price;
	}

	public void setProduct_default_price(int product_default_price) {
		this.product_default_price = product_default_price;
	}

	public String getProduct_discount_rate() {
		return product_discount_rate;
	}

	public void setProduct_discount_rate(String product_discount_rate) {
		this.product_discount_rate = product_discount_rate;
	}

	public int getProduct_discount_price() {
		return product_discount_price;
	}

	public void setProduct_discount_price(int product_discount_price) {
		this.product_discount_price = product_discount_price;
	}

	public String getProduct_link() {
		return product_link;
	}

	public void setProduct_link(String product_link) {
		this.product_link = product_link;
	}

	@Override
	public String toString() {
		return "ProductDTO{" +
				"product_num=" + product_num +
				", product_name='" + product_name + '\'' +
				", product_profile='" + product_profile + '\'' +
				", product_default_price=" + product_default_price +
				", product_discount_rate='" + product_discount_rate + '\'' +
				", product_discount_price=" + product_discount_price +
				", product_link='" + product_link + '\'' +
				'}';
	}
}
