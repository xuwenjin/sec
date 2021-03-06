package com.xwj.sec.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.xwj.sec.entity.OrderInfo;
import com.xwj.sec.entity.SeckillOrder;

@Mapper
public interface OrderDao {

	/**
	 * 通过用户id、商品id查询秒杀订单
	 */
	@Select("select * from seckill_order where user_id = #{userId} and goods_id = #{goodsId}")
	public SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

	/**
	 * 生成订单，返回订单id
	 */
	@Insert("insert into order_info (user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status) values ("
			+ "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status})")
	// 查询最近插入的id
	@SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
	public long insert(OrderInfo orderInfo);

	/**
	 * 生成秒杀订单
	 */
	@Insert("insert into seckill_order (user_id, goods_id, order_id) values (#{userId}, #{goodsId}, #{orderId})")
	public int insertSeckillOrder(SeckillOrder SeckillOrder);

}
