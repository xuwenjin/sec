package com.xwj.sec.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwj.sec.entity.SeckillOrder;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.service.GoodsService;
import com.xwj.sec.service.OrderService;
import com.xwj.sec.service.SeckillService;
import com.xwj.sec.util.JsonUtil;
import com.xwj.sec.vo.GoodsVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息消费者
 * 
 * @author xuwenjin
 */
@Service
@Slf4j
public class MqReceiver {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SeckillService seckillService;

	@RabbitListener(queues = MqConfig.SECKILL_QUEUE_NAME)
	public void receive(String message) {
		log.info("receive message: " + message);
		
		SeckillMsg seckillMsg = JsonUtil.stringToBean(message, SeckillMsg.class);
		SeckillUser user = seckillMsg.getUser();
		long goodsId = seckillMsg.getGoodsId();

		// 查询库存
		GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
		if (goodsVo.getStockCount() < 0) {
			return;
		}
		// 判断是否已经秒杀到了
		SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			return;
		}
		seckillService.seckill(user, goodsVo);
	}

	// @RabbitListener(queues = MqConfig.QUEUE_NAME)
	// public void receive(String message){
	// log.info("receive message: " + message);
	// }
	//
	// @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
	// public void receiveTopic1(String message){
	// log.info("receive topic message1: " + message);
	// }
	//
	// @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
	// public void receiveTopic2(String message){
	// log.info("receive topic message2: " + message);
	// }
	//
	// @RabbitListener(queues = MqConfig.FANOUT_QUEUE1)
	// public void receiveFanout1(String message){
	// log.info("receive fanout message1: " + message);
	// }
	//
	// @RabbitListener(queues = MqConfig.FANOUT_QUEUE2)
	// public void receiveFanout2(String message){
	// log.info("receive fanout message2: " + message);
	// }

}
