package com.xwj.sec.access;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

	int seconds(); // 超时时间(秒)

	int maxCount(); // 最大访问次数

	boolean needLogin() default true; // 是否需要登录
}
