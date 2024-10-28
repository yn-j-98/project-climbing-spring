package com.coma.app.biz.common;

import java.util.ArrayList;

import com.coma.app.biz.board.BoardService;
import com.coma.app.biz.board.BoardDTO;
import com.coma.app.biz.gym.GymService;
import com.coma.app.biz.gym.GymDTO;
import com.coma.app.biz.product.ProductDTO;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleListener {

    private final BoardService boardService;
    private final GymService gymService;
    private final Crawling2 crawling;
    private final ServletContext servletContext; //어플리케이션에 product정보를 담기위해 사용
    private final ApplicationContext applicationContext; //빈 파괴를 위해 사용

    @Autowired
    public SampleListener(BoardService boardService, GymService gymService, Crawling2 crawling, ServletContext servletContext, ApplicationContext applicationContext) {
        //필드 주입보다는 생성자 주입이 더 권장되는 방식이며, 테스트와 유지보수 측면에서도 생성자 주입이 더 유리
        //컨테이너가 주입하지않기때문에 테스트 하기에 유리, 의존 주입 강제로 잠재적 에러 방지
        this.boardService = boardService;
        this.gymService = gymService;
        this.crawling = crawling;
        this.servletContext = servletContext;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        //PostConstruct : 스프링 컨텍스트 초기화 된 이후에 실행
        //Application Context : 스프링 Context 기능의 중심인 최상위 인터페이스

        //SerlvetContext : 자바 자체의 Context를 말함
        //스프링도 자바로 만들어졌으므로, 모든 스프링 Context는 ServletContext

        //applicationContext.xml 초기화 : 애플리케이션 전반에 걸친 모든 서비스, 리포지토리, 비즈니스 로직 빈이 생성되고 의존성 주입
        //의존성이 주입된 후 각 빈에 대해 @PostConstruct 메서드가 실행

        //이후 DispatcherServlet 초기화 진행
        log.info("com.coma.app.biz.common.SampleListener 시작");

        ArrayList<ProductDTO> product_datas = crawling.makeSampleProduct();
        // 상품 크롤링 부분
        int PK = 0; // 자동부여하는 PK
        for (ProductDTO product_data : product_datas) {
            product_data.setProduct_num(PK);
            PK++;
        }

        // 전체 게시판 크롤링 부분
        BoardDTO boardDTO = new BoardDTO();
        BoardDTO data = boardService.selectOneCount(boardDTO);
        if (data.getTotal() <= 0) { //게시글 데이터가 하나도 없다면 크롤링 동작
            log.info("com.coma.app.biz.common.SampleListener.makeSampleBoard 크롤링 시작");
            ArrayList<BoardDTO> crawling_board_datas = crawling.makeSampleBoard();
            for (BoardDTO board_data : crawling_board_datas) {
                boardService.insert(board_data);
            }
        }


        // 암벽장 크롤링 부분
        GymDTO gymDTO = new GymDTO();
        GymDTO data2 = gymService.selectOneCount(gymDTO);
        if (data2.getTotal() <= 0) { //암벽장 데이터가 하나도 없다면 크롤링 동작
            log.info("com.coma.app.biz.common.SampleListener.makeSampleGym 크롤링 시작");
            ArrayList<GymDTO> crawling_gym_datas = crawling.makeSampleGym();
            for (GymDTO gym_data : crawling_gym_datas) {
                gymService.insert(gym_data);
            }
        }
		// 암벽장 크롤링 부분
		GymDTO gymDTO = new GymDTO();
		GymDTO data2 = gymDAO.selectOneCount(gymDTO);
		if (data2.getTotal() <= 0) {
			System.out.println("com.coma.app.biz.common.SampleListener.makeSampleGym 크롤링 시작");
			ArrayList<GymDTO> crawling_gym_datas = crawling.makeSampleGym();
			for (GymDTO gym_data : crawling_gym_datas) {
				gymDAO.insert(gym_data);
			}
		}

        // 어플리케이션단위로 보내려면 ServletContextEvent 사용해서 전달해야 한다
        servletContext.setAttribute("product_datas", product_datas);
        if (crawling != null) {
            crawling.close_driver();
        }
        log.info("com.coma.app.biz.common.SampleListener 끝");

        //수동 빈 파괴 메서드
        destroy();
    }

    @PreDestroy
    public void cleanup() {
        log.info("sampleListener 생명주기가 끝났을때 나오는 로그 = sampleListener 완전 파괴");
    }

    public void destroy() {
        log.info("SampleListener 제거");
        if (applicationContext instanceof ConfigurableApplicationContext) {
            //ConfigurableApplicationContext : ApplicationContext의 서브인터페이스
            //동적으로 빈을 등록하거나 제거할 수 있는 메서드 제공
            ((ConfigurableApplicationContext) applicationContext).getBeanFactory().destroyBean(this);
            //타입 캐스팅을 통해 더 구체적인 타입으로 변환할 때 괄호 사용
            //다운 캐스팅
            log.info("SampleListener 빈 ApplicationContext에서 제거완료");
        }
    }
}