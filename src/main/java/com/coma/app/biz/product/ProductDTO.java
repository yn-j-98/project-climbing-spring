package com.coma.app.biz.product;

public class ProductDTO {
	private int model_product_num; //상품 PK
	private String model_product_name; //상품 이름
	private String model_product_profile; //상품 사진
	private int model_product_default_price; //상품 원가
	private String model_product_discount_rate; //상품 할인율
	private int model_product_discount_price; //상품 할인된 가격
	private String model_product_link; //상품 링크
	
	private int page;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getModel_product_num() {
		return model_product_num;
	}
	public void setModel_product_num(int model_product_num) {
		this.model_product_num = model_product_num;
	}
	public String getModel_product_name() {
		return model_product_name;
	}
	public void setModel_product_name(String model_product_name) {
		this.model_product_name = model_product_name;
	}
	public String getModel_product_profile() {
		return model_product_profile;
	}
	public void setModel_product_profile(String model_product_profile) {
		this.model_product_profile = model_product_profile;
	}

	public String getModel_product_discount_rate() {
		return model_product_discount_rate;
	}
	public void setModel_product_discount_rate(String model_product_discount_rate) {
		this.model_product_discount_rate = model_product_discount_rate;
	}
	public int getModel_product_default_price() {
		return model_product_default_price;
	}
	public void setModel_product_default_price(int model_product_default_price) {
		this.model_product_default_price = model_product_default_price;
	}
	public int getModel_product_discount_price() {
		return model_product_discount_price;
	}
	public void setModel_product_discount_price(int model_product_discount_price) {
		this.model_product_discount_price = model_product_discount_price;
	}
	
	public String getModel_product_link() {
		return model_product_link;
	}
	public void setModel_product_link(String model_product_link) {
		this.model_product_link = model_product_link;
	}
	@Override
	public String toString() {
		return "ProductDTO [model_product_num=" + model_product_num + ", model_product_name=" + model_product_name
				+ ", model_product_profile=" + model_product_profile + ", model_product_default_price="
				+ model_product_default_price + ", model_product_discount_rate=" + model_product_discount_rate
				+ ", model_product_discount_price=" + model_product_discount_price + ", model_product_link="
				+ model_product_link + "]";
	}
	
	
	
}
