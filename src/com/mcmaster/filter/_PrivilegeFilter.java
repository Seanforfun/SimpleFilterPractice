package com.mcmaster.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mcmaster.exception.MyException;
import com.mcmaster.service.LoginService;
import com.mcmaster.vo.Users;

public class _PrivilegeFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String contentPath = httpRequest.getContextPath();
		String uri = httpRequest.getRequestURI();
		String path = uri.substring(contentPath.length());
		
		Users u = (Users) httpRequest.getSession().getAttribute("user");
		
		if (path.equals("/book_add") || path.equals("/book_delete")
				|| path.equals("/book_search") || path.equals("/book_update"))
		{
			if(null != u)
			{
				LoginService service = new LoginService();
				String role;
				try {
					role = service.findRole(u);
					if(role.equals("boss"))
					{
						chain.doFilter(request, response);
					}
					else
					{
						if( path.equals("/book_search"))
						{
							chain.doFilter(httpRequest, httpResponse);
						}
						else
						{
							throw new RuntimeException("No privilage");
						}
					}
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
