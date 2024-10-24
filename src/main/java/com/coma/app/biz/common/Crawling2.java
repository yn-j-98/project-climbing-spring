package com.coma.app.biz.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.product.ProductDTO;


public class Crawling2 {
   public static WebDriver driver;
   private String target_url;
   private String default_url;
   WebDriverWait wait = null;
   public Crawling2() {
      // 크롬 옵션 설정
      ChromeOptions options = new ChromeOptions();
      // 헤드리스 모드 추가 (코드 실행시 크롬창이 뜨지않게 함)
      //options.addArguments("--headless");
      // 팝업창 제거 옵션 추가
      options.addArguments("--disable-popup-blocking");
      //GPU 가속 비활성화
      options.addArguments("--disable-gpu");
      //샌드박스 비활성화
      options.addArguments("--no-sandbox");

      // 옵션설정한 ChromeDriver 인스턴스 생성
      driver = new ChromeDriver(options);
      // 2초 동안 특정 조건을 기다리는 WebDriverWait 인스턴스를 생성
      wait = new WebDriverWait(driver, Duration.ofSeconds(2));
   }

   public ArrayList<ProductDTO> makeSampleProduct() {
      System.out.println("model.Crawling.makeSampleProduct 시작");
      ArrayList<ProductDTO> datas = new ArrayList<ProductDTO>();
      //크롤링할 사이트 url
      default_url = "https://spiri7.com";
      target_url = "";
      //css tag
      //추천상품 더보기 버튼
      String product_button = "#__next > main > div.card.mb-11.w-full > div.px-5.flex.justify-between.items-end.mb-2.font-KoPubWorldDotum > button";
      //추천상품속 암벽화 페이지
      String rock_button = "a:nth-child(1) > div.font-bold.text-xs";
      //암벽화 개수 더보기 버튼
      String see_button = "div.list-content.flex > div > div > div > svg";
      //전체 00개 상품
      String product_num = "div.list-header.flex.justify-between.items-center > div";
      //상품의 상세보기 링크
      String product_href= "div.list-content.flex.flex-wrap > a";
      //상품 이름
      String product_name = "#__next > main > div:nth-child(3) > h1";
      //상품 원가
      String product_default_price ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.flex.items-center > span.text-gray-500.text-sm.font-normal.truncate.line-through";
      //상품 할인율
      String product_discount_rate ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.flex.items-center > span.text-red-400.text-base.font-bold.truncate.mr-1";
      //상품 할인가
      String product_discount_price ="#__next > main > div:nth-child(3) > div:nth-child(4) > div.text-red-400.text-xl.font-bold.truncate";
      //상품 이미지
      String product_img = "#__next > main > div.relative.w-full.bg-white > section > div > div.slick-list > div > div.slick-slide.slick-active.slick-current > div > div > div > span > img";

      //크롤링 동작 부분
      try {
         //드라이버에 url 주입
         driver.get(default_url);

         //추천상품 더보기 버튼 찾기
         //WebElement button = driver.findElement(By.cssSelector(product_button));
         WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(product_button)));
         //버튼 클릭
         button.click();

         //암벽화 버튼 선택
         //클릭이 가능해질때까지 대기
         while(true) {
            try {
               WebElement product_rock_button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(rock_button)));
               product_rock_button.click();

               //요소가 있다면 while 문 종료
               if(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(rock_button)))) {
                  break;
               }
            }catch (Exception e) {
               System.err.println("암벽화페이지 버튼 없음");
            }
         }

         // 변경된 URL을 저장
         target_url = driver.getCurrentUrl();
         System.out.println("87 target_url ="+target_url);

         //JAVA에서 1초 대기
         Thread.sleep(1000);

         //전체 상품 개수 받아오기
         WebElement product_total = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_num)));
         System.out.println(product_total.getText());

         int product_total_num = Integer.parseInt(product_total.getText().replace("전체 ", "").replace("개 상품", ""));

         //페이지 이동후 크롤링
         List<WebElement> product = new ArrayList<>();
         WebElement product_see_button = null;
         System.out.println(product_total_num);         
         for(int j = 0; j < product_total_num%10; j++) {
            try {
               //상품 더보기 클릭
               product_see_button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(see_button)));
               //elementToBeClickable : CSS 선택자에 해당하는 요소가 클릭 가능한 상태가 될 때까지 대기
               //'더보기'가 클릭될때까지 대기
               product_see_button.click();
               //상품 개수가 모두 로딩 될때까지 대기

               product = wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(product_href),product_total_num));
               //numberOfElementsToBe : 주어진 CSS 선택자에 해당하는 요소의 수가 지정된 숫자(product_total_num)와 같아질 때까지 대기
               //상품의 상세보기링크 개수와 상품 전체 개수가 같아질때까지 대기

               //매개변수
               //By : 요소를 찾기 위한 선택자 (상세보기 링크)
               //int : 기다릴 요소의 개수 (상품 전체 개수)

               //상품이 있다면 종료
               if(product != null) {
                  break;                  
               }
            }catch (Exception e) {
               //오류나면 반복
            }
         }

         //wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(product_href),product_total_num));
         System.out.println("    [로그] (product.size() : "+product.size());

         for(int i=0; i < product.size(); i++) {      
//            if(datas.size()>=5) { //FIXME
//               //샘플 5개만
//               return datas;
//            }
            //링크 파밍
            try {
               System.out.println((i+1)+"번째 요소");
               String tag = product.get(i).getAttribute("href");
               product.get(i).click();
               try {
                  //상품 이름
                  //List<WebElement> productName = driver.findElements(By.cssSelector(product_name));
                  WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_name)));

                  //상품 이미지
                  //List<WebElement> productImg = driver.findElements(By.cssSelector(product_img));
                  WebElement productImg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_img)));

                  //상품 원가
                  //List<WebElement> productDefaultPrice = driver.findElements(By.cssSelector(product_default_price));
                  WebElement productDefaultPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_default_price)));

                  //상품 할인율
                  //List<WebElement> productDiscountRate = driver.findElements(By.cssSelector(product_discount_rate));
                  WebElement productDiscountRate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_discount_rate)));

                  //상품 할인된 가격
                  //List<WebElement> productDiscountPrice = driver.findElements(By.cssSelector(product_discount_price));
                  WebElement productDiscountPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(product_discount_price)));

                  ProductDTO productDTO = new ProductDTO();
                  productDTO.setProduct_name(productName.getText());
                  productDTO.setProduct_profile(productImg.getAttribute("src"));
                  productDTO.setProduct_discount_rate(productDiscountRate.getText());
                  productDTO.setProduct_default_price(Integer.parseInt(productDefaultPrice.getText().replace("원", "").replace(",", "")));
                  productDTO.setProduct_discount_price(Integer.parseInt(productDiscountPrice.getText().replace("원신규회원", "").replace(",", "")));
                  productDTO.setProduct_link(tag);//상품 링크 FIXME
                  System.out.println("이미지 = "+productDTO.getProduct_profile());
                  datas.add(productDTO);

                  // 들어갔던 링크를 빠져나오기 위해
                  driver.navigate().back();
                  //               driver.get(target_url);
               }
               catch (Exception e) {
                  System.out.println("상품 정보 로드 실패");
                  System.out.println("실패 주소 = "+ tag);
                  // 들어갔던 링크를 빠져나오기 위해
                  driver.navigate().back();
                  //               driver.get(target_url);
               }

            } catch (Exception e) {
               System.out.println("상품 주소 불러오기 실패");
            }
            //안정성 확보
            //StaleElementReferenceException 발생 가능성
            //driver.navigate().back() 코드가 실행되면서 페이지가 다시 로드되어 발생
            //페이지가 뒤로 이동하면 원래 있던 페이지에서 찾은 요소가 유효하지 않게 되어 무효한 요소를 참조하려고 하여 발생
            //페이지가 로드된 후 다시 요소를 검색하여 최신 상태로 갱신
            for(int j = 0; j < product_total_num%10; j++) {
               try {
                  System.out.println("요소 다시 검색 "+(j+1)+"번째");
                  product = wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(product_href),product_total_num));
                  //numberOfElementsToBe : 주어진 CSS 선택자에 해당하는 요소의 수가 지정된 숫자(product_total_num)와 같아질 때까지 대기
                  //상품의 a태그의 개수와 상품 전체 개수가 같아질때까지 대기

                  product_see_button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(see_button)));
                  product_see_button.click();                  
               }catch (ElementClickInterceptedException e) {
                  System.out.println("요소 클릭이 안됨");
                  continue;
               }
            }

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
      //크롤링 동작 부분
      try {
         //드라이버에 url 주입
         driver.get(default_url);

         System.out.println("127 default_url = "+default_url);
         //최신글 더보기 버튼 찾기
         //WebElement button = driver.findElement(By.cssSelector(board_button));         
         WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(board_button)));

         //버튼 클릭
         button.click();

         // 변경된 URL을 저장
         target_url = driver.getCurrentUrl();
         System.out.println("135 target_url ="+target_url);

         //페이지 이동후 크롤링
         //List<WebElement> board = driver.findElements(By.cssSelector(board_href));
         List<WebElement> board = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(board_href)));

         System.out.println("143");
         if(board.isEmpty()) {
            System.err.println("144 board비어있음");
         }
         //System.out.println("147 ="+board.get(6).getText());
         int i;
         for(i = 0; i < board.size(); i++) {
            board = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(board_href)));
            try {
               // 현재 게시물의 div 요소를 찾기 ( 이 페이지에서 div를 클릭해야 게시글 상세 페이지로 이동 )
               WebElement board_div = board.get(i).findElement(By.cssSelector(board_divs));
               // div 클릭
               board_div.click();

               //페이지 이동후 크롤링
               //제목
               WebElement board_div_detail_title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(board_title)));

               //내용
               WebElement board_div_detail_content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(board_content)));

               //작성자
               String board_div_detail_writer = "coma@naver.com";

               BoardDTO boardDTO = new BoardDTO();
               boardDTO.setBoard_title(board_div_detail_title.getText());
               boardDTO.setBoard_content(board_div_detail_content.getText());
               boardDTO.setBoard_writer_id(board_div_detail_writer);
               datas.add(boardDTO);

               // 들어갔던 링크에서 나가기
               driver.navigate().back();
            } catch (Exception e) {
               // TODO: handle exception
            }
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
         WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".query")));
         System.out.println("238"+search.getText());

         //검색어 입력
         String searchKeyWord = "클라이밍";
         search.sendKeys(searchKeyWord);
         search.sendKeys(Keys.ENTER);

         System.out.println("247 검색어 입력성공");

         //검색후 크롤링
         List<WebElement> gym = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#info\\.search\\.place\\.list >li.PlaceItem.clickArea")));
         if(gym.isEmpty()) {
            System.err.println("252 gym 비어있음");
         }
         System.out.println(gym.size());
         for(int i=1;i<=gym.size();i++) {
            WebElement detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.PlaceItem.clickArea:nth-child("+i+") > div > div > a.moreview")));
            System.out.println("257 "+i+"번째"+detail.getText());

            //상세보기 링크 추출
            target_url = detail.getAttribute("href").replace("..", default_url);
            System.out.println("261 "+i+"번째 href = "+target_url);

            //상세보기 링크 접속
            driver.get(target_url);

            //암벽장별 첫번째 이미지
            String img_url = "default.jpg";
            try {
               WebElement img_elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mArticle > div.cont_photo.no_category > div.photo_area > ul > li > a")));

               //url 정제
               img_url = img_elem.getAttribute("style").replace("background-image: url", "").replace("(", "").replace("\"", "").replace("//", "").replace(");", "");
            }catch(NoSuchElementException e) {
               System.err.println("현재 암벽장에 사진이 없습니다");
            }
            System.out.println("279 img_url = "+img_url);

            //이름
            String name = null;
            try {
               WebElement name_elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.place_details > div > h2")));
               name = name_elem.getText();
            }catch(NoSuchElementException e) {
               System.err.println("현재 암벽장에 이름이 없습니다");
            }
            System.out.println("289 title = "+name);

            //설명
            String description = null;
            try {
               WebElement description_elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.placeinfo_default > div > div > a")));
               description = description_elem.getAttribute("href");
            }catch(NoSuchElementException e) {
               System.err.println("현재 암벽장에 설명이 없습니다");
            }catch (TimeoutException e) {
               System.err.println("현재 암벽장에 설명이 없습니다");
            }
            System.out.println("299 description = "+description);

            //주소
            String location = null;
            try {
               WebElement location_elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.txt_address")));
               location = location_elem.getText();
            }catch(NoSuchElementException e) {
               System.err.println("현재 암벽장에 주소가 없습니다");
            }
            System.out.println("309 location = "+location);

            GymDTO gymDTO = new GymDTO();
            gymDTO.setGym_profile(img_url);
            gymDTO.setGym_name(name);
            gymDTO.setGym_description(description);
            gymDTO.setGym_location(location);
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
   //크롤링 테스트   
   //      public static void main(String[] args) {
   //         Crawling3 crawling = new Crawling3();
   //         ArrayList<BoardDTO> boardDTO_datas = crawling.makeSampleBoard();
   //         ArrayList<GymDTO> gymDTO_datas = crawling.makeSampleGym();
   //         ArrayList<ProductDTO> productDTO_datas = crawling.makeSampleProduct();
   //         System.out.println("게시글 크롤링 완료 : "+boardDTO_datas);
   //         System.out.println("암벽장 크롤링 완료 : "+gymDTO_datas);
   //         System.out.println("상품 크롤링 완료 : "+productDTO_datas);
   //         System.out.println("상품 크롤링 완료 : "+productDTO_datas.size());
   //         crawling.close_driver();
   //      }
}
