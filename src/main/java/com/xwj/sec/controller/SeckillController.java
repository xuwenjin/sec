package com.xwj.sec.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwj.sec.access.AccessLimit;
import com.xwj.sec.entity.SeckillOrder;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.rabbitmq.MqSender;
import com.xwj.sec.rabbitmq.SeckillMsg;
import com.xwj.sec.redis.GoodsKey;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.result.CodeMsg;
import com.xwj.sec.result.Result;
import com.xwj.sec.service.GoodsService;
import com.xwj.sec.service.OrderService;
import com.xwj.sec.service.SeckillService;
import com.xwj.sec.vo.GoodsVo;

@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

	@Autowired
	private SeckillService seckillService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MqSender sender;

	private Map<Long, Boolean> localOverMap = new ConcurrentHashMap<Long, Boolean>();

	/**
	 * 服务启动时加载
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		goodsList.forEach(d -> {
			redisService.set(GoodsKey.getSeckillGoodsStock, "" + d.getId(), d.getStockCount());
			localOverMap.put(d.getId(), false);
		});
	}

	/**
	 * 获取秒杀地址
	 */
	@AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
	@RequestMapping(value = "/path", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaPath(HttpServletRequest request, SeckillUser user, @RequestParam("goodsId") long goodsId, @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		// TODO 验证下验证码verifyCode

		// 生成秒杀path
		String path = seckillService.createSeckillPath(user, goodsId);
		return Result.success(path);
	}

	/**
	 * 秒杀
	 */
	@RequestMapping("/{path}/do_seckill")
	public Result<Integer> seckill(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId, @PathVariable String path) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}

		// 校验秒杀地址
		boolean check = seckillService.checkSeckillPath(user, goodsId, path);
		if (!check) {
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}

		// 标记该商品是否秒杀完成，减少redis访问
		boolean over = localOverMap.get(goodsId);
		if (over) {
			return Result.error(CodeMsg.SECKILL_OVER);
		}

		// 预减库存
		Long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
		if (stock < 0) {
			localOverMap.put(goodsId, true);
			return Result.error(CodeMsg.SECKILL_OVER);
		}
		// 判断是否已经秒杀到了
		SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			return Result.error(CodeMsg.REPEATE_SECKILL);
		}
		// 入队
		SeckillMsg msg = new SeckillMsg(user, goodsId);
		sender.sendSeckillMsg(msg);
		return Result.success(0); // 排队中
	}

	/**
	 * 查询秒杀结果
	 * 
	 * @return 返回orderId：秒杀成功，-1：秒杀失败，0：排队中
	 */
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> getMiaoshaResult(SeckillUser user, @RequestParam("goodsId") long goodsId) {
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = seckillService.getSeckillResult(user, goodsId);
		return Result.success(result);
	}

}
