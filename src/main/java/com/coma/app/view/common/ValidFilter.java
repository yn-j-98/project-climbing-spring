package com.coma.app.view.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ValidFilter extends OncePerRequestFilter {

	 // 요청이 있을 때마다 호출되는 필터 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
        // 요청 URI와 컨텍스트 경로 가져오기
        String uri = request.getRequestURI();
        String cp = request.getContextPath();
        
        // 요청 명령어를 추출
        String command = uri.substring(cp.length());
        System.out.println("command : " + command);
        
        // 특정 명령어에 대한 로직 처리
        if (command.equals("/boardUpdate.do")) {
        	
            // 세션에서 UPDATE_FOLDER_NUM 값을 가져와 출력
            System.out.println("필터 UPDATE_FOLDER_NUM : " + request.getSession().getAttribute("UPDATE_FOLDER_NUM"));
            
            
        } else {
        	
            // 명령어가 위의 두 가지가 아닐 경우, 세션에서 UPDATE_FOLDER_NUM을 null로 설정
            if (request.getSession().getAttribute("UPDATE_FOLDER_NUM") != null) {
                request.getSession().setAttribute("UPDATE_FOLDER_NUM", null);
            }
        }
        
        // 필터 체인의 다음 필터 또는 서블릿 호출
        filterChain.doFilter(request, response);
    }

    // 필터 종료 시 호출되는 메서드
    @Override
    public void destroy() {
        System.out.println("로그 : 필터 종료");
    }
}
