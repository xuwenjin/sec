package com.xwj.sec.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	
	public static final String SECKILL_QUEUE_NAME = "seckill.queue"; //秒杀队列名称
	public static final String QUEUE_NAME = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String TOPIC_EXCHANGE = "topicExchange"; //交换机名称
	public static final String FANOUT_QUEUE1 = "fanout.queue1";
	public static final String FANOUT_QUEUE2 = "fanout.queue2";
	public static final String FUNOUT_EXCHANGE = "fanoutExchange"; //交换机名称
	
	/**
	 * direct模式
	 */
	@Bean
	public Queue queue(){
		return new Queue(QUEUE_NAME, true);
	}
	
	/**
	 * topic模式
	 */
	@Bean
	public Queue topicQueue1(){
		return new Queue(TOPIC_QUEUE1, true);
	}
	
	/**
	 * topic模式
	 */
	@Bean
	public Queue topicQueue2(){
		return new Queue(TOPIC_QUEUE2, true);
	}
	
	/**
	 * 创建一个topic的交换机
	 */
	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	
	/**
	 * 将topic1以路由键为"topic.key1"绑定到交换机上
	 */
	@Bean
	public Binding topicBinding1(){
		return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
	}
	
	/**
	 * 将topic2以路由键为"topic.#"绑定到交换机上
	 */
	@Bean
	public Binding topicBinding2(){
		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
	}
	
	/**
	 * fanout模式
	 */
	@Bean
	public Queue fanoutQueue1(){
		return new Queue(FANOUT_QUEUE1, true);
	}
	
	/**
	 * fanout模式
	 */
	@Bean
	public Queue fanoutQueue2(){
		return new Queue(FANOUT_QUEUE2, true);
	}
	
	/**
	 * 创建一个fanout的交换机
	 */
	@Bean
	public FanoutExchange fanoutExchange(){
		return new FanoutExchange(FUNOUT_EXCHANGE);
	}
	
	/**
	 * 将fanout1绑定到交换机上(广播模式，不需要routingKey)
	 */
	@Bean
	public Binding fanoutBinding1(){
		return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
	}
	
	/**
	 * 将fanout2绑定到交换机上(广播模式，不需要routingKey)
	 */
	@Bean
	public Binding fanoutBinding2(){
		return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
	}

}
