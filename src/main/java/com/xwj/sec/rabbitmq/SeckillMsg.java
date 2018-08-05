package com.xwj.sec.rabbitmq;

import com.xwj.sec.entity.SeckillUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeckillMsg {
	
	private SeckillUser user;
	
	private long goodsId;

}
