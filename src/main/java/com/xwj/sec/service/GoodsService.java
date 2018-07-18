package com.xwj.sec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwj.sec.dao.GoodsDao;
import com.xwj.sec.entity.SeckillGoods;
import com.xwj.sec.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 查询商品信息(包含秒杀商品信息)
	 */
	public List<GoodsVo> listGoodsVo() {
		return goodsDao.listGoodsVo();
	}

	/**
	 * 通过商品id查询商品详情
	 * 
	 * @param goodsId
	 *            商品id
	 * @return
	 */
	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	/**
	 * 减库存
	 */
	public void reduceStock(GoodsVo goods) {
		SeckillGoods g = new SeckillGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}

}
