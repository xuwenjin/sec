package com.xwj.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xwj.sec.entity.OrderInfo;
import com.xwj.sec.entity.SeckillOrder;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.result.CodeMsg;
import com.xwj.sec.service.GoodsService;
import com.xwj.sec.service.OrderService;
import com.xwj.sec.service.SeckillService;
import com.xwj.sec.service.SeckillUserService;
import com.xwj.sec.vo.GoodsVo;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Autowired
	private SeckillUserService userService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private SeckillService seckillService;

	/**
	 * 秒杀
	 */
	@RequestMapping("/do_seckill")
	public String list(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return "login";
		}
		// 判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if (stock <= 0) {
			model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
			return "seckill_fail";
		}
		// 判断是否已经秒杀到了
		SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
			return "seckill_fail";
		}
		// 减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = seckillService.seckill(user, goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}
}
