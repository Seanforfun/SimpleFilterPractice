package com.mcmaster.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.mcmaster.exception.MyException;
import com.mcmaster.service.LoginService;
import com.mcmaster.vo.Users;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Users user = new Users();
		Map<String, String[]> map = request.getParameterMap();
		
		try {
			BeanUtils.populate(user, map);
			LoginService service = new LoginService();
			try {
				Users ret = service.userLogin(user);
				if(null == ret)
				{
					System.out.println("User doesn't exist");
					request.setAttribute("loginresult", "Username or password incorrect!");
					request.getRequestDispatcher("/login/login.jsp").forward(request, response);
				}
				else
				{
					String check = request.getParameter("remember");
					
					if("ok".equals(check))
					{
						String userInfo = ret.getUsername() + ":" + ret.getPassword();
						Cookie c = new Cookie("userInfo", userInfo);
						response.addCookie(c);
					}
					
					request.getSession().setAttribute("user", ret);
					response.sendRedirect(request.getContextPath() + "/loginSuccess/success.jsp");
				}
			} catch (MyException e) {
				request.setAttribute("loginresult", e.getMessage());
				request.getRequestDispatcher("/login/login.jsp").forward(request, response);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
