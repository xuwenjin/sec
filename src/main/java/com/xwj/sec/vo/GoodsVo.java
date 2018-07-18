package com.xwj.sec.vo;

import java.util.Date;

import com.xwj.sec.entity.Goods;

/**
 * 将商品表和秒杀商品表的数据合到一起，用于与前台交互
 * 
 * @author xuwenjin
 */
public class GoodsVo extends Goods {

	private Double seckillPrice; // 秒杀单价
	private Integer stockCount; // 库存数量
	private Date startTime; // 秒杀开始时间
	private Date endTime; // 秒杀结束时间

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Double getSeckillPrice() {
		return seckillPrice;
	}

	public void setSeckillPrice(Double seckillPrice) {
		this.seckillPrice = seckillPrice;
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
