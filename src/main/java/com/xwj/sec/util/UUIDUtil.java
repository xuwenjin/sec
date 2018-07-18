package com.xwj.sec.util;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author xuwenjin
 */
public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
