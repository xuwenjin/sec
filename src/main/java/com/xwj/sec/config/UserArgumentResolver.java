package com.xwj.sec.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xwj.sec.access.UserContext;
import com.xwj.sec.entity.SeckillUser;

/**
 * 自定义参数解析器
 * 
 * @author xuwenjin
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

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
		return UserContext.getUser();
	}

}
