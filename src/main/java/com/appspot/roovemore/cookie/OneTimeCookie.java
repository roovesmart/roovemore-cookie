package com.appspot.roovemore.cookie;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OneTimeCookie extends CookieManager{

    protected String oneTimeMsgCookieName = "_onemsg";
    public void setOneTimeMsgCookieName(String oneTimeMsgCookieName) {
		this.oneTimeMsgCookieName = oneTimeMsgCookieName;
	}

    public OneTimeCookie(HttpServletRequest request, HttpServletResponse response) {
    	super(request, response);
    }

	public void setOneTimeValue(String oneTimeValue) throws UnsupportedEncodingException{
		setCookie(oneTimeMsgCookieName, oneTimeValue);
	}

	public String getOneTimeValueAndRemove() throws UnsupportedEncodingException{
		String value = getValue(oneTimeMsgCookieName);
		removeCookie(oneTimeMsgCookieName);
		return value;
	}

}
