package com.mcmaster.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

public class PrivilegeFilter implements Filter {
	private List<String> admin;
	private List<String> boss;

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
		
		if (admin.contains(path) || boss.contains(path))
		{
			if(null != u)
			{
				LoginService service = new LoginService();
				String role;
				try {
					role = service.findRole(u);
					if(role.equals("boss"))
					{
						if(boss.contains(path))
							chain.doFilter(request, response);
					}
					else
					{
						if(admin.contains(path))
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
		this.admin = new ArrayList<String>();
		this.boss = new ArrayList<>();
		
		this.fillPath("boss", boss);
		this.fillPath("admin", admin);
	}
	
	private void fillPath(String baseName, List<String> list)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		String path = bundle.getString("url");
		String[] paths = path.split(",");
		
		for(String p:paths)
		{
			list.add(p);
		}
	}

}
