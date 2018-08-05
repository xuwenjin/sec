package com.xwj.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwj.sec.entity.OrderInfo;
import com.xwj.sec.entity.SeckillUser;
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

	@Transactional
	public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
		// 减库存
		boolean success = goodsService.reduceStock(goods);
		if (success) {
			// 下订单，写入秒杀订单
			return orderService.createOrder(user, goods);
		}
		return null;
	}

}
