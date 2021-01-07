package com.xwj.sec.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwj.sec.dao.OrderDao;
import com.xwj.sec.entity.OrderInfo;
import com.xwj.sec.entity.SeckillOrder;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.redis.OrderKey;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.vo.GoodsVo;

/**
 * 订单服务
 * 
 * @author xuwenjin
 */
@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private RedisService redisService;

	/**
	 * 通过用户id、商品id查询秒杀订单
	 */
	public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
		return redisService.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
	}

	/**
	 * 生成订单、秒杀订单
	 */
	@Transactional
	public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
		// 生成订单
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSeckillPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderInfo.setCreateTime(new Date());
		orderDao.insert(orderInfo);

		// 生成秒杀订单
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderInfo.getId());
		seckillOrder.setUserId(user.getId());
		orderDao.insertSeckillOrder(seckillOrder);

		// 写入redis
		redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), seckillOrder);

		return orderInfo;
	}

}
