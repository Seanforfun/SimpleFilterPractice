package com.mcmaster.cookieUtils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	public static Cookie getCookieByName(Cookie[] cookies, String name)
	{
		if(null == cookies || name.length() == 0)
		{
			return null;
		}
		else
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals(name))
				{
					return c;
				}
			}
		}
		
		return null;
	}
}
