package com.xwj.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwj.sec.entity.OrderInfo;
import com.xwj.sec.entity.SeckillOrder;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.redis.SeckillKey;
import com.xwj.sec.redis.SeckillUserKey;
import com.xwj.sec.util.MD5Util;
import com.xwj.sec.util.UUIDUtil;
import com.xwj.sec.vo.GoodsVo;

/**
 * 秒杀服务
 * 
 * @author xuwenjin
 */
@Service
public class SeckillService {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RedisService redisService;

	/**
	 * 执行秒杀逻辑
	 */
	@Transactional
	public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
		// 1、减库存
		boolean success = goodsService.reduceStock(goods);
		if (success) {
			// 2、扣减库存成功，表示还有库存。下订单，写入秒杀订单
			return orderService.createOrder(user, goods);
		} else {
			// 3、扣减库存失败，表示没有库存了，缓存该状态
			setGoodsOver(goods.getId());
		}
		return null;
	}

	/**
	 * 设置该商品卖完了
	 */
	private void setGoodsOver(long goodsId) {
		redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
	}

	/**
	 * 查看该商品是否卖完了
	 */
	private boolean getGoodsOver(long goodsId) {
		return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
	}

	/**
	 * 生成秒杀地址
	 */
	public long getSeckillResult(SeckillUser user, long goodsId) {
		SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			// 秒杀成功
			return order.getOrderId();
		} else {
			boolean isOver = getGoodsOver(goodsId);
			if (isOver) {
				// 没有库存了，秒杀失败
				return -1;
			} else {
				// 还有库存，客户端轮询查询秒杀结果
				return 0;
			}
		}
	}

	/**
	 * 生成秒杀地址
	 */
	public String createSeckillPath(SeckillUser user, long goodsId) {
		String path = MD5Util.md5(UUIDUtil.uuid() + "123456");
		redisService.set(SeckillUserKey.getSeckillPath, user.getId() + "_" + goodsId, path);
		return path;
	}

	/**
	 * 校验秒杀地址
	 */
	public boolean checkSeckillPath(SeckillUser user, long goodsId, String path) {
		if (user == null || path == null) {
			return false;
		}
		String cachePath = redisService.get(SeckillUserKey.getSeckillPath, user.getId() + "_" + goodsId, String.class);
		return path.equals(cachePath);
	}

}
