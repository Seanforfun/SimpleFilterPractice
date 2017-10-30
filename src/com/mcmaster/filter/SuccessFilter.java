package com.mcmaster.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mcmaster.cookieUtils.CookieUtils;
import com.mcmaster.exception.MyException;
import com.mcmaster.service.LoginService;
import com.mcmaster.vo.Users;

public class SuccessFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		String path = uri.substring(contextPath.length());
		
		if(!(path.equals("/loginSuccess/success.jsp") || path.equals("/login")))
		{
			Cookie[] cookies = httpRequest.getCookies();
			Cookie cookie = CookieUtils.getCookieByName(cookies, "userInfo");
			
			if(null != cookie)
			{
				String userInfo = cookie.getValue();
				String[] tokens = userInfo.split(":");
				String username = tokens[0];
				String password = tokens[1];
				Users u = new Users();
				u.setUsername(username);
				u.setPassword(password);
				
				LoginService service = new LoginService();
				try {
					Users userLogin = service.userLogin(u);
					httpRequest.getSession().setAttribute("user", userLogin);
				} catch (MyException e) {
					e.printStackTrace();
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
