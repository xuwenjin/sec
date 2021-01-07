package com.xwj.sec.redis;

/**
 * 秒杀键
 * 
 * @author xuwenjin
 */
public class SeckillKey extends BasePrefix {

	public static SeckillKey isGoodsOver = new SeckillKey("go");

	private SeckillKey(String prefix) {
		super(prefix);
	}

}
