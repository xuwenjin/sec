package com.xwj.sec.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.service.SeckillUserService;

/**
 * 自定义参数解析器
 * 
 * @author xuwenjin
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private SeckillUserService userService;

	/**
	 * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType(); // 获取参数类型
		return clazz == SeckillUser.class;
	}

	/**
	 * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象
	 */
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 获取request和response
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

		String paramToken = request.getParameter(SeckillUserService.COOKI_NAME_TOKEN); // 从请求中获取token
		String cookieToken = getCookieValue(request, SeckillUserService.COOKI_NAME_TOKEN);// 从cookie中获取token
		if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
		return userService.getByToken(response, token);
	}

	/**
	 * 获取cookie信息
	 */
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookiName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
