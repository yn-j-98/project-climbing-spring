package com.coma.app.view.common;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("*.do")
public class ValidFilter extends HttpFilter implements Filter {
       
    public ValidFilter() {
        super();
    }

	public void destroy() {
		System.out.println("로그 : 필터 종료");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String uri=httpRequest.getRequestURI();
		String cp=httpRequest.getContextPath();
		String command=uri.substring(cp.length());
		System.out.println("command : "+command);
		
		if(command.equals("/BOARDUPDATEPAGEACTION.do")) {
			System.out.println("필터 UPDATE_FOLDER_NUM : "+httpRequest.getSession().getAttribute("UPDATE_FOLDER_NUM"));
		}
		else if(command.equals("/BOARDUPDATEACTION.do")){
			System.out.println("필터 UPDATE_FOLDER_NUM : "+httpRequest.getSession().getAttribute("UPDATE_FOLDER_NUM"));			
		}
		else {
			if(httpRequest.getSession().getAttribute("UPDATE_FOLDER_NUM") != null) {
				httpRequest.getSession().setAttribute("UPDATE_FOLDER_NUM", null);
			}
		}
		
		chain.doFilter(request, response); 
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("로그 : 필터 시작");
	}

}
