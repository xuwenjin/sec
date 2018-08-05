package com.xwj.sec.redis;

public class GoodsKey extends BasePrefix {

	public GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public static final GoodsKey getSeckillGoodsStock = new GoodsKey(0, "gd");

}
