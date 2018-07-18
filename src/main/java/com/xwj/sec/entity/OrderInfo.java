package com.xwj.sec.entity;

import java.util.Date;

/**
 * 订单实体
 * 
 * @author xuwenjin
 */
public class OrderInfo {

	private Long id; // 订单id
	private Long userId; // 用户id
	private Long goodsId; // 商品id
	private Long deliveryAddrId; // 收货地址id
	private String goodsName; // 冗余的商品名称
	private Integer goodsCount; // 商品数量
	private Double goodsPrice; // 商品单价
	private Integer orderChannel; // 1:pc, 2:android, 3 ios
	private Integer status; // 订单状态 0:未支付, 1:已支付, 2:已发货, 3:已收货, 4:已退款, 5:已完成
	private Date createTime; // 订单的创建时间
	private Date payTime; // 支付时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getDeliveryAddrId() {
		return deliveryAddrId;
	}

	public void setDeliveryAddrId(Long deliveryAddrId) {
		this.deliveryAddrId = deliveryAddrId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

}
