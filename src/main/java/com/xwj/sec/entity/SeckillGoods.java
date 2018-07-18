package com.xwj.sec.entity;

import java.util.Date;

/**
 * 秒杀商品实体
 * 
 * @author xuwenjin
 */
public class SeckillGoods {

	private Long id;
	private Long goodsId; // 商品id
	private Integer stockCount; // 库存数量
	private Date startTime; // 秒杀开始时间
	private Date endTime; // 秒杀结束时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
