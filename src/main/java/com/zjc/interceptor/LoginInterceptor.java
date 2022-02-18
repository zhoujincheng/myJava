package com.zjc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录过滤器
 * @author 周金城
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler)throws Exception {
			if(request.getSession().getAttribute("user")==null) {		
				response.sendRedirect("/amin");
				return false;
			}
		return true;
	}

	

}
