package com.coma.app.biz.crew_board;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("crew_boardService")
public class Crew_boardService {

    @Autowired
    Crew_boardDAO crew_boardDAO;


    public List<Crew_boardDTO> selectAllNEW10(Crew_boardDTO crew_boardDTO) {
        //Content Delivery Network 주입
        String cdn = "https://comapro.cdn1.cafe24.com";
        List<Crew_boardDTO> datas =  crew_boardDAO.selectAllNEW10(crew_boardDTO);
        for (Crew_boardDTO data : datas) {
            data.setCrew_board_writer_profile(cdn + data.getCrew_board_writer_profile());
        }
        return datas;
    }

    public void insert(Crew_boardDTO crew_boardDTO) {
        boolean flag = crew_boardDAO.insert(crew_boardDTO);
        if (flag) {
            log.info("crew_boardDAO.insert 성공");
        }

        // 모든 데이터 조회
        List<Crew_boardDTO> datas = crew_boardDAO.selectAll(crew_boardDTO);

        // 데이터 개수 체크
        if (datas.size() > 10) {
            // 오래된 데이터 삭제
            for (int i = 0; i < datas.size()-10; i++) {
                crew_boardDTO.setCrew_board_num(datas.get(i).getCrew_board_num());
                crew_boardDAO.delete(crew_boardDTO);
            }
        }
        //트리거가 작동 중인 테이블에서 데이터를 수정하려고 할 때 에러가 발생해
        //트랜잭션 처리로 트리거 대체
    }
}
