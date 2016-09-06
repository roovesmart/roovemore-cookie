package com.appspot.roovemore.cookie;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneralCookie extends CookieManager{

	protected String generalCookieName = "_general";
	public void setModeCookieName(String cookieName) {
		this.generalCookieName = cookieName;
	}

	public GeneralCookie(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	public void setGeneralCookie(String value) throws UnsupportedEncodingException{
		setMaxAge(ConstCookie.ONE_MONTH);
		setCookie(generalCookieName,  value);
	}
	public String getGeneralCookie() throws UnsupportedEncodingException{
		return getValue(generalCookieName);
	}

}
