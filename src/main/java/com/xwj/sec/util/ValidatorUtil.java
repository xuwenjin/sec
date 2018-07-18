package com.xwj.sec.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式的工具类
 * 
 * @author xuwenjin
 */
public class ValidatorUtil {

	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

	/**
	 * 验证手机号格式
	 */
	public static boolean isMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(mobile);
		return m.matches();
	}

}
