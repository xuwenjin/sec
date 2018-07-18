package com.xwj.sec.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xwj.sec.entity.SeckillUser;

@Mapper
public interface SeckillUserDao {
	
	@Select("select * from seckill_user where id = #{id}")
	public SeckillUser getById(@Param("id") long id);
}
