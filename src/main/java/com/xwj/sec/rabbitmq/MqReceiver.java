package com.xwj.sec.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 消息消费者
 * 
 * @author xuwenjin
 */
@Service
public class MqReceiver {
	
	private static Logger log = LoggerFactory.getLogger(MqSender.class);
	
	@RabbitListener(queues = MqConfig.QUEUE_NAME)
	public void receive(String message){
		log.info("receive message: " + message);
	}
	
	@RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
	public void receiveTopic1(String message){
		log.info("receive topic message1: " + message);
	}
	
	@RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
	public void receiveTopic2(String message){
		log.info("receive topic message2: " + message);
	}
	
	@RabbitListener(queues = MqConfig.FANOUT_QUEUE1)
	public void receiveFanout1(String message){
		log.info("receive fanout message1: " + message);
	}
	
	@RabbitListener(queues = MqConfig.FANOUT_QUEUE2)
	public void receiveFanout2(String message){
		log.info("receive fanout message2: " + message);
	}
	
}
