package com.mcmaster.service;

import com.mcmaster.dao.LoginDao;
import com.mcmaster.exception.MyException;
import com.mcmaster.vo.Users;

public class LoginService {

	public Users userLogin(Users user) throws MyException {
		LoginDao dao = new LoginDao();
		Users ret = dao.userLogin(user);
		return ret;
	}
	
	public String findRole(Users users) throws MyException
	{
		LoginDao dao = new LoginDao();
		String role = dao.findRole(users);
		return role;
	}

}
