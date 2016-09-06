package com.appspot.roovemore.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected Integer maxAge = -1;
	protected String path = "/";
	protected String encodeKey = null;

	protected boolean isUseRequestScope = true;

	public CookieManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public void setEncodeKey(String key) {
		this.encodeKey = key;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public void setUseRequestScope(boolean isUseRequestScope) {
		this.isUseRequestScope = isUseRequestScope;
	}

	public void setCookie(String key, String value) throws UnsupportedEncodingException{

		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException("param value = "+value);
		}
		if (encodeKey != null) {
			value = CookieBlowfish.enc(encodeKey, value);
		}

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		response.addCookie(cookie);
		if(isUseRequestScope){
			request.setAttribute(key, value);
		}
	}

	public void removeCookie(Cookie paramCookie) {
		paramCookie.setMaxAge(0);
		paramCookie.setPath(path);
		response.addCookie(paramCookie);
		if(isUseRequestScope){
			request.removeAttribute(paramCookie.getName());
		}
	}

	public void removeCookie(String paramCookieName) {
		Cookie cookie = getCookieObject(paramCookieName);
		if (cookie == null)
			return;
		removeCookie(cookie);
	}

	public Cookie getCookieObject(String paramCookieName) {

		Cookie cookie = null;

		if(isUseRequestScope){
			// リクエストに存在するCookie情報を優先的に参照する。
			cookie = getCookieFromRequestScope(paramCookieName);
			if (cookie != null) {
				return cookie;
			}
		}

		cookie = getCookieFromCookies(paramCookieName);
		return cookie;
	}

	public Cookie getCookieFromRequestScope(String paramCookieName) {
		return (Cookie) request.getAttribute(paramCookieName);
	}

//	public Cookie getCookieFromCookies(String paramCookieName) {
//		return getCookie(paramCookieName);
//	}

	public Cookie getCookieFromCookies(String paramCookieName) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(paramCookieName)) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	public String getValue(Cookie paramCookie) throws UnsupportedEncodingException{

		String ret = null;
		String cookieValue = paramCookie.getValue();
		if (encodeKey != null) {
			cookieValue = CookieBlowfish.dec(encodeKey, cookieValue);
		}
		try {
			ret = URLDecoder.decode(cookieValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException("cookieValue = "+cookieValue);
		}
		return ret;
	}

	public String getValue(String paramCookieName) throws UnsupportedEncodingException{
		Cookie cookie = getCookieObject(paramCookieName);
		if (cookie != null) {
			return getValue(cookie);
		}
		return null;
	}

	public String getValueFromCookie(String paramCookieName) throws UnsupportedEncodingException{
		Cookie cookie = getCookieFromCookies(paramCookieName);
		if (cookie != null) {
			return getValue(cookie);
		}
		return null;
	}

	public String getValueFromRequest(String paramCookieName) throws UnsupportedEncodingException{
		Cookie cookie = getCookieFromRequestScope(paramCookieName);
		if (cookie != null) {
			return getValue(cookie);
		}
		return null;
	}

}
