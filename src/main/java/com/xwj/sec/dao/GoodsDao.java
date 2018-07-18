package com.xwj.sec.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xwj.sec.entity.SeckillGoods;
import com.xwj.sec.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	/**
	 * 查询商品信息列表
	 */
	@Select("select g.*, mg.stock_count, mg.start_time, mg.end_time, mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();

	/**
	 * 通过商品id查询商品详情
	 * 
	 * @param goodsId
	 *            商品id
	 * @return
	 */
	@Select("select g.*, mg.stock_count, mg.start_time, mg.end_time, mg.seckill_price from seckill_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	/**
	 * 减库存
	 */
	@Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
	public int reduceStock(SeckillGoods g);

}
