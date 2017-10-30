package com.mcmaster.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mcmaster.DatabaseUtils.DataSourceUtils;
import com.mcmaster.exception.MyException;
import com.mcmaster.vo.Users;

public class LoginDao {

	public Users userLogin(Users user) throws MyException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password = ?";
		Users resultUser = null;
		try {
			resultUser = runner.query(sql, new BeanHandler<Users>(Users.class), user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			throw new MyException(e.getMessage());
		}
		
		return resultUser;
	}

	public String findRole(Users users) throws MyException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password = ?";
		Users ret = null;
		try {
			ret = runner.query(sql, new BeanHandler<Users>(Users.class), users.getUsername(), users.getPassword());
		} catch (SQLException e) {
			throw new MyException("findRole Exception: " + e.getMessage());
		}
		
		return ret.getRole();
	}

}
