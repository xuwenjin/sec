package com.xwj.sec.redis;

/**
 * 秒杀-用户键
 * 
 * @author xuwenjin
 */
public class SeckillUserKey extends BasePrefix {

	public static final int TOKEN_EXPIRE = 3600 * 24 * 2; // 过期时间：2天

	public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE, "tk");
	public static SeckillUserKey getById = new SeckillUserKey(0, "gd"); // 不过期
	public static SeckillUserKey getSeckillPath = new SeckillUserKey(60, "gsp");

	private SeckillUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}
