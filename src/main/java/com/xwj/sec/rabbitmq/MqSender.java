package com.xwj.sec.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwj.sec.util.JsonUtil;

/**
 * 消息生产者
 * 
 * @author xuwenjin
 */
@Service
public class MqSender {
	
	private static Logger log = LoggerFactory.getLogger(MqSender.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send(Object message) {
		String msg = JsonUtil.beanToString(message);
		log.info("send message: " + msg);
		amqpTemplate.convertAndSend(MqConfig.QUEUE_NAME, msg);
	}
	
	public void sendTopic(Object message) {
		String msg = JsonUtil.beanToString(message);
		log.info("send topic message: " + msg);
		amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
		amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
	}
	
	public void sendFanout(Object message) {
		String msg = JsonUtil.beanToString(message);
		log.info("send fanout message: " + msg);
		amqpTemplate.convertAndSend(MqConfig.FUNOUT_EXCHANGE, "fanout.key1", msg + "1");
		amqpTemplate.convertAndSend(MqConfig.FUNOUT_EXCHANGE, "fanout.key2", msg + "2");
	}

}
