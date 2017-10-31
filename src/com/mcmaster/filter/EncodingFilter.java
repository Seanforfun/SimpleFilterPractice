package com.mcmaster.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpServletRequest myRequest = new MyRequest(req);
		chain.doFilter(myRequest, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}

class MyRequest extends HttpServletRequestWrapper
{
	private HttpServletRequest request = null;
	
	public MyRequest(HttpServletRequest request)
	{
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		if(name == null)
			return null;
		
		Map<String, String[]> map = getParameterMap();
		return map.get(name)[0];
	}
	
	@Override
	public String[] getParameterValues(String name) {
		if(null == name)
			return null;
		
		return getParameterMap().get(name);
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = this.request.getParameterMap();
		for(String key: map.keySet())
		{
			String[] values = map.get(key);
			
			for(int i = 0; i < values.length; i++)
			{
				try {
					values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
					System.out.println(values[i]);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		return map;
	}
}
