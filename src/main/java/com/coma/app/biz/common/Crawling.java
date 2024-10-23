package com.coma.app.biz.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.product.ProductDTO;


public class Crawling {
	public static WebDriver driver;
	private String target_url;
	private String default_url;
	WebDriverWait wait = null;
	public Crawling() {
		// 크롬 옵션 설정
		ChromeOptions options = new ChromeOptions();
		// 헤드리스 모드 추가 (코드 실행시 크롬창이 뜨지않게 함)
		options.addArguments("--headless");
		// 팝업창 제거 옵션 추가
		options.addArguments("--disable-popup-blocking");
		//GPU 가속 비활성화
		options.addArguments("--disable-gpu");
		//샌드박스 비활성화
		options.addArguments("--no-sandbox");

		// 옵션설정한 ChromeDriver 인스턴스 생성
		driver = new ChromeDriver(options);
		//
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	}

	public ArrayList<ProductDTO> makeSampleProduct() {
		System.out.println("model.Crawling.makeSampleProduct 시작");
		ArrayList<ProductDTO> datas = new ArrayList<ProductDTO>();
		//크롤링할 사이트 url
		default_url = "https://spiri7.com";
		target_url = "";
		//css tag
		String product_button = "#__next > main > div.card.mb-11.w-full > div.px-5.flex.justify-between.items-end.mb-2.font-KoPubWorldDotum > button";
		String product_href= "#__next > main > div.card.px-5.py-2\\.5.pb-24 > div.list-content.flex.flex-wrap >a";
		String product_name = "#__next > main > div:nth-child(3) > h1";
		String product_default_price ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.flex.items-center > span.text-gray-500.text-sm.font-normal.truncate.line-through";
		String product_discount_rate ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.flex.items-center > span.text-red-400.text-base.font-bold.truncate.mr-1";
		String product_discount_price ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.text-red-400.text-xl.font-bold.truncate";
		String product_img = "#__next > main > div.relative.w-full.bg-white > section > div > div.slick-list > div > div.slick-slide.slick-active.slick-current > div > div > div > span > img";


		//크롤링 동작 부분
		try {
			//드라이버에 url 주입
			driver.get(default_url);

			//추천상품 더보기 버튼 찾기
			WebElement button = driver.findElement(By.cssSelector(product_button));
			//버튼 클릭
			button.click();

			// 변경된 URL을 저장
			target_url = driver.getCurrentUrl();
			System.out.println("63 target_url ="+target_url);

			//브라우저 열수있게 1초 대기
			Thread.sleep(1000);


			//페이지 이동후 크롤링
			List<WebElement> product = driver.findElements(By.cssSelector(product_href));
			for(WebElement detail : product) {

				//링크 파밍
				String tag = detail.getAttribute("href").replace("..", default_url);
				System.out.println("74 href = "+ tag);

				//파밍한 링크 접속
				driver.get(tag);
				System.out.println("78");
				//상품 이름
				List<WebElement> productName = driver.findElements(By.cssSelector(product_name));

				//상품 이미지
				List<WebElement> productImg = driver.findElements(By.cssSelector(product_img));

				//상품 원가
				List<WebElement> productDefaultPrice = driver.findElements(By.cssSelector(product_default_price));

				//상품 할인율
				List<WebElement> productDiscountRate = driver.findElements(By.cssSelector(product_discount_rate));

				//상품 할인된 가격
				List<WebElement> productDiscountPrice = driver.findElements(By.cssSelector(product_discount_price));

				for(int i=0;i<productName.size();i++) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setModel_product_name(productName.get(i).getText());
					productDTO.setModel_product_profile(productImg.get(i).getAttribute("src"));
					productDTO.setModel_product_discount_rate(productDiscountRate.get(i).getText());
					productDTO.setModel_product_default_price(Integer.parseInt(productDefaultPrice.get(i).getText().replace("원", "").replace(",", "")));
					productDTO.setModel_product_discount_price(Integer.parseInt(productDiscountPrice.get(i).getText().replace("원신규회원", "").replace(",", "")));
					productDTO.setModel_product_link(tag);//상품 링크 FIXME
					System.out.println("이미지 = "+productDTO.getModel_product_profile());
					datas.add(productDTO);
				}
				// 들어갔던 링크를 빠져나오기 위해
				driver.navigate().back();

				//빠져나온후 페이지 로드 대기
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println("crawling.makeSampleProduct 크롤링 실패");
			e.printStackTrace();
			return datas;
		}
		System.out.println("model.Crawling.makeSampleProduct 성공");
		return datas;
	}

	public ArrayList<BoardDTO> makeSampleBoard(){
		System.out.println("model.Crawling.makeSampleBoard 시작");
		ArrayList<BoardDTO> datas = new ArrayList<BoardDTO>();
		//크롤링할 사이트 url
		default_url = "https://spiri7.com";
		target_url = "";
		//css tag
		String board_button = "#__next > main > div:nth-child(5) > div > button";
		String board_href= "#__next > main > div.bg-white.py-10.md\\:py-10 > div.w-full > ul > li";
		String board_divs = "div.postContent.py-0.font-KoPubWorldDotum > div > div.w-full.text-base.font-bold.mb-1";
		String board_title = "#__next > main > div.w-full.max-w-5xl.mx-auto.bg-white.py-4.md\\:py-8 > div.sectionPostHeader.px-4.md\\:px-6 > h1";
		String board_content = "#__next > main > div.w-full.max-w-5xl.mx-auto.bg-white.py-4.md\\:py-8 > div.sectionPostContent.md\\:pt-4.md\\:pb-10.pt-2.pb-8 > div";
		String board_writer = "#__next > main > div.w-full.max-w-5xl.mx-auto.bg-white.py-4.md\\:py-8 > div.sectionPostHeader.px-4.md\\:px-6 > div > div.flex.items-center > div.ml-2 > div.text-base.font-bold.tracking-tight";
		//크롤링 동작 부분
		try {
			//드라이버에 url 주입
			driver.get(default_url);

			System.out.println("127 default_url = "+default_url);
			//최신글 더보기 버튼 찾기
			WebElement button = driver.findElement(By.cssSelector(board_button));
			//버튼 클릭
			button.click();

			// 변경된 URL을 저장
			target_url = driver.getCurrentUrl();
			System.out.println("135 target_url ="+target_url);

			//브라우저 열수있게 1초 대기
			Thread.sleep(1000);


			//페이지 이동후 크롤링
			List<WebElement> board = driver.findElements(By.cssSelector(board_href));
			System.out.println("143");
			if(board.isEmpty()) {
				System.err.println("144 board비어있음");
			}
			//System.out.println("147 ="+board.get(6).getText());
			int rsCnt=1;
			int i;
			for(i = 0; i < board.size(); i++) {
				System.err.println("150 rsCnt = " + rsCnt);

				// 현재 게시물의 div 요소를 찾기 ( 이 페이지에서 div를 클릭해야 게시글 상세 페이지로 이동 )
				WebElement board_div;
				try {
					board_div = board.get(i).findElement(By.cssSelector(board_divs));
					System.out.println("참조오류 발생하지않은 인덱스 번호 ="+i);
				}catch (StaleElementReferenceException e) {
					//driver.navigate().back() 코드가 실행되면서 페이지가 다시 로드되어 발생
					//페이지가 뒤로 이동하면 원래 있던 페이지에서 찾은 요소가 유효하지 않게 되어 무효한 요소를 참조하려고 하여 발생
					System.out.println("참조오류 발생한 인덱스 번호 ="+i);
					board = driver.findElements(By.cssSelector(board_href));
					board_div = board.get(i).findElement(By.cssSelector(board_divs));
				}

				// div 클릭
				board_div.click();

				// 페이지가 로드되도록 대기
				Thread.sleep(1000);

				//페이지 이동후 크롤링
				//제목
				WebElement board_div_detail_title = driver.findElement(By.cssSelector(board_title));
				//System.out.println("board_div_detail_title = "+board_div_detail_title.getText());

				//내용
				WebElement board_div_detail_content = driver.findElement(By.cssSelector(board_content));
				//System.out.println("board_div_detail_content = "+board_div_detail_content.getText());

				//작성자
				String board_div_detail_writer = "coma@naver.com";
				//System.out.println("board_div_detail_writer = "+board_div_detail_writer.getText());

				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBoard_title(board_div_detail_title.getText());
				boardDTO.setBoard_content(board_div_detail_content.getText());
				boardDTO.setBoard_writer_id(board_div_detail_writer);
				datas.add(boardDTO);

				// 들어갔던 링크에서 나가기
				driver.navigate().back();

				//나가서 다시 로드되기 기다리기
				Thread.sleep(1000);
				rsCnt++;

			}
		} catch (Exception e) {
			System.err.println("crawling.makeSampleBoard 크롤링 실패");
			e.printStackTrace();
			return datas;
		}
		System.out.println("model.Crawling.makeSampleBoard 성공");
		return datas;
	}

	public ArrayList<GymDTO> makeSampleGym(){
		System.out.println("model.Crawling.makeSampleGym 시작");
		ArrayList<GymDTO> datas = new ArrayList<GymDTO>();
		//크롤링할 사이트 url
		default_url = "https://map.kakao.com/";
		target_url = "";

		//크롤링 동작 부분
		try {
			//드라이버에 url 주입
			driver.get(default_url);

			//검색창 찾기
			WebElement search = driver.findElement(By.cssSelector(".query"));
			System.out.println("238"+search.getText());

			//검색어 입력
			String searchKeyWord = "클라이밍";
			search.sendKeys(searchKeyWord);
			search.sendKeys(Keys.ENTER);

			//검색어 입력후 1초 대기
			Thread.sleep(1000);
			System.out.println("247 검색어 입력성공");

			//검색후 크롤링
			List<WebElement> gym = driver.findElements(By.cssSelector("#info\\.search\\.place\\.list >li.PlaceItem.clickArea"));
			if(gym.isEmpty()) {
				System.err.println("252 gym 비어있음");
			}

			for(int i=1;i<=gym.size();i++) {
				WebElement detail = driver.findElement(By.cssSelector("li.PlaceItem.clickArea:nth-child("+i+") > div > div > a.moreview"));
				System.out.println("257 "+i+"번째"+detail.getText());

				//상세보기 링크 추출
				target_url = detail.getAttribute("href").replace("..", default_url);
				System.out.println("261 "+i+"번째 href = "+target_url);

				//상세보기 링크 접속
				driver.get(target_url);

				//링크접속후 대기
				Thread.sleep(1000);

				//암벽장별 첫번째 이미지
				String img_url = "default.jpg";
				try {
					WebElement img_elem = driver.findElement(By.cssSelector("#mArticle > div.cont_photo.no_category > div.photo_area > ul > li > a"));

					//url 정제
					img_url = img_elem.getAttribute("style").replace("background-image: url", "").replace("(", "").replace("\"", "").replace("//", "").replace(");", "");
				}catch(NoSuchElementException e) {
					System.err.println("현재 암벽장에 사진이 없습니다");
				}
				System.out.println("279 img_url = "+img_url);

				//이름
				String name = null;
				try {
					WebElement name_elem = driver.findElement(By.cssSelector("div.place_details > div > h2"));
					name = name_elem.getText();
				}catch(NoSuchElementException e) {
					System.err.println("현재 암벽장에 이름이 없습니다");
				}
				System.out.println("289 title = "+name);

				//설명
				String description = null;
				try {
					WebElement description_elem = driver.findElement(By.cssSelector("div.placeinfo_default > div > div > a"));
					description = description_elem.getAttribute("href");
				}catch(NoSuchElementException e) {
					System.err.println("현재 암벽장에 설명이 없습니다");
				}
				System.out.println("299 description = "+description);

				//주소
				String location = null;
				try {
					WebElement location_elem = driver.findElement(By.cssSelector("span.txt_address"));
					location = location_elem.getText();
				}catch(NoSuchElementException e) {
					System.err.println("현재 암벽장에 주소가 없습니다");
				}
				System.out.println("309 location = "+location);

				GymDTO gymDTO = new GymDTO();
				gymDTO.setModel_gym_profile(img_url);
				gymDTO.setModel_gym_name(name);
				gymDTO.setModel_gym_description(description);
				gymDTO.setModel_gym_location(location);
				datas.add(gymDTO);

				// 들어갔던 링크를 빠져나오기 위해
				driver.navigate().back();

				//링크빠져나온후 대기
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println("crawling.makeSampleGym 크롤링 실패");
			e.printStackTrace();
			return datas;
		}
		return datas;
	}
	
	//드라이버 종료
	public void close_driver() {
		if(driver!=null) {
			driver.quit();
		}
	}
}
