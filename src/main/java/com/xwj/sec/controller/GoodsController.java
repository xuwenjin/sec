package com.xwj.sec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.service.GoodsService;
import com.xwj.sec.service.SeckillUserService;
import com.xwj.sec.vo.GoodsVo;

/**
 * 商品-控制层
 * 
 * @author xuwenjin
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private SeckillUserService userService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private GoodsService goodsService;

	/**
	 * 查询商品列表
	 */
	@RequestMapping("/to_list")
	public String list(Model model, SeckillUser user) {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}

	/**
	 * 查看详情
	 */
	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model, SeckillUser user, @PathVariable("goodsId") long goodsId) {
		model.addAttribute("user", user);

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartTime().getTime();
		long endAt = goods.getEndTime().getTime();
		long now = System.currentTimeMillis();

		int seckillStatus = 0; //秒杀状态
		int remainSeconds = 0; //秒杀剩余时间(秒)
		if (now < startAt) {// 秒杀还没开始，倒计时
			seckillStatus = 0;
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {// 秒杀已经结束
			seckillStatus = 2;
			remainSeconds = -1;
		} else {// 秒杀进行中
			seckillStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("seckillStatus", seckillStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}

}
